package com.catelog.controller;

import java.util.List;


import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.catelog.dto.ProductDTO;
import com.catelog.dto.UpdateDTO;
import com.catelog.entity.Product;
import com.catelog.service.ProductService;
import lombok.extern.slf4j.Slf4j;



/**
 * This class is the controller class for all the product API end
 * points 
 */
@Slf4j
@RestController
@RequestMapping(value="/api/v1/product")
public class ProductController {  
	
	@Autowired
	ProductService productService;
	
	@PostMapping("/add")
	public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDTO productDTO)
	{
		Product product=productService.addProduct(productDTO);
		
		return new ResponseEntity<Product>(product,HttpStatus.CREATED);
		
	}
	
	/**
	 * This method will update a product and also validate incoming request
	 * 
	 * @param UpdateDTO
	 * @return response entity representation of UpdateDTO
	 */
	@PutMapping("/update")
	public ResponseEntity<?> updateProduct(@Valid @RequestBody UpdateDTO updateDTO)
	{
		Product productUpdate=productService.updateProduct(updateDTO);
		
		if(ObjectUtils.isEmpty(productUpdate))
		{
			return new ResponseEntity<>("No updated data",HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<Product>(productUpdate,HttpStatus.OK);
		
	}
	

	/**
	 * Method to add the get product with letter/word of name
	 * 
	 * @param product name
	 * @return product
	 */
	@GetMapping("/searchByWord")
	public ResponseEntity<List<Product>> searchByName(@RequestParam(required = true) String productName) {
		log.info("ProductController searchByName call started with {}",productName);
		List<Product> products = productService.searchByName(productName);

		if (!CollectionUtils.isEmpty(products)) {
			log.info("ProductController searchByName call ended with {}",products);
			return new ResponseEntity<>(products, HttpStatus.OK);
		} else
			log.info("No product exist with the name {}",productName);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Method to delete the product
	 *
	 * @param id and last updated by user
	 * @return product data
	 */
	@PutMapping("/deleteProduct/id/{id}/lastUpdatedBy/{lastUpdatedBy}")
	public ResponseEntity<Product> deleteProduct(@PathVariable(required = true) String id, String lastUpdatedBy) {
		log.info("--delete product controller call start with id {} and lastUpdatedBy {}", id, lastUpdatedBy);
		Product productData = productService.deleteProduct(id, lastUpdatedBy);
		log.info("--delete product controller call end--");
		return new ResponseEntity<Product>(productData, HttpStatus.OK);

	}
}
