package com.fransoufil.ms_orders.controllers.documentation;


import com.fransoufil.ms_orders.entities.dtos.OrderDTO;
import com.fransoufil.ms_orders.entities.dtos.PageDTO;
import com.fransoufil.ms_orders.exceptions.BusinessRulesException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface OrderControllerDoc {

    @Operation(summary = "List paginated orders",
            description = "Returns a paginated list of orders.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful listing.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid parameters."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/orders")
    PageDTO<OrderDTO> listPaginated(
            @RequestParam Integer page,
            @RequestParam Integer size) throws BusinessRulesException;

    @Operation(summary = "Get order by ID",
            description = "Returns the order corresponding to the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order successfully retrieved.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/orders/{id}")
    ResponseEntity<OrderDTO> getById(@PathVariable @NotNull Long id);
}
