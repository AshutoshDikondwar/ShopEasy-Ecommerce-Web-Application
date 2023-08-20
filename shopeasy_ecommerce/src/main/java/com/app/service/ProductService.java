package com.app.service;

import java.util.List;

import com.app.collections.Product;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.ProductDto;

public interface ProductService {
	
	public String createProduct(ProductDto productdto);
	
	public String deleteProduct(String id);
	
	List<Product>getAllProduct();
	
	Product getProductById(String id) throws ResourceNotFoundException;
	
	public Product updateProduct( ProductDto product);
}
