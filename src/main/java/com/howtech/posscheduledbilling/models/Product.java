package com.howtech.posscheduledbilling.models;

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.howtech.posscheduledbilling.models.enums.ProductCategory;
import com.howtech.posscheduledbilling.models.enums.ProductSubCategory;
import com.howtech.posscheduledbilling.models.enums.ProductType;
import com.howtech.posscheduledbilling.models.enums.ProductUnit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Damond Howard
 * @apiNote this is an entity for managing the products table
 *
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue
	@Column(name = "product_id")
	private Long productId;
	@Column(name = "description")
	private String productDescription;
	@Column(name = "product_name")
	private String productName;
	@Column(name = "product_brand")
	private String productBrand;
	@Column(name = "img_url")
	private String imageURL;
	@Column(name = "product_unit")
	private ProductUnit productUnit;
	@Column(name = "unit_price")
	private BigDecimal productPrice;
	@Column(name = "quantity")
	private int productQuantity;
	@Enumerated(EnumType.STRING)
	private ProductType productType;
	@Enumerated(EnumType.STRING)
	private ProductCategory productCategory;
	@Enumerated(EnumType.STRING)
	private ProductSubCategory productSubCategory;
	@ElementCollection
	@MapKeyColumn(name = "description_category")
	@Column(name = "description_tag")
	private Map<String, String> descriptions;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Store store;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Store))
			return false;
		return productId != null && productId.equals(((Store) o).getStoreId());
	}

	@Override
	public int hashCode() {
		return 31;
	}
}