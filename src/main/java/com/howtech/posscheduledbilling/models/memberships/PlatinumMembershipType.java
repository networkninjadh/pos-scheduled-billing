package com.howtech.posscheduledbilling.models.memberships;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Damond Howard
 * @apiNote This is a static entity that never changes used to store the values
 *          needed to initialize the membership type for PLATINUM
 *
 */
@Data
@NoArgsConstructor
public class PlatinumMembershipType {
	private final int freeDeliveriesPerMonth = 100;
	private final double transactionFee = .04;
	private final double cashBackReferal = 200.00;
	private final double onboardingFee = 0.00;
	private final double monthlyCost = 900.00;
	private final double sixMonthCost = 4500.00;
	private final double yearlyCost = 3500.00;
	private final double lifetimeCost = 7000.00;
}