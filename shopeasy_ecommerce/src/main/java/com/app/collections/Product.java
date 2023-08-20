package com.app.collections;


import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection="product")
public class Product {
	
	@Id
    private String id;
	private String prodName;
	private String description;
	private double price;
	private int rating;
	private Image image;
	private String Category;
	private Long stock;
	private int noOfReviews;
	private Reviews review;
//	private User user;
	private LocalDate createdAt;
	
	public Product() {
		
	}

	public Product(String prodName, String description, double price, int rating, Image image, String category,
			Long stock, int noOfReviews, Reviews review, LocalDate createdAt) {
		super();
		this.prodName = prodName;
		this.description = description;
		this.price = price;
		this.rating = rating;
		this.image = image;
		Category = category;
		this.stock = stock;
		this.noOfReviews = noOfReviews;
		this.review = review;
		this.createdAt = createdAt;
	}
}
