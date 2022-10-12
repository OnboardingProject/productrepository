package com.catelog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.catelog.capabilitymatrixsoftwarecatelog.helper.ProductHelper;
import com.catelog.dto.ProductDTO;
import com.catelog.dto.UpdateDTO;
import com.catelog.entity.Product;
import com.catelog.exception.ProductException;
import com.catelog.repository.ProductRepository;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductServiceTest {

	@InjectMocks
	private ProductServiceImpl productServiceImpl;

	@Mock
	private ProductRepository productRepository;

	@Autowired
	MockMvc mockMvc;

	@Test
	private ProductDTO addProductTestData() {

		List<String> categorylevel = new LinkedList<String>();
		categorylevel.add("1-1-1");
		categorylevel.add("2-2-1");
		List<String> categoryleveldes = new LinkedList<String>();
		categoryleveldes.add("Category description");
		LocalDateTime time = LocalDateTime.now();
		ProductDTO productdto = new ProductDTO("Azure", 10f, 7, categorylevel, "viji", "raj", false, categoryleveldes,
				"Description");
		return productdto;
	}

	@Test
	private UpdateDTO getProductTestData() {

		List<String> categorylevel = new LinkedList<String>();
		categorylevel.add("1-1-1");
		categorylevel.add("2-2-1");
		List<String> categoryleveldes = new LinkedList<String>();
		categoryleveldes.add("Category description");
		LocalDateTime time = LocalDateTime.now();
		UpdateDTO updatedto = new UpdateDTO("Pjt111", "Azure", 10f, 6, categorylevel, "nisha", categoryleveldes,
				"Description");
		return updatedto;
	}

	private List<String> categoryLevel() {
		List<String> categoryLevel = new ArrayList<String>();
		categoryLevel.add("1-1-1");
		return categoryLevel;
	}

	private List<String> catagoryDescription() {
		List<String> category = new ArrayList<String>();
		category.add("desc");
		return category;
	}

	private List<Product> product() {
		List<Product> product = new ArrayList<Product>();
		product.add(new Product("632b231ad3433a705e3b30cd", "Azure Cognitive Sevices", 18f, 6, categoryLevel(),
				"athira", LocalDateTime.now(), "athira", LocalDateTime.now(), false, catagoryDescription(), "desc1"));
		return product;
	}

	@Test
	public void addProductTestSuccess() throws Exception {

		ProductDTO productdto = addProductTestData();
		LocalDateTime time = LocalDateTime.now();

		Product product = new Product("pjt111", productdto.getProductName(), productdto.getContractSpend(),
				productdto.getStakeholder(), productdto.getCategoryLevel(), productdto.getCreatedBy(), time,
				productdto.getLastUpdateBy(), time, false, productdto.getCategoryDescription(),
				productdto.getProductDescription());
		when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
		assertEquals(product, productServiceImpl.addProduct(productdto));

	}

	@Test
	public void addProductTestFailure() throws Exception {

		ProductDTO productdto = addProductTestData();
		LocalDateTime time = LocalDateTime.now();

		Product product = new Product("pjt111", productdto.getProductName(), productdto.getContractSpend(),
				productdto.getStakeholder(), productdto.getCategoryLevel(), productdto.getCreatedBy(), time,
				productdto.getLastUpdateBy(), time, false, productdto.getCategoryDescription(),
				productdto.getProductDescription());
		String Name = "pjt111";
		when(productRepository.findById(productdto.getProductName())).thenReturn(Optional.of(product));
		assertThrows(ProductException.class, () -> productServiceImpl.addProduct(productdto));
	}

	@Test()
	public void addDataBaseErrorExceptionTest() {
		ProductDTO productdto = addProductTestData();
		LocalDateTime time = LocalDateTime.now();
		List<String> category = Arrays.asList("1-1-1");
		List<String> categorydes = Arrays.asList("category description");
		Product product = new Product("pjt111", productdto.getProductName(), productdto.getContractSpend(),
				productdto.getStakeholder(), productdto.getCategoryLevel(), productdto.getCreatedBy(), time,
				productdto.getLastUpdateBy(), time, false, productdto.getCategoryDescription(),
				productdto.getProductDescription());
		when(productRepository.save(Mockito.any(Product.class))).thenThrow(ProductException.class);
		assertThrows(ProductException.class, () -> productServiceImpl.addProduct(productdto));
	}

	@Test
	public void updateProductTestSuccess() throws Exception {

		UpdateDTO updatedto = getProductTestData();
		LocalDateTime time = LocalDateTime.now();
		List<String> category = Arrays.asList("1-1-1");
		List<String> categorydes = Arrays.asList("category description");
		Product product = new Product("pjt111", "Azure", 10f, 7, category, "viji", time, "raj", time, false,
				categorydes, "product description");
		when(productRepository.findById(any())).thenReturn(Optional.of(product));

		product.setProductName(updatedto.getProductName());
		product.setContractSpend(updatedto.getContractSpend());
		product.setStakeholder(updatedto.getStakeholder());
		product.setCategoryLevel(updatedto.getCategoryLevel());
		product.setLastUpdatedBy(updatedto.getLastUpdateBy());

		when(productRepository.save(product)).thenReturn(product);
		assertEquals(product, productServiceImpl.updateProduct(updatedto));

	}

	@Test
	public void updateProductTestFailure() throws Exception {
		UpdateDTO updatedto = getProductTestData();
		String id = "pjt111";
		when(productRepository.findById(id)).thenReturn(Optional.empty());
		assertThrows(ProductException.class, () -> productServiceImpl.updateProduct(updatedto));

	}

	@Test()
	public void updateDataBaseErrorExceptionTest() {
		UpdateDTO updatedto = getProductTestData();
		LocalDateTime time = LocalDateTime.now();
		List<String> category = Arrays.asList("1-1-1");
		List<String> categorydes = Arrays.asList("category description");
		Product product = new Product("pjt111", "Azure", 10f, 7, category, "viji", time, "raj", time, false,
				categorydes, "Product description");
		// Product product1=new Product("","",0,0,null,"",null,"",null,false);
		when(productRepository.findById(any())).thenReturn(Optional.of(product));

		product.setProductName(updatedto.getProductName());
		product.setContractSpend(updatedto.getContractSpend());
		product.setStakeholder(updatedto.getStakeholder());
		product.setCategoryLevel(updatedto.getCategoryLevel());
		product.setLastUpdatedBy(updatedto.getLastUpdateBy());

		when(productRepository.save(product)).thenThrow(ProductException.class);
		assertThrows(ProductException.class, () -> productServiceImpl.updateProduct(updatedto));
	}

	@Test
	public void searchByNameTestSuccess() {
		when(productRepository.getProductByName(Mockito.anyString())).thenReturn(product());
		List<Product> product1 = productServiceImpl.searchByName("Azure");
		assertEquals("Azure Cognitive Sevices", product1.get(0).getProductName());
	}

	@Test
	public void searchByNameIsDeletedTestSuccess() {
		List<Product> product = new ArrayList<Product>();
		product.add(new Product("632b231ad3433a705e3b30cd", "Azure Cognitive Sevices", 18f, 6, categoryLevel(),
				"athira", LocalDateTime.now(), "athira", LocalDateTime.now(), false, catagoryDescription(), "desc1"));
		product.add(new Product("632b2359d3433a705e3b30ce", "Azure DevOps", 18f, 6, categoryLevel(), "aneesh",
				LocalDateTime.now(), "aneesh", LocalDateTime.now(), true, catagoryDescription(), "desc1"));
		when(productRepository.getProductByName(Mockito.anyString())).thenReturn(product);
		List<Product> products = productServiceImpl.searchByName("Azure");
		assertEquals(products.size(), 1);
	}

	@Test
	public void searchByNameTestFailure() {
		List<Product> product = new ArrayList<Product>();
		when(productRepository.getProductByName(Mockito.anyString())).thenReturn(product);
		List<Product> products = productServiceImpl.searchByName("A");
		assertEquals(products, product);
	}

	@Test
	public void searchByNameExceptionTest() {
		ProductException ex = assertThrows(ProductException.class, () -> productServiceImpl.searchByName(""));
		assertEquals("No product name given as input", ex.getErrorMessage());
	}

	@Test
	public void searchByNameDBExceptionTest() {
		when(productRepository.getProductByName(Mockito.anyString()))
				.thenThrow(new ProductException("DB Exception occured", HttpStatus.CONFLICT));
		ProductException ex = assertThrows(ProductException.class, () -> productServiceImpl.searchByName("M"));
		assertEquals("DB Exception occured", ex.getErrorMessage());
	}

	@Test
	public void deleteProductTestSuccess() throws Exception {

		ProductDTO productDTO = new ProductDTO("ID001", 10F, 7, Arrays.asList("1-1-1"), "user1", "user1", false,
				Arrays.asList("detailed description"), "product description");
		when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(ProductHelper.getProduct()));
		when(productRepository.save(Mockito.any())).thenReturn(ProductHelper.getProduct());
		Product productResponse = productServiceImpl.deleteProduct("pdt1", "user1");
		assertEquals(productResponse.getLastUpdatedBy(), "user1");

	}

	@Test
	public void deleteProduct_NullPointerException() {
		try {
			ProductDTO productDTO = null;
			productServiceImpl.deleteProduct(null, null);
		} catch (Exception e) {
			assertTrue(e instanceof ProductException);
		}
	}

	@Test
	public void deleteProduct_IdNotFoundException() {
		try {
			ProductDTO productDTO = new ProductDTO("ID001", 10F, 7, Arrays.asList("1-1-1"), "user1", "user1", false,
					Arrays.asList("detailed description"), "product description");
			productServiceImpl.deleteProduct("pdt1", "user1");
		} catch (Exception e) {
			assertTrue(e instanceof ProductException);
		}

	}

}
