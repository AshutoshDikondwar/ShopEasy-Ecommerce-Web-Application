package com.app.service;

import java.util.List;

import com.app.collections.Order;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.OrderDto;

public interface OrderService {
	public String createOrder(OrderDto orderdto);
	
	public String deleteOrder(String id);
	
	public List<Order>getAllOrder();
	
	public Order getOrderById(String id) throws ResourceNotFoundException;
	
	public Order updateOrder(OrderDto order);
	
}
