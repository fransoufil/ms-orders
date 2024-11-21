package com.fransoufil.ms_orders.controllers;

import com.fransoufil.ms_orders.controllers.documentation.OrderControllerDoc;
import com.fransoufil.ms_orders.entities.dtos.OrderDTO;
import com.fransoufil.ms_orders.entities.dtos.PageDTO;
import com.fransoufil.ms_orders.exceptions.BusinessRulesException;
import com.fransoufil.ms_orders.exceptions.DataBaseException;
import com.fransoufil.ms_orders.services.OrderService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController implements OrderControllerDoc {

    private final OrderService service;

    @GetMapping
    public PageDTO<OrderDTO> listPaginated(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            return service.listPaginated(page, size);
        } catch (BusinessRulesException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable @NotNull Long id) {
        try {
            OrderDTO dto = service.getOrderById(id);
            return ResponseEntity.ok(dto);
        } catch (DataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}
