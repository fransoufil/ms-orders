package com.fransoufil.ms_orders.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Data
public class ItemOrderPK implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Order pedido;
	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Product produto;

}