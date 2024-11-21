package com.fransoufil.ms_orders.amqp;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fransoufil.ms_orders.entities.dtos.OrderDTO;
import com.fransoufil.ms_orders.services.OrderProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderListener {

    private final OrderProcessor orderProcessor;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "order.details-requests")
    public void message(@Payload OrderDTO order) throws JsonProcessingException {
        if (order == null) {
            log.error("Received null order message");
            throw new IllegalArgumentException("Order cannot be null");
        }

        log.info("Received message: {}", objectMapper.writeValueAsString(order));
        orderProcessor.processOrder(order);
    }

}
