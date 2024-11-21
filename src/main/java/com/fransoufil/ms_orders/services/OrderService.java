package com.fransoufil.ms_orders.services;


import com.fransoufil.ms_orders.entities.dtos.OrderDTO;
import com.fransoufil.ms_orders.entities.dtos.PageDTO;
import com.fransoufil.ms_orders.exceptions.BusinessRulesException;
import com.fransoufil.ms_orders.exceptions.DataBaseException;

public interface OrderService {

    PageDTO<OrderDTO> listPaginated(Integer page, Integer size) throws BusinessRulesException;

    OrderDTO getOrderById(Long id) throws DataBaseException;

}
