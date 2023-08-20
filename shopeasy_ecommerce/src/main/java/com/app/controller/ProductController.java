package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.collections.Product;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.ProductDto;
import com.app.service.ProductService;


@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productservice;
	
	@PostMapping("/createProduct")
	public String createProduct( @RequestBody ProductDto productdto) {
	return	productservice.createProduct(productdto);
	}
	
	@DeleteMapping("/deleteProduct")
	public String deleteProduct(@RequestParam String id) {
		return productservice.deleteProduct(id);
	}
	
	@GetMapping("/getAllProducts")
	public List<Product>getAllProduct(){
		return productservice.getAllProduct();
	}
	
	@GetMapping("/getProductById")
	public Product getProductById(@RequestParam String id) throws ResourceNotFoundException {
		return productservice.getProductById(id);
	}
	
	@PutMapping("/updateProduct")
	public Product updateProduct( @RequestBody ProductDto product) {
		return productservice.updateProduct( product);
	}
}
