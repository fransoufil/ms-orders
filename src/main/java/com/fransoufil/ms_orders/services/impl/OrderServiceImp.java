package com.fransoufil.ms_orders.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fransoufil.ms_orders.entities.Order;
import com.fransoufil.ms_orders.entities.dtos.OrderDTO;
import com.fransoufil.ms_orders.entities.dtos.PageDTO;
import com.fransoufil.ms_orders.exceptions.BusinessRulesException;
import com.fransoufil.ms_orders.exceptions.DataBaseException;
import com.fransoufil.ms_orders.mappers.OrderMapper;
import com.fransoufil.ms_orders.repositories.OrderRepository;
import com.fransoufil.ms_orders.services.OrderProcessor;
import com.fransoufil.ms_orders.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImp implements OrderService, OrderProcessor {

    private final OrderRepository repository;
    private final ObjectMapper objectMapper;
    private final OrderMapper orderMapper;

    public PageDTO<OrderDTO> listPaginated(Integer page, Integer size) throws BusinessRulesException {
        if (size < 0 || page < 0) {
            throw new BusinessRulesException("Page or Size cannot be less than zero.");
        }
        if (size > 0) {
            Page<Order> pageRepository = repository.findAll(PageRequest.of(page, size));
            List<OrderDTO> orderDTOList = pageRepository.stream()
                    .map(orderMapper::fromEntityToDTO)
                    .toList();

            return new PageDTO<>(pageRepository.getTotalElements(),
                    pageRepository.getTotalPages(),
                    page,
                    size,
                    orderDTOList);
        }
        List<OrderDTO> emptyList = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, emptyList);
    }

    public OrderDTO getOrderById(Long id) throws DataBaseException {
        Order order = repository.findById(id)
                .orElseThrow(() -> new DataBaseException("Order " + id + " not found"));

        return orderMapper.fromEntityToDTO(order);
    }

    public OrderDTO createOrder(OrderDTO dto) {
        Order newOrder = orderMapper.fromDTOtoEntity(dto);
        repository.save(newOrder);

        try {
            log.info("Saved Order: {}", objectMapper.writeValueAsString(newOrder));
        } catch (JsonProcessingException e) {
            log.error("Error converting Order to JSON", e);
        }

        return orderMapper.fromEntityToDTO(newOrder);
    }

    public void processOrder(OrderDTO order) {
        if (repository.existsById(order.getOrdersId())) {
            updateOrder(order.getOrdersId(), order);
        } else {
            createOrder(order);
        }
    }

    public void updateOrder(Long id, OrderDTO dto) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " not found"));

        order.getItens().clear();
        orderMapper.fromDTOtoEntity(dto);
        repository.save(order);

        try {
            log.info("Updated Order: {}", objectMapper.writeValueAsString(order));
        } catch (JsonProcessingException e) {
            log.error("Error converting Order to JSON", e);
        }

        orderMapper.fromEntityToDTO(order);
    }
}