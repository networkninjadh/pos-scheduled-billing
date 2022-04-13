package com.howtech.posscheduledbilling.models.memberships;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Damond Howard
 * @apiNote This is a static entity that never changes used to store the values
 *          needed to initialize the membership type for BRONZE
 *
 */
@Data
@NoArgsConstructor
public class BronzeMembershipType {
	private final int freeDeliveriesPerMonth = 25;
	private final double transactionFee = .10;
	private final double cashBackReferal = 100.00;
	private final double onboardingFee = 100.00;
	private final double monthlyCost = 500.00;
	private final double sixMonthCost = 2500.00;
	private final double yearlyCost = 1000.00;
	private final double lifetimeCost = 3000.00;
}