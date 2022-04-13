package com.howtech.posscheduledbilling.scheduled;

import java.time.LocalDate;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.howtech.posscheduledbilling.clients.StoreClient;
import com.howtech.posscheduledbilling.models.Shipment;
import com.howtech.posscheduledbilling.models.Store;
import com.howtech.posscheduledbilling.models.enums.ChargeFrequency;
import com.howtech.posscheduledbilling.models.enums.MembershipType;
import com.howtech.posscheduledbilling.models.memberships.BronzeMembershipType;
import com.howtech.posscheduledbilling.models.memberships.GoldMembershipType;
import com.howtech.posscheduledbilling.models.memberships.PlatinumMembershipType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Damond Howard
 * @apiNote This is a group of scheduled tasks
 *
 */
@Component
public class ScheduledPaymentsAndCharges {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

	private final StoreClient storeClient;

	public ScheduledPaymentsAndCharges(StoreClient storeClient) {
		this.storeClient = storeClient;
	}

	/**
	 * 
	 * @param nextBillingDate
	 * @return a boolean false if the nextBillingDate is not today and true if it is
	 */
	public static boolean isToday(LocalDate nextBillingDate) {
		LocalDate today = LocalDate.now();
		if (nextBillingDate.equals(today)) {
			log.info("Date is today");
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param firstDate
	 * @param dateToCheck
	 * @param secondDate
	 * @return a boolean true if the dateToCheck comes between the first and second
	 *         date and false if not
	 */
	public static boolean isBetweenDates(LocalDate firstDate, LocalDate dateToCheck, LocalDate secondDate) {
		if (dateToCheck.isAfter(firstDate) && dateToCheck.isBefore(secondDate)) {
			return true;
		}
		return false;
	}

	/**
	 * Charge and pay all Stores whos billing date is the current date the task runs
	 * every day
	 * figure out their plan, how many orders they did, the transaction fee, if
	 * there have been any refereals, add the onboarding fee
	 * determine if they are monthly 6 months or lifetime and add based on that
	 */

	@Scheduled(cron = " 0 0 4 * * *")
	public void chargeAndPayStores() {
		System.out.println("Method Start");
		System.out.println("Initiated MembershipTypes ");

		final BronzeMembershipType bronzeMembership = new BronzeMembershipType();
		final GoldMembershipType goldMembership = new GoldMembershipType();
		final PlatinumMembershipType platinumMembership = new PlatinumMembershipType();

		System.out.println("Create predicates");
		System.out.println("Check stores");

		Predicate<Store> byChargeDate = store -> {
			System.out.println(store.getStoreId());
			return isToday(store.getNextBillingDate());
		};

		Predicate<Shipment> fitsDateRange = shipment -> {
			return isBetweenDates(shipment.getStore().getLastBillingDate(), shipment.getShipDate(),
					shipment.getStore().getNextBillingDate());
		};

		List<Store> storesToCharge = storeClient.getAll().stream().filter(byChargeDate)
				.collect(Collectors.toList());
		System.out.println("Go through all stores");
		storesToCharge.stream().forEach((store) -> {
			System.out.println(store.getStoreName() + " is being charged");
			Set<Shipment> storeShipments = store.getStoreShipments().stream().filter(fitsDateRange)
					.collect(Collectors.toSet());
			double chargeAmount = 0.0;
			double payAmount = 0.0;
			int numFreeDeliveries = 0;
			int numberOfReferalsLastBillingCycle = store.getReferals();
			int numOfDeliveriesLastBillingCycle = store.getStoreShipments().size();

			if (store.getMembershipType() == MembershipType.BRONZE) {
				if (store.getWhenToCharge() == ChargeFrequency.MONTHLY) {
					chargeAmount += bronzeMembership.getMonthlyCost();
					numFreeDeliveries = bronzeMembership.getFreeDeliveriesPerMonth();
					double transactionFees = 0.0;
					if (storeShipments.size() == 0) {
						transactionFees = 0.0;
					} else if (storeShipments.size() <= numFreeDeliveries) {
						transactionFees = 0.0;
					} else {
						storeShipments = storeShipments
								.stream()
								.filter((shipment) -> {
									return true;
								})
								.skip(numFreeDeliveries)
								.collect(Collectors.toSet());
						transactionFees = storeShipments
								.stream()
								.map(shipment -> (shipment.getShipmentCost() * shipment.getTransactionFee()))
								.reduce((double) 0, (previous, current) -> {
									return previous + current;
								});
					}
					chargeAmount += transactionFees;
				} else if (store.getWhenToCharge() == ChargeFrequency.BI_YEARLY) {
					chargeAmount += bronzeMembership.getSixMonthCost();
					numFreeDeliveries = bronzeMembership.getFreeDeliveriesPerMonth() * 6;
					double transactionFees = 0.0;
					if (storeShipments.size() == 0) {
						transactionFees = 0.0;
					} else if (storeShipments.size() <= numFreeDeliveries) {
						transactionFees = 0.0;
					} else {
						storeShipments = storeShipments
								.stream()
								.filter((shipment) -> {
									return true;
								})
								.skip(numFreeDeliveries)
								.collect(Collectors.toSet());
						transactionFees = storeShipments
								.stream()
								.map(shipment -> (shipment.getShipmentCost() * shipment.getTransactionFee()))
								.reduce((double) 0, (previous, current) -> {
									return previous + current;
								});
					}
					chargeAmount += transactionFees;
				} else if (store.getWhenToCharge() == ChargeFrequency.YEARLY) {
					chargeAmount += bronzeMembership.getYearlyCost();
					numFreeDeliveries = bronzeMembership.getFreeDeliveriesPerMonth() * 12;
					double transactionFees = 0.0;
					if (storeShipments.size() == 0) {
						transactionFees = 0.0;
					} else if (storeShipments.size() <= numFreeDeliveries) {
						transactionFees = 0.0;
					} else {
						storeShipments = storeShipments
								.stream()
								.filter((shipment) -> {
									return true;
								})
								.skip(numFreeDeliveries)
								.collect(Collectors.toSet());
						transactionFees = storeShipments
								.stream()
								.map(shipment -> (shipment.getShipmentCost() * shipment.getTransactionFee()))
								.reduce((double) 0, (previous, current) -> {
									return previous + current;
								});
					}
					chargeAmount += transactionFees;

				} else if (store.getWhenToCharge() == ChargeFrequency.LIFETIME) {
					chargeAmount += bronzeMembership.getLifetimeCost();
				} else {
					chargeAmount += 0;
				}
			} else if (store.getMembershipType() == MembershipType.GOLD) {
				if (store.getWhenToCharge() == ChargeFrequency.MONTHLY) {
					chargeAmount += goldMembership.getMonthlyCost();
					numFreeDeliveries = goldMembership.getFreeDeliveriesPerMonth();
					double transactionFees = 0.0;
					if (storeShipments.size() == 0) {
						transactionFees = 0.0;
					} else if (storeShipments.size() <= numFreeDeliveries) {
						transactionFees = 0.0;
					} else {
						storeShipments = storeShipments
								.stream()
								.filter((shipment) -> {
									return true;
								})
								.skip(numFreeDeliveries)
								.collect(Collectors.toSet());
						transactionFees = storeShipments
								.stream()
								.map(shipment -> (shipment.getShipmentCost() * shipment.getTransactionFee()))
								.reduce((double) 0, (previous, current) -> {
									return previous + current;
								});
					}
					chargeAmount += transactionFees;
				} else if (store.getWhenToCharge() == ChargeFrequency.BI_YEARLY) {
					chargeAmount += goldMembership.getSixMonthCost();
					numFreeDeliveries = goldMembership.getFreeDeliveriesPerMonth() * 6;
					double transactionFees = 0.0;
					if (storeShipments.size() == 0) {
						transactionFees = 0.0;
					} else if (storeShipments.size() <= numFreeDeliveries) {
						transactionFees = 0.0;
					} else {
						storeShipments = storeShipments
								.stream()
								.filter((shipment) -> {
									return true;
								})
								.skip(numFreeDeliveries)
								.collect(Collectors.toSet());
						transactionFees = storeShipments
								.stream()
								.map(shipment -> (shipment.getShipmentCost() * shipment.getTransactionFee()))
								.reduce((double) 0, (previous, current) -> {
									return previous + current;
								});
					}
					chargeAmount += transactionFees;
				} else if (store.getWhenToCharge() == ChargeFrequency.YEARLY) {
					chargeAmount += goldMembership.getYearlyCost();
					numFreeDeliveries = goldMembership.getFreeDeliveriesPerMonth() * 12;
					double transactionFees = 0.0;
					if (storeShipments.size() == 0) {
						transactionFees = 0.0;
					} else if (storeShipments.size() <= numFreeDeliveries) {
						transactionFees = 0.0;
					} else {
						storeShipments = storeShipments
								.stream()
								.filter((shipment) -> {
									return true;
								})
								.skip(numFreeDeliveries)
								.collect(Collectors.toSet());
						transactionFees = storeShipments
								.stream()
								.map(shipment -> (shipment.getShipmentCost() * shipment.getTransactionFee()))
								.reduce((double) 0, (previous, current) -> {
									return previous + current;
								});
					}
					chargeAmount += transactionFees;
				} else if (store.getWhenToCharge() == ChargeFrequency.LIFETIME) {
					chargeAmount += goldMembership.getLifetimeCost();
				} else {
					chargeAmount += 0;
				}
			} else if (store.getMembershipType() == MembershipType.PLATINUM) {
				if (store.getWhenToCharge() == ChargeFrequency.MONTHLY) {
					chargeAmount += platinumMembership.getMonthlyCost();
					numFreeDeliveries = platinumMembership.getFreeDeliveriesPerMonth();
					double transactionFees = 0.0;
					if (storeShipments.size() == 0) {
						transactionFees = 0.0;
					} else if (storeShipments.size() <= numFreeDeliveries) {
						transactionFees = 0.0;
					} else {
						storeShipments = storeShipments
								.stream()
								.filter((shipment) -> {
									return true;
								})
								.skip(numFreeDeliveries)
								.collect(Collectors.toSet());
						transactionFees = storeShipments
								.stream()
								.map(shipment -> (shipment.getShipmentCost() * shipment.getTransactionFee()))
								.reduce((double) 0, (previous, current) -> {
									return previous + current;
								});
					}
					chargeAmount += transactionFees;
				} else if (store.getWhenToCharge() == ChargeFrequency.BI_YEARLY) {
					chargeAmount += platinumMembership.getSixMonthCost();
					numFreeDeliveries = platinumMembership.getFreeDeliveriesPerMonth() * 6;
					double transactionFees = 0.0;
					if (storeShipments.size() == 0) {
						transactionFees = 0.0;
					} else if (storeShipments.size() <= numFreeDeliveries) {
						transactionFees = 0.0;
					} else {
						storeShipments = storeShipments
								.stream()
								.filter((shipment) -> {
									return true;
								})
								.skip(numFreeDeliveries)
								.collect(Collectors.toSet());
						transactionFees = storeShipments
								.stream()
								.map(shipment -> (shipment.getShipmentCost() * shipment.getTransactionFee()))
								.reduce((double) 0, (previous, current) -> {
									return previous + current;
								});
					}

					chargeAmount += transactionFees;
				} else if (store.getWhenToCharge() == ChargeFrequency.YEARLY) {
					chargeAmount += platinumMembership.getYearlyCost();
					numFreeDeliveries = platinumMembership.getFreeDeliveriesPerMonth() * 12;
					double transactionFees = 0.0;
					if (storeShipments.size() == 0) {
						transactionFees = 0.0;
					} else if (storeShipments.size() <= numFreeDeliveries) {
						transactionFees = 0.0;
					} else {
						storeShipments = storeShipments
								.stream()
								.filter((shipment) -> {
									return true;
								})
								.skip(numFreeDeliveries)
								.collect(Collectors.toSet());
						transactionFees = storeShipments
								.stream()
								.map(shipment -> (shipment.getShipmentCost() * shipment.getTransactionFee()))
								.reduce((double) 0, (previous, current) -> {
									return previous + current;
								});
					}
					chargeAmount += transactionFees;
				} else if (store.getWhenToCharge() == ChargeFrequency.LIFETIME) {
					chargeAmount += platinumMembership.getLifetimeCost();
				} else {
					chargeAmount += 0;
				}
			}
			System.out.println("Charge amount is " + chargeAmount);
			System.out.println("Pay amount is " + payAmount);
			System.out.println("Number of referals last cycle is " + numberOfReferalsLastBillingCycle);
			System.out.println("Number of deliveries last cycle is " + numOfDeliveriesLastBillingCycle);
			// charge the store here once the amount is generated
			// generate an invoice for the charge
			// save it in the store
			// this is where we actually charge the customer
		});
	}
}