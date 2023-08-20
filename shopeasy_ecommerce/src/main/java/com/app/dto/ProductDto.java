package com.app.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import com.app.collections.Image;
import com.app.collections.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ProductDto {
	@NotBlank(message="Product name can't blank")
	private String prodName;
	private String description;
	@NotBlank(message="plz select the price")
	private double price;
	private Image image;
	@NotBlank(message="plz select the category")
	private String Category;
	private Long stock;
	
	private LocalDate createdAt;
}
