package com.fransoufil.ms_orders.mappers;

import com.fransoufil.ms_orders.entities.Product;
import com.fransoufil.ms_orders.entities.dtos.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product fromDTOtoEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getProductId())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .build();
    }

    public ProductDTO fromEntityToDTO(Product itemEntity) {
        return ProductDTO.builder()
                .productId(itemEntity.getProductId())
                .description(itemEntity.getDescription())
                .price(itemEntity.getPrice())
                .quantity(itemEntity.getQuantity())
                .build();
    }
}
