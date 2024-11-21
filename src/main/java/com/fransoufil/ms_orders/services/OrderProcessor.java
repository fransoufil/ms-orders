package com.fransoufil.ms_orders.services;


import com.fransoufil.ms_orders.entities.dtos.OrderDTO;

public interface OrderProcessor {

    void processOrder(OrderDTO order);

}
