package com.howtech.posscheduledbilling.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 
 * @author Damond Howard
 * @apiNote this class is an entity that manages the shipments table in the database
 *
 */

/**
 * 
 * TODO: come back and finish this entity relies on order
 *
 */
@Data
@Entity
@Table(name = "shipments")
public class Shipment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "shipment_id")
	private Long shipmentId;
	@Column(name = "order_id")
	private Long orderId;
	@Column(name = "store_id")
	private Long storeId;
	@Column(name = "customer_id")
	private Long customerId;
	@Column(name = "driver_id")
	private Long driverId;
	@Column(name = "shipped_at")
	private LocalDate shipDate = LocalDate.now();
	@Column(name = "fulfilled_by")
	private Long employeeId;
	private double transactionFee = 0.0;
	private double shipmentCost;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Store store;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Store))
			return false;
		return shipmentId != null && shipmentId.equals(((Store) o).getStoreId());
	}

	@Override
	public int hashCode() {
		return 31;
	}
}