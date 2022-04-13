package com.howtech.posscheduledbilling.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Damond Howard
 * @apiNote this entity maps to a store order which is an order for a customer
 *          from a specific store
 *
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store_orders")
public class StoreOrder {
	@Id
	@GeneratedValue
	@Column(name = "order_id")
	private Long orderId;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "order_date")
	private LocalDate orderDate = LocalDate.now();
	@ElementCollection
	protected Set<Long> cartItems = new HashSet<>(); // corresponds to inventory ids
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Store store;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Store))
			return false;
		return orderId != null && orderId.equals(((Store) o).getStoreId());
	}

	@Override
	public int hashCode() {
		return 31;
	}
}