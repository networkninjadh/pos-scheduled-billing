package com.howtech.posscheduledbilling.models;

import java.time.LocalTime;

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
 * @apiNote this is a model that represents the regular store hours of a store
 *
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hours_of_operation")
public class HoursOfOperation {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "mon_open", nullable = false)
	private LocalTime mondayOpen;
	@Column(name = "tue_open", nullable = false)
	private LocalTime tuesdayOpen;
	@Column(name = "wed_open", nullable = false)
	private LocalTime wednesdayOpen;
	@Column(name = "thu_open", nullable = false)
	private LocalTime thursdayOpen;
	@Column(name = "fri_open", nullable = false)
	private LocalTime fridayOpen;
	@Column(name = "sat_open", nullable = false)
	private LocalTime saturdayOpen;
	@Column(name = "sun_open", nullable = false)
	private LocalTime sundayOpen;
	@Column(name = "mon_close", nullable = false)
	private LocalTime mondayClose;
	@Column(name = "tue_close", nullable = false)
	private LocalTime tuesdayClose;
	@Column(name = "wed_close", nullable = false)
	private LocalTime wednesdayClose;
	@Column(name = "thu_close", nullable = false)
	private LocalTime thursdayClose;
	@Column(name = "fri_close", nullable = false)
	private LocalTime fridayClose;
	@Column(name = "sat_close", nullable = false)
	private LocalTime saturdayClose;
	@Column(name = "sun_close", nullable = false)
	private LocalTime sundayClose;
	@Column(name = "new_years_eve")
	private boolean openForNewYearsEve;
	@Column(name = "new_years_eve_open")
	private LocalTime newYearsEvenOpen;
	@Column(name = "new_years_eve_close")
	private LocalTime newYearsEveClose;
	@Column(name = "new_years")
	private boolean openForNewYears;
	@Column(name = "new_years_open")
	private LocalTime newYearsOpen;
	@Column(name = "new_years_close")
	private LocalTime newYearsClose;
	@Column(name = "independence_day")
	private boolean openForIndependenceDay;
	@Column(name = "independence_open")
	private LocalTime independenceOpen;
	@Column(name = "independence_close")
	private LocalTime independenceClose;
	@Column(name = "memorial_day")
	private boolean openForMemorialDay;
	@Column(name = "memorial_day_open")
	private LocalTime memorialDayOpen;
	@Column(name = "memorial_day_close")
	private LocalTime memorialDayClose;
	@Column(name = "easter")
	private boolean openForEaster;
	@Column(name = "easter_open")
	private LocalTime easterOpen;
	@Column(name = "easter_close")
	private LocalTime easterClose;
	@Column(name = "columbus_day")
	private boolean openForColumbusDay;
	@Column(name = "columbus_day_open")
	private LocalTime columbusDayOpen;
	@Column(name = "columbus_day_close")
	private LocalTime columbusDayClose;
	@Column(name = "thanksgiving")
	private boolean penForThanksgiving;
	@Column(name = "thanksgiving_open")
	private LocalTime thanksgivingOpen;
	@Column(name = "thanksgiving_close")
	private LocalTime thanksgivingClose;
	@Column(name = "christams_eve")
	private boolean openForChristmasEve;
	@Column(name = "christmas_eve_open")
	private LocalTime christmasEveOpen;
	@Column(name = "christmas_eve_close")
	private LocalTime christmasEveClose;
	@Column(name = "christmas")
	private boolean openForChristmas;
	@Column(name = "christmas_open")
	private LocalTime christmasOpen;
	@Column(name = "christmas_close")
	private LocalTime christmasClose;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	@JsonIgnore
	private Store store;

	public HoursOfOperation(LocalTime mondayOpen, LocalTime tuesdayOpen, LocalTime wednesdayOpen,
			LocalTime thursdayOpen, LocalTime fridayOpen, LocalTime saturdayOpen, LocalTime sundayOpen,
			LocalTime mondayClose, LocalTime tuesdayClose, LocalTime wednesdayClose, LocalTime thursdayClose,
			LocalTime fridayClose, LocalTime saturdayClose, LocalTime sundayClose, boolean openForNewYearsEve,
			LocalTime newYearsEvenOpen, LocalTime newYearsEveClose, boolean openForNewYears, LocalTime newYearsOpen,
			LocalTime newYearsClose, boolean openForIndependenceDay, LocalTime independenceOpen,
			LocalTime independenceClose, boolean openForMemorialDay, LocalTime memorialDayOpen,
			LocalTime memorialDayClose, boolean openForEaster, LocalTime easterOpen, LocalTime easterClose,
			boolean openForColumbusDay, LocalTime columbusDayOpen, LocalTime columbusDayClose,
			boolean penForThanksgiving, LocalTime thanksgivingOpen, LocalTime thanksgivingClose,
			boolean openForChristmasEve, LocalTime christmasEveOpen, LocalTime christmasEveClose,
			boolean openForChristmas, LocalTime christmasOpen, LocalTime christmasClose, Store store) {
		super();
		this.mondayOpen = mondayOpen;
		this.tuesdayOpen = tuesdayOpen;
		this.wednesdayOpen = wednesdayOpen;
		this.thursdayOpen = thursdayOpen;
		this.fridayOpen = fridayOpen;
		this.saturdayOpen = saturdayOpen;
		this.sundayOpen = sundayOpen;
		this.mondayClose = mondayClose;
		this.tuesdayClose = tuesdayClose;
		this.wednesdayClose = wednesdayClose;
		this.thursdayClose = thursdayClose;
		this.fridayClose = fridayClose;
		this.saturdayClose = saturdayClose;
		this.sundayClose = sundayClose;
		this.openForNewYearsEve = openForNewYearsEve;
		this.newYearsEvenOpen = newYearsEvenOpen;
		this.newYearsEveClose = newYearsEveClose;
		this.openForNewYears = openForNewYears;
		this.newYearsOpen = newYearsOpen;
		this.newYearsClose = newYearsClose;
		this.openForIndependenceDay = openForIndependenceDay;
		this.independenceOpen = independenceOpen;
		this.independenceClose = independenceClose;
		this.openForMemorialDay = openForMemorialDay;
		this.memorialDayOpen = memorialDayOpen;
		this.memorialDayClose = memorialDayClose;
		this.openForEaster = openForEaster;
		this.easterOpen = easterOpen;
		this.easterClose = easterClose;
		this.openForColumbusDay = openForColumbusDay;
		this.columbusDayOpen = columbusDayOpen;
		this.columbusDayClose = columbusDayClose;
		this.penForThanksgiving = penForThanksgiving;
		this.thanksgivingOpen = thanksgivingOpen;
		this.thanksgivingClose = thanksgivingClose;
		this.openForChristmasEve = openForChristmasEve;
		this.christmasEveOpen = christmasEveOpen;
		this.christmasEveClose = christmasEveClose;
		this.openForChristmas = openForChristmas;
		this.christmasOpen = christmasOpen;
		this.christmasClose = christmasClose;
		this.store = store;
	}
}