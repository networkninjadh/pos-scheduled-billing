package com.howtech.posscheduledbilling.models;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.howtech.posscheduledbilling.models.enums.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Damond Howard
 * @apiNote this entity maps to a store's account information with our system
 *          which will be used for identifying stores and charging their
 *          accounts
 *
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class AccountInfo {
	@Id
	@GeneratedValue
	@Column(name = "account_id")
	private Long accountId;
	@Column(name = "routing_number")
	private Long routingNumber;
	@Column(name = "account_type")
	private AccountType accountType;
	@Column(name = "created_at")
	private Date accountStartDate = new Date(System.currentTimeMillis());
	@OneToOne(mappedBy = "accountInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private AccountAddressInfo accountAddressInfo;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	@JsonIgnore
	private Store store;

	public void setAccountAddressInfo(AccountAddressInfo accountAddressInfo) {
		if (accountAddressInfo == null) {
			if (this.accountAddressInfo != null) {
				this.accountAddressInfo.setAccountInfo(null);
			}
		} else {
			accountAddressInfo.setAccountInfo(this);
		}
		this.accountAddressInfo = accountAddressInfo;
	}
}