package com.howtech.posscheduledbilling.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Damond Howard
 * @apiNote this entity maps to the business information table of the store
 *
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "business_info")
public class BusinessInfo {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "ein")
	private String EIN;
	@Column(name = "store_name")
	private String storeName;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	@JsonIgnore
	private Store store;

	/**
	 * TODO: come back to revisit this
	 * 
	 * @param EIN       business id number
	 * @param storeName store name
	 * @param address   store address
	 */
	public BusinessInfo(String EIN, String storeName) {
		super();
		this.EIN = EIN;
		this.storeName = storeName;
	}
}