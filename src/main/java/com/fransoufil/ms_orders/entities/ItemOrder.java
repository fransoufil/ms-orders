package com.fransoufil.ms_orders.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemOrder implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@EmbeddedId
	private ItemOrderPK id = new ItemOrderPK();
	private Integer quantidade;
	private BigDecimal desconto;
	private BigDecimal preco;

	public BigDecimal getSubTotal() {
		return preco.subtract(desconto).multiply(BigDecimal.valueOf(quantidade));
	}

}
