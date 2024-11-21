package com.fransoufil.ms_orders.mappers;

import com.fransoufil.ms_orders.entities.ItemOrder;
import com.fransoufil.ms_orders.entities.ItemOrderPK;
import com.fransoufil.ms_orders.entities.Order;
import com.fransoufil.ms_orders.entities.dtos.OrderDTO;
import com.fransoufil.ms_orders.entities.dtos.ProductDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order fromDTOtoEntity(OrderDTO orderDTO) {
        Order order = Order.builder()
                .orderId(orderDTO.getOrdersId())
                .customerId(orderDTO.getCustomerId())
                .dateTime(orderDTO.getDateTime())
                .status(orderDTO.getStatus())
                .totalAmount(orderDTO.getTotalAmount())
                .build();

        if (orderDTO.getItems() != null && !orderDTO.getItems().isEmpty()) {
            List<ItemOrder> items = orderDTO.getItems().stream()
                    .map(this::fromDTOtoEntity)
                    .toList();

            order.setItens(new HashSet<>(items));
            items.forEach(item -> item.getId().setPedido(order));
        }

        return order;
    }

    public OrderDTO fromEntityToDTO(Order order) {
        return OrderDTO.builder()
                .ordersId(order.getOrderId())
                .customerId(order.getCustomerId())
                .dateTime(order.getDateTime())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .items(order.getItens().stream()
                        .map(this::fromEntityToDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    private ItemOrder fromDTOtoEntity(ProductDTO productDTO) {
        return ItemOrder.builder()
                .id(new ItemOrderPK())
                .preco(productDTO.getPrice())
                .quantidade(productDTO.getQuantity())
                .desconto(BigDecimal.ZERO) // Assuming no discount in DTO
                .build();
    }

    private ProductDTO fromEntityToDTO(ItemOrder itemOrder) {
        return ProductDTO.builder()
                .productId(itemOrder.getId().getProduto().getId())
                .description(itemOrder.getId().getProduto().getDescription())
                .price(itemOrder.getPreco())
                .quantity(itemOrder.getQuantidade())
                .build();
    }
}