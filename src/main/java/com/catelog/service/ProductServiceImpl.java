package com.catelog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.catelog.constants.Constants;
import com.catelog.dto.ProductDTO;
import com.catelog.dto.UpdateDTO;
import com.catelog.entity.Product;
import com.catelog.exception.ProductException;
import com.catelog.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * This class contains the Product service methods which interacts with the
 * Repository
 * 
 *
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	/**
	 * This method will save the details in DB if the product is not already
	 * available in DB
	 * 
	 * @throws DataBaseErrorException
	 * @throws ProductAlreadyExistsException
	 */
	@Override
	public Product addProduct(ProductDTO productDTO) {

		Product product = new Product();

		Optional<Product> opt = productRepository.findById(productDTO.getProductName());

		if (opt.isPresent()) {
			log.error("Given product is already added!!!");
			throw new ProductException(Constants.ALREADY_EXISTS, HttpStatus.ALREADY_REPORTED);
		} else {
			log.info("Add product details is started");
			try {
				setProduct(productDTO, product);

				Product productSaved = productRepository.save(product);
				log.info("Product details are added successfully");
				return productSaved;
			} catch (ProductException ex) {
				log.error("Problem occured during adding product details to the database");
				throw new ProductException(Constants.DB_EXCEPTION, HttpStatus.CONFLICT);
			}

		}

	}

	private void setProduct(ProductDTO productDTO, Product product) {
		product.setProductName(productDTO.getProductName());
		product.setProductDescription(productDTO.getProductDescription());
		product.setContractSpend(productDTO.getContractSpend());
		product.setStakeholder(productDTO.getStakeholder());
		product.setCategoryLevel(productDTO.getCategoryLevel());
		product.setCatagoryDescription(productDTO.getCategoryDescription());
		product.setCreatedBy(productDTO.getCreatedBy());
		product.setCreatedTime(LocalDateTime.now());
		product.setLastUpdatedBy(productDTO.getLastUpdateBy());
		product.setLastUpdatedTime(LocalDateTime.now());
		product.setIsDeleted(false);
	}

	/**
	 * This method will get the product details from details and modify the details
	 * then save the details in DB .
	 * 
	 * @throws DataBaseErrorException
	 * @throws DataNotFoundException
	 */

	@Override
	public Product updateProduct(UpdateDTO updateDTO) {

		log.info("{} In service class,the modify method is started" + updateDTO);
		try {

			Optional<Product> opt = productRepository.findById(updateDTO.getId());

			if (opt.isPresent()) {

				log.info("Product details are ready to modify");
				Product getproduct = opt.get();
				getproduct.setProductName(updateDTO.getProductName());
				getproduct.setProductDescription(updateDTO.getProductDescription());
				getproduct.setContractSpend(updateDTO.getContractSpend());
				getproduct.setStakeholder(updateDTO.getStakeholder());
				getproduct.setCategoryLevel(updateDTO.getCategoryLevel());
				getproduct.setCatagoryDescription(updateDTO.getCategoryDescription());
				getproduct.setLastUpdatedBy(updateDTO.getLastUpdateBy());
				getproduct.setLastUpdatedTime(LocalDateTime.now());
				Product updatedproduct = productRepository.save(getproduct);
				log.info("{} Product details are modified successfully" + updateDTO);
				return updatedproduct;
			}

			else {
				log.error("No product details are available to modify");
				throw new ProductException(Constants.PRODUCT_NOT_FOUND, HttpStatus.BAD_REQUEST);
			}
		} catch (ProductException ex) {
			log.error("Problem occured in database during modify process");
			throw new ProductException(Constants.DB_EXCEPTION, HttpStatus.CONFLICT);
		}

	}

	/**
	 * Method to get the product by name (using word in any position of name)
	 *
	 * @param Product name
	 * @return Product
	 */
	@Override
	public List<Product> searchByName(String productName) {
		log.info("ProductService searchtByName  call started with {}", productName);
		if (StringUtils.isEmpty(productName)) {
			log.error("Product name is blank");
			throw new ProductException(Constants.PRODUCTNAME_EMPTY, HttpStatus.BAD_REQUEST);
		}
		try {
			List<Product> product = new ArrayList<Product>();
			product = productRepository.getProductByName(productName);
			log.info("ProductService searchtByName  call ended with {}", product);
			return product.stream().filter(a -> a.getIsDeleted().equals(false)).collect(Collectors.toList());
		} catch (Exception e) {
			throw new ProductException(Constants.DB_EXCEPTION, HttpStatus.CONFLICT);
		}

	}

	/**
	 * Method to delete the product
	 *
	 * @param id,lastUpdatedBy
	 * @return Product
	 */
	public Product deleteProduct(String id, String lastUpdatedBy) {
		log.info("--deleting product service call start with id {}", id);
		try {
			Optional<Product> productData = productRepository.findById(id);

			if (productData.isPresent()) {
				Product product = productData.get();

				product.setIsDeleted(true);
				product.setLastUpdatedBy(lastUpdatedBy);
				product.setLastUpdatedTime(LocalDateTime.now());
				Product updateddProduct = productRepository.save(product);

				return updateddProduct;

			} else {
				log.error("--product id not found--");
				throw new ProductException(Constants.NO_PRODUCT_DETAILS, HttpStatus.NOT_FOUND);

			}
		} catch (ProductException ex) {
			log.error(lastUpdatedBy);
			throw new ProductException(Constants.SAVE_FAILED, HttpStatus.BAD_REQUEST);
		}

	}
}
