package com.fransoufil.ms_orders.entities.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    @Schema(description = "Unique identifier of the item", example = "1234")
    private Long productId;

    @Schema(description = "Quantity of the item ordered", example = "4321")
    private Integer quantity;

    @Schema(description = "Description of the item", example = "PC Gamer")
    private String description;

    @Schema(description = "Price per unit of the item", example = "1230.50")
    private BigDecimal price;

}
