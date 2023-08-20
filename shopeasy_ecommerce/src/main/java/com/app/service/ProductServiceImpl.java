package com.app.service;




import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.collections.Product;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.ProductDto;
import com.app.repository.ProductRepository;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public String createProduct(ProductDto productdto) {
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
	public List<Product> getAllProduct() {
		return productRepo.findAll();
	}
	
	@Override
	public Product getProductById(String id) throws ResourceNotFoundException {
		return productRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Emp ID , Can't get emp details!!!!"));
	}

	@Override
	public Product updateProduct( ProductDto product) {
		
		
		Optional < Product > productDb = this.productRepo.findByProdName(product.getProdName());
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
