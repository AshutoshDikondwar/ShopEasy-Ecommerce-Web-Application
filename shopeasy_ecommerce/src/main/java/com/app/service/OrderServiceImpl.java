package com.app.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.collections.Order;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.OrderDto;
import com.app.repository.OrderRepository;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderrepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public String createOrder(OrderDto orderdto) {
		Order order= mapper.map(orderdto,Order.class);
		orderrepository.save(order);
		return null;
	}

	@Override
	public String deleteOrder(String id) {
		 String mesg = "Please enter a valid id";
	        
         if (orderrepository.existsById(id)) {
             orderrepository.deleteById(id);
             mesg = "Order deleted successfully";
         }
         return mesg;
	}

	@Override
	public List<Order> getAllOrder() {
		return orderrepository.findAll();
	}

	@Override
	public Order getOrderById(String id) throws ResourceNotFoundException {
		return orderrepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Emp ID , Can't get emp details!!!!"));
	}

	@Override
	public Order updateOrder(OrderDto order) {
		// TODO Auto-generated method stub
		return null;
	}

}
