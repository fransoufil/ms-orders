package com.fransoufil.ms_orders.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fransoufil.ms_orders.entities.Order;
import com.fransoufil.ms_orders.entities.dtos.OrderDTO;
import com.fransoufil.ms_orders.entities.dtos.PageDTO;
import com.fransoufil.ms_orders.exceptions.BusinessRulesException;
import com.fransoufil.ms_orders.exceptions.DataBaseException;
import com.fransoufil.ms_orders.mappers.OrderMapper;
import com.fransoufil.ms_orders.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImpTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImp orderService;

    private Order order;
    private OrderDTO orderDTO;

    @BeforeEach
    void setUp() {
        order = new Order();
        orderDTO = new OrderDTO();
    }

    @Test
    void listPaginated_ShouldReturnPageDTO_WhenPageAndSizeAreValid() throws BusinessRulesException {
        Page<Order> page = new PageImpl<>(List.of(order));
        when(repository.findAll(any(PageRequest.class))).thenReturn(page);
        when(orderMapper.fromEntityToDTO(any(Order.class))).thenReturn(orderDTO);

        PageDTO<OrderDTO> result = orderService.listPaginated(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(repository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void listPaginated_ShouldThrowException_WhenPageOrSizeIsNegative() {
        assertThrows(BusinessRulesException.class, () -> orderService.listPaginated(-1, 10));
        assertThrows(BusinessRulesException.class, () -> orderService.listPaginated(0, -10));
    }

    @Test
    void getOrderById_ShouldReturnOrderDTO_WhenOrderExists() throws DataBaseException {
        when(repository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderMapper.fromEntityToDTO(any(Order.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.getOrderById(1L);

        assertNotNull(result);
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void getOrderById_ShouldThrowException_WhenOrderDoesNotExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DataBaseException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void createOrder_ShouldReturnOrderDTO_WhenOrderIsCreated() {
        when(orderMapper.fromDTOtoEntity(any(OrderDTO.class))).thenReturn(order);
        when(repository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.fromEntityToDTO(any(Order.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.createOrder(orderDTO);

        assertNotNull(result);
        verify(repository, times(1)).save(any(Order.class));
    }

    @Test
    void processOrder_ShouldUpdateOrder_WhenOrderExists() {
        orderDTO.setOrdersId(1L); // Ensure orderDTO has a non-null ID
        when(repository.existsById(orderDTO.getOrdersId())).thenReturn(true);
        OrderServiceImp spyOrderService = spy(orderService);
        doNothing().when(spyOrderService).updateOrder(anyLong(), any(OrderDTO.class));

        spyOrderService.processOrder(orderDTO);

        verify(spyOrderService, times(1)).updateOrder(anyLong(), any(OrderDTO.class));
    }

    @Test
    void processOrder_ShouldCreateOrder_WhenOrderDoesNotExist() {
        when(repository.existsById(orderDTO.getOrdersId())).thenReturn(false);
        when(orderMapper.fromDTOtoEntity(any(OrderDTO.class))).thenReturn(order);
        when(repository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.fromEntityToDTO(any(Order.class))).thenReturn(orderDTO);

        orderService.processOrder(orderDTO);

        verify(repository, times(1)).save(any(Order.class));
        verify(orderMapper, times(1)).fromDTOtoEntity(any(OrderDTO.class));
        verify(orderMapper, times(1)).fromEntityToDTO(any(Order.class));
    }

    @Test
    void updateOrder_ShouldUpdateOrder_WhenOrderExists() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderMapper.fromDTOtoEntity(any(OrderDTO.class))).thenReturn(order);
        when(repository.save(any(Order.class))).thenReturn(order);

        orderService.updateOrder(1L, orderDTO);

        verify(repository, times(1)).save(any(Order.class));
    }

    @Test
    void updateOrder_ShouldThrowException_WhenOrderDoesNotExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.updateOrder(1L, orderDTO));
    }
}