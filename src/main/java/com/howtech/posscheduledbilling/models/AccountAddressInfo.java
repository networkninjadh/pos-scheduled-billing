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

import lombok.Data;

/**
 * 
 * @author Damond Howard
 * @apiNote this entity maps to the address information table of the store
 *
 */

@Data
@Entity
@Table(name = "address_info")
public class AccountAddressInfo {
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
	@JoinColumn(name = "account_id")
	@JsonIgnore
	private AccountInfo accountInfo;

	public AccountAddressInfo() {
	}

	/**
	 * 
	 * @param street   the entity's street
	 * @param city     the entity's city
	 * @param province the entity's province
	 * @param country  the entity's country
	 * @param postCode the entity's postal code
	 */
	public AccountAddressInfo(String street, String city, String province, String country, String postCode) {
		super();
		this.street = street;
		this.city = city;
		this.province = province;
		this.country = country;
		this.postCode = postCode;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public AccountInfo getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(AccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}
}