package com.howtech.posscheduledbilling.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.howtech.posscheduledbilling.models.enums.EmployeeAccessPermissions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Damond Howard
 * @apiNote this entity maps to a store employee
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee extends Person {
	@Id
	@GeneratedValue
	private Long employeeId;
	@Column(name = "employee_user_id")
	private String employeeUserId;
	@Column(name = "code")
	private String code;
	@Column(name = "title")
	private String employeeTitle;
	@Column(name = "permissions")
	private EmployeeAccessPermissions employeePermissions;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Store store;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Store))
			return false;
		return employeeId != null && employeeId.equals(((Store) o).getStoreId());
	}

	@Override
	public int hashCode() {
		return 31;
	}
}
