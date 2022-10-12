package com.catelog.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the DTO class representing Product
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {	
	
	private String productName;	
	private Float contractSpend;
	private int stakeholder;
	@NotNull
	private List<String> categoryLevel;
	private String createdBy;	
	private String lastUpdateBy;	
	private boolean isDeleted;
	private List<String> categoryDescription;
	private String productDescription;
	
	
	
}
