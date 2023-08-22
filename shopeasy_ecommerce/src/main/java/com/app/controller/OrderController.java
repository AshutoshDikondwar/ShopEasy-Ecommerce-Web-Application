package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.collections.Order;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.OrderDto;

import com.app.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderservice;
	
	@PostMapping("/createorder")
	public String createOrder( @RequestBody OrderDto orderdto) {
		return	orderservice.createOrder(orderdto);
	}
	
	@GetMapping("/getOrders")
	public List<Order>getAllOrders(){
		return orderservice.getAllOrder();
	}
	
	@GetMapping("/getOrderById")
	public Order getOrderById(@RequestParam String id) throws ResourceNotFoundException {
		return orderservice.getOrderById(id);
	}
	
	@DeleteMapping("/deleteOrder")
	public String deleteOrder(@RequestParam String id) {
		return orderservice.deleteOrder(id);
	}
	
	@PutMapping("/updateOrder")
	public Order updateOrder(@RequestBody OrderDto orderdto) {
		return orderservice.updateOrder(orderdto);
	}
	
	

}
