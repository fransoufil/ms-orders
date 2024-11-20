package com.fransoufil.ms_orders.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fransoufil.ms_orders.entities.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long orderId;

    @NotNull
    private Long customerId;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private BigDecimal totalAmount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orders", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();

}
