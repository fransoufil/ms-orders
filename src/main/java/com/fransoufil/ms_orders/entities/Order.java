package com.fransoufil.ms_orders.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fransoufil.ms_orders.entities.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long orderId;

    @NotNull
    private Long customerId;

    @NotNull
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    private Date dateTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private BigDecimal totalAmount;

    @OneToMany(mappedBy="id.pedido")
    private Set<ItemOrder> itens = new HashSet<>();

    public BigDecimal getValorTotal() {
        BigDecimal soma = BigDecimal.ZERO;
        for (ItemOrder ip : itens) {
            soma = soma.add(ip.getSubTotal());
        }
        return soma;
    }

}
