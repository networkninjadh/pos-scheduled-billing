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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Damond Howard
 * @apiNote this entity maps to the address information table of the store
 *
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address_info")
public class AddressInfo {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long addressId;
	@Column(name = "street")
	private String street;
	@Column(name = "city")
	private String city;
	@Column(name = "province")
	private String province;
	@Column(name = "country")
	private String country;
	@Column(name = "postcode")
	private String postCode;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	@JsonIgnore
	private Store store;

	/**
	 * 
	 * @param street   the entity's street
	 * @param city     the entity's city
	 * @param province the entity's province
	 * @param country  the entity's country
	 * @param postCode the entity's postal code
	 */
	public AddressInfo(String street, String city, String province, String country, String postCode) {
		super();
		this.street = street;
		this.city = city;
		this.province = province;
		this.country = country;
		this.postCode = postCode;
	}
}