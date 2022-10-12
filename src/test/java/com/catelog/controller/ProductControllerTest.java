package com.catelog.controller;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.catelog.dto.ProductDTO;
import com.catelog.dto.UpdateDTO;
import com.catelog.entity.Product;
import com.catelog.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {
	
	@InjectMocks
	private ProductController productController; 
	
	@Mock 
	private ProductService productService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	public void init()
	{
		MockitoAnnotations.initMocks(this);
		mockMvc=MockMvcBuilders.standaloneSetup(productController).build();
	}
	private List<String> categoryLevel() {
		List<String> categoryLevel = new ArrayList<String>();
		categoryLevel.add("1-1-1");
		return categoryLevel;
	}
	private List<String> catagoryDescription() {
		List<String> category= new ArrayList<String>();
		category.add("desc");
		return category;
	}

	private List<Product> product() {
		List<Product> product = new ArrayList<Product>();
		product.add(new Product("632b231ad3433a705e3b30cd", "Azure Cognitive Sevices", 18f, 6, categoryLevel(),
				"athira", LocalDateTime.now(), "athira", LocalDateTime.now(), false,catagoryDescription(),"desc1"));
		return product; 
	}
	
	LocalDateTime time=LocalDateTime.now(); 
	@Test
	private ProductDTO addProductTestData() {

		List<String> categorylevel = new LinkedList<String>();
		categorylevel.add("1-1-1");
		categorylevel.add("2-2-1");
		List<String> categoryleveldes = new LinkedList<String>();
		categoryleveldes.add("Category description");
		LocalTime time = LocalTime.now();
		ProductDTO productdto = new ProductDTO("Azure",10f,7,categorylevel,"viji","raj",false,categoryleveldes,"Description");
		return productdto; 
	}
	
	
	
	
	private UpdateDTO updateProductTestData() {

		List<String> categorylevel = new LinkedList<String>();
		categorylevel.add("1-1-1");
		categorylevel.add("2-2-1");
		List<String> categoryleveldes = new LinkedList<String>();
		categoryleveldes.add("Category description");
		LocalDateTime time = LocalDateTime.now();
		UpdateDTO updatedto = new UpdateDTO("Pjt111", "Azure", 10f,6, categorylevel,"nisha",categoryleveldes,"Description");
		return updatedto;
	}
	/**{@link ProductController #updateProduct(UpdateDTO) with response}
	 * 
	 */
	@Test
    public void addProjectTest_success() throws Exception {
    	
		ProductDTO productdto=addProductTestData();	
		List<String>category=Arrays.asList("1-1-1");
		List<String> categoryleveldes = new LinkedList<String>();
		categoryleveldes.add("Category description");
		LocalDateTime time = LocalDateTime.now();
		Product product=new Product("pjt111","Azure",10f,7,category,"viji",time,"raj",time,false,categoryleveldes,"product description");
    	
        when(productService.addProduct(Mockito.any(ProductDTO.class))).thenReturn(product);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product/add")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(productdto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
	/**{@link ProductController #addProduct(ProductDTO) with exception}
	 * 
	 */
	@Test
	public void addProjectTest_Failure() throws Exception{
		LocalDateTime time = LocalDateTime.now();
		List<String>category=Arrays.asList("1-1-1");
		List<String> categoryleveldes = new LinkedList<String>();
		categoryleveldes.add("Category description");
		ProductDTO productdto1 = new ProductDTO("",10f,7,null,"viji","raj",false,categoryleveldes,"Description");	
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product/add")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(productdto1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
 
	}
    
	/**{@link ProductController #updateProduct(UpdateDTO)with response}
	 * 
	 */
	@Test
    public void modifyProductTest() throws Exception {
		LocalDateTime time = LocalDateTime.now();
		UpdateDTO updatedto=updateProductTestData();	
		List<String>category=Arrays.asList("1-1-1");
		List<String> categoryleveldes = new LinkedList<String>();
		categoryleveldes.add("Category description");
		Product product=new Product("pjt111","Azure",10f,7,category,"viji",time,"raj",time,false,categoryleveldes,"product description");
    	updatedto.setProductName("Azure");
    	updatedto.setProductDescription("product description");
    	updatedto.setContractSpend(10f);
    	updatedto.setStakeholder(5);
    	updatedto.setCategoryLevel(category);
    	updatedto.setCategoryDescription(categoryleveldes);
    	updatedto.setLastUpdateBy("nisha");  
    	
        when(productService.updateProduct(Mockito.any(UpdateDTO.class))).thenReturn(product);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/product/update")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(updatedto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
	/**{@link ProductController #updateProduct(UpdateDTO) with exception}
	 * 
	 */
	@Test
	public void modifyProjectTest_failure() throws Exception{
		List<String>category=Arrays.asList("1-1-1");
		List<String> categoryleveldes = new LinkedList<String>();
		categoryleveldes.add("Category description");
		 when(productService.updateProduct(Mockito.any(UpdateDTO.class))).thenReturn(null);
		UpdateDTO updatedto1 = new UpdateDTO("Pjt111", "Azure", 10f,6, category,"nisha",categoryleveldes,"Description");
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/product/update").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(updatedto1)))
				.andExpect(MockMvcResultMatchers.status().isConflict());
 
	}
	/**{@link ProductController #searchByName(String) with response}
	 * 
	 */
	@Test
	public void searchByNameTestSucess() throws JsonProcessingException, Exception {

		when(productService.searchByName(Mockito.anyString())).thenReturn(product());
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/searchByWord").param("productName", "Azure")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

	}
	/**{@link ProductController #searchByName(String) with exception}
	 * 
	 */
	@Test
	public void searchByNameTestFailure() throws JsonProcessingException, Exception {
		when(productService.searchByName(Mockito.anyString())).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/searchByWord").param("productName", "Teams")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	/**{@link ProductController #deleteProduct(String, String) with response}
	 * 
	 */
    
	@Test
	public void deleteProductTest() throws Exception {

		ProductDTO productDTO = addProductTestData();
		productDTO.setDeleted(true);
		productDTO.setLastUpdateBy("user1");

		Product product = new Product("pjt111", "Azure", 10f, 7, categoryLevel(), "viji", time, "raj", time, false,
				categoryLevel(), "product description");

		when(productService.deleteProduct(Mockito.anyString(), Mockito.anyString())).thenReturn(product);
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/product/deleteProduct/id/ID001/lastUpdatedBy/user1")
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

	}


}
