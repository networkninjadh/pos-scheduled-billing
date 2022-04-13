package com.howtech.posscheduledbilling.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.howtech.posscheduledbilling.models.enums.ChargeFrequency;
import com.howtech.posscheduledbilling.models.enums.MembershipType;
import com.howtech.posscheduledbilling.models.memberships.BronzeMembershipType;
import com.howtech.posscheduledbilling.models.memberships.GoldMembershipType;
import com.howtech.posscheduledbilling.models.memberships.PlatinumMembershipType;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author Damond Howard
 * @apiNote this class represents an entity which maps to an entire store and
 *          everything inside that store this is the most important relationship
 *          in the database
 */

@Data
@Entity
@AllArgsConstructor
@Table(name = "stores")
public class Store {

	@Id
	@GeneratedValue
	@Column(name = "store_id")
	private Long storeId;
	@Column(name = "store_name", nullable = false)
	private String storeName;
	@Column(name = "store_logo_uri")
	private String storeLogo;
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;
	@Column(name = "cell_phone_number")
	private String cellPhoneNumber;
	@Column(name = "queue_full", nullable = false)
	private boolean queueFull;
	@Column(name = "open_for_delivery", nullable = false)
	private boolean openForDelivery;
	@Column(name = "owner_name", nullable = false)
	private String ownerName; // this will be an array

	@Column(name = "owners")
	@ElementCollection
	private List<String> owners = new ArrayList<String>();
	@Column(name = "account_manager")
	private String accountManager;
	@Column(name = "membership_code")
	private String memberShipCode;
	@Enumerated(EnumType.STRING)
	private MembershipType membershipType;
	@Column(name = "charge_frequency")
	private ChargeFrequency whenToCharge;
	@Column(name = "account_start_date")
	private LocalDate accountStartDate = LocalDate.now();
	@Column(name = "next_billing_date")
	private LocalDate nextBillingDate;
	@Column(name = "last_billing_date")
	private LocalDate lastBillingDate;
	@Column(name = "number_of_referals")
	private int referals;
	@Column(name = "number_of_transactions")
	private int transactions;
	@OneToOne(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private HoursOfOperation storeHours;
	@OneToOne(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private AddressInfo address;
	@OneToOne(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private BusinessInfo businessInfo;
	@OneToOne(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private AccountInfo accountInfo;
	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Employee> employees = new HashSet<>();
	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<StoreOrder> storeOrders = new HashSet<>();
	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Product> storeInventory = new HashSet<>();
	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Shipment> storeShipments = new HashSet<>();

	public Store() {
		this.lastBillingDate = LocalDate.now();
	}

	/**
	 * Set next billing date sets the next billing date to be used to bill stores
	 */
	public void setNextBillingDate() {

		if (whenToCharge == ChargeFrequency.MONTHLY) {
			nextBillingDate = lastBillingDate.plusMonths(1);
		} else if (whenToCharge == ChargeFrequency.BI_YEARLY) {
			nextBillingDate = lastBillingDate.plusMonths(6);
		} else if (whenToCharge == ChargeFrequency.YEARLY) {
			nextBillingDate = lastBillingDate.plusYears(1);
		} else if (whenToCharge == ChargeFrequency.LIFETIME) {
			nextBillingDate = null;
		}
	}

	public void addShipment(Shipment storeShipment) {
		final BronzeMembershipType bronzeMembership = new BronzeMembershipType();
		final GoldMembershipType goldMembership = new GoldMembershipType();
		final PlatinumMembershipType platinumMembership = new PlatinumMembershipType();

		if (this.membershipType == MembershipType.BRONZE) {
			storeShipment.setTransactionFee(bronzeMembership.getTransactionFee());
		} else if (this.membershipType == MembershipType.GOLD) {
			storeShipment.setTransactionFee(goldMembership.getTransactionFee());
		} else if (this.membershipType == MembershipType.PLATINUM) {
			storeShipment.setTransactionFee(platinumMembership.getTransactionFee());
		}
		storeShipments.add(storeShipment);
		storeShipment.setStore(this);
	}

	public void removeShipment(Shipment storeShipment) {
		storeShipments.remove(storeShipment);
		storeShipment.setStore(null);
	}

	public void addEmployee(Employee employee) {
		employees.add(employee);
		employee.setStore(this);
	}

	public void removeEmployee(Employee employee) {
		employees.remove(employee);
		employee.setStore(null);
	}

	public void addInventory(Product product) {
		storeInventory.add(product);
		product.setStore(this);

	}

	public void removeInventory(Product product) {
		storeInventory.remove(product);
		product.setStore(null);
	}

	public void addOrder(StoreOrder order) {
		storeOrders.add(order);
		order.setStore(this);
	}

	public void setAddress(AddressInfo address) {
		if (address == null) {
			if (this.address != null) {
				this.address.setStore(null);
			}
		} else {
			address.setStore(this);
		}
		this.address = address;
	}

	public void setHoursOfOperation(HoursOfOperation storeHours) {
		if (storeHours == null) {
			if (this.storeHours != null) {
				this.storeHours.setStore(this);
			} else {
				this.storeHours = storeHours;
			}
		}
		this.storeHours = storeHours;
	}

	public boolean isOpenForDelivery() {
		return openForDelivery;
	}

	public void setBusinessInfo(BusinessInfo businessInfo) {
		if (businessInfo == null) {
			if (this.businessInfo != null) {
				this.businessInfo.setStore(null);
			}
		} else {
			businessInfo.setStore(this);
		}
		this.businessInfo = businessInfo;
	}

	public void setAccountInfo(AccountInfo accountInfo) {
		if (accountInfo == null) {
			if (this.accountInfo != null) {
				this.accountInfo.setStore(null);
			}
		} else {
			accountInfo.setStore(this);
		}
		this.accountInfo = accountInfo;
	}

	public void addReferal() {
		this.referals++;
	}
}