package com.app.service;

import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.collections.Product;
import com.app.custom_exceptions.ErrorHandler;
import com.app.custom_exceptions.MalFormedTokenException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.custom_exceptions.TokenExpiredException;
import com.app.dto.ALlProductandCountDTO;
import com.app.dto.ProductDto;

public interface ProductService {

	public String createProduct(ProductDto productdto, String token, HttpSession session)
			throws ResourceNotFoundException, AccessDeniedException, TokenExpiredException, MalFormedTokenException,
			ErrorHandler;

	public String deleteProduct(String id);

	ALlProductandCountDTO getAllProduct(String keyword, String category, Double lt, Double lte, Double gt, Double gte,
			Integer page);

	Product getProductById(String id) throws ResourceNotFoundException;

	public Product updateProduct(ProductDto product);
}
