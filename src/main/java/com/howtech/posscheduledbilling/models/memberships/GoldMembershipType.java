package com.howtech.posscheduledbilling.models.memberships;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Damond Howard
 * @apiNote This is a static entity that never changes used to store the values
 *          needed to initialize the membership type for GOLD
 *
 */
@Data
@NoArgsConstructor
public class GoldMembershipType {
	private final int freeDeliveriesPerMonth = 75;
	private final double transactionFee = .07;
	private final double cashBackReferal = 150.00;
	private final double onboardingFee = 50.00;
	private final double monthlyCost = 750.00;
	private final double sixMonthCost = 3500.00;
	private final double yearlyCost = 2500.00;
	private final double lifetimeCost = 5000.00;
}