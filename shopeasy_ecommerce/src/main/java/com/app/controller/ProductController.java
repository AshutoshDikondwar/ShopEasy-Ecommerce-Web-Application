package com.app.controller;

import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.collections.Product;
import com.app.custom_exceptions.ErrorHandler;
import com.app.custom_exceptions.MalFormedTokenException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.custom_exceptions.TokenExpiredException;
import com.app.dto.ALlProductandCountDTO;
import com.app.dto.ProductDto;
import com.app.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productservice;

	@PostMapping("/create")
	public String createProduct(@RequestBody ProductDto productdto, @CookieValue(name = "tokenjwt", required = false) String tokenjwt,
			HttpSession session) throws ResourceNotFoundException, AccessDeniedException, TokenExpiredException,
			MalFormedTokenException, ErrorHandler {
		System.out.println("token=====" + tokenjwt);
		return productservice.createProduct(productdto, tokenjwt, session);
	}

	@DeleteMapping("/deleteProduct")
	public String deleteProduct(@RequestParam String id) {
		return productservice.deleteProduct(id);
	}

	@GetMapping("/all")
	public ALlProductandCountDTO getAllProduct(@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String category, @RequestParam(required = false) Double lt,
			@RequestParam(required = false) Double lte, @RequestParam(required = false) Double gt,
			@RequestParam(required = false) Double gte, @RequestParam(required = false) Integer page

	) {
		System.out.println("keyword " + keyword + " Category " + category);
		return productservice.getAllProduct(keyword, category, lt, lte, gt, gte, page);
	}

	@GetMapping("/getProductById")
	public Product getProductById(@RequestParam String id) throws ResourceNotFoundException {
		return productservice.getProductById(id);
	}

	@PutMapping("/updateProduct")
	public Product updateProduct(@RequestBody ProductDto product) {
		return productservice.updateProduct(product);
	}
}
