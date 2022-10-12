package com.catelog.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the DTO class representing Update fields
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UpdateDTO {
	
	@NotNull(message="Id field must be not null")
	private String id;
	private String productName;
	private Float contractSpend;
	private int stakeholder;
	private List<String> categoryLevel;	
	private String lastUpdateBy;
	private List<String> categoryDescription;
	private String productDescription;

	
}
