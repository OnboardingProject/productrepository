package com.catelog.entity;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the entity class for Product
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "ProductDetails")

public class Product {
	@Id
	private String productId;
    private String productName;
	private Float contractSpend;
	private int stakeholder;
	private List<String> categoryLevel;
	private String createdBy;
	private LocalDateTime createdTime;
	private String lastUpdatedBy;
	private LocalDateTime lastUpdatedTime;
	private Boolean isDeleted;
	private List<String> catagoryDescription;
	private String productDescription;
	

}
