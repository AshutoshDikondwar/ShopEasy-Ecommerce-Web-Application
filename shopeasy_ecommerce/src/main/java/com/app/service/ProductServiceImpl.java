package com.app.service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.collections.Product;
import com.app.collections.User;
import com.app.custom_exceptions.ErrorHandler;
import com.app.custom_exceptions.MalFormedTokenException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.custom_exceptions.TokenExpiredException;
import com.app.dto.ALlProductandCountDTO;
import com.app.dto.ProductDto;
import com.app.jwt.SaveCookie;
import com.app.repository.ProductRepository;
import com.app.repository.UserRepository;
import com.app.util.JwtUtil;

import io.jsonwebtoken.Claims;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserRepository userRepo;

	@Override
	public String createProduct(ProductDto productdto, String tokenjwt, HttpSession session)
			throws ResourceNotFoundException, AccessDeniedException, TokenExpiredException, MalFormedTokenException, ErrorHandler {



//		// Authorize user("Admin")
		User storedUser = (User) session.getAttribute("user");
		String userRole = storedUser.getRole();
		if (userRole.equals("Admin")) {

		} else {
			throw new ErrorHandler(storedUser + " is not allowed to access this resource");
		}

		Product prod = mapper.map(productdto, Product.class);
		productRepo.save(prod);
		return "product added successfully ";
	}

	@Override
	public String deleteProduct(String id) {
		String mesg = "Please enter a valid id";

		if (productRepo.existsById(id)) {
			productRepo.deleteById(id);
			mesg = "Product deleted successfully";
		}
		return mesg;
	}

	@Override
	public ALlProductandCountDTO getAllProduct(String keyword, String category, Double lt, Double lte, Double gt,
			Double gte, Integer page) {
		List<Product> product = productRepo.findAll();
		int count = (int) productRepo.count();

//		Set<Product> p = new HashSet<>();

		Map<String, Product> m = new HashMap<>();

		if (keyword != null && category != null) {

			productRepo.findByRegexNameAndCat(keyword, category).forEach(pm -> m.put(pm.getId(), pm));
		} else if (keyword != null) {

			productRepo.findByRegexName(keyword).forEach(pm -> m.put(pm.getId(), pm));
		} else if (category != null) {

			productRepo.findByRegexCat(category).forEach(pm -> m.put(pm.getId(), pm));
		}

		if (gt != null && lt != null) {

			productRepo.findByPriceGreaterThanAndLessThan(gt, lt).forEach(pm -> m.put(pm.getId(), pm));
		} else if (gte != null && lte != null) {

			productRepo.findByPriceGreaterThanEqualtoAndLessThanEqualto(gte, lte).forEach(pm -> m.put(pm.getId(), pm));
		} else if (gte != null && lt != null) {

			productRepo.findByPriceGreaterThanEqualtoAndLessThan(gte, lt).forEach(pm -> m.put(pm.getId(), pm));
		} else if (gt != null && lte != null) {

			productRepo.findByPriceGreaterThanAndLessThanEqualto(gt, lte).forEach(pm -> m.put(pm.getId(), pm));
		} else if (lt != null) {

			productRepo.findByPriceLessThan(lt).forEach(pm -> m.put(pm.getId(), pm));
		} else if (lte != null) {

			productRepo.findByPriceLessThanEqual(lte).forEach(pm -> m.put(pm.getId(), pm));
		} else if (gt != null) {

			productRepo.findByPriceGreaterThan(gt).forEach(pm -> m.put(pm.getId(), pm));

			productRepo.findByPriceGreaterThanEqual(gte).forEach(pm -> m.put(pm.getId(), pm));
		}

		if (m.isEmpty()) {
			// PAGINATION
			int resultPerPage = 5;
			int currentPage;
			if (page == null) {
				currentPage = 0;
			} else {
				currentPage = page;
			}
			Pageable pageable = PageRequest.of(currentPage, resultPerPage);
			Page<Product> pg = productRepo.findAll(pageable);
			return new ALlProductandCountDTO(pg.getContent(), count);
		}

//		return new ArrayList<>(p);
		return new ALlProductandCountDTO(new ArrayList<>(m.values()), count);

	}

	@Override
	public Product getProductById(String id) throws ResourceNotFoundException {
		return productRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Emp ID , Can't get emp details!!!!"));
	}

	@Override
	public Product updateProduct(ProductDto product) {

		Optional<Product> productDb = this.productRepo.findByProdName(product.getProdName());
//		Optional<Product> prod = productRepo.findByProdName(product.getProdName());

		if (productDb.isPresent()) {
			Product productUpdate = productDb.get();
//		            productUpdate.setProdName(product.getProdName());
			productUpdate.setCategory(product.getCategory());
			productUpdate.setDescription(product.getDescription());
			productUpdate.setCreatedAt(product.getCreatedAt());
			productUpdate.setImage(product.getImage());
			productUpdate.setPrice(product.getPrice());
			productUpdate.setStock(product.getStock());

			return productRepo.save(productUpdate);
		}
		return null;

	}

}
