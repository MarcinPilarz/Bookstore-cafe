package springboot.bookstorecafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.models.OrderItem;
import springboot.bookstorecafe.services.OrderItemService;

@RestController
public class OrderItemController {

	@Autowired
	private OrderItemService orderService;
	
	@GetMapping(value="/orders")
	public List<OrderItem> getOrdersByPerson(){
		return orderService.findAllItems();
	}
	@PostMapping(value="/addOrder")
	public ResponseEntity<String> addOrderItem(@RequestParam Long idPerson, @RequestParam Long idProduct){
	
		orderService.addItem(idPerson, idProduct);
		return ResponseEntity.ok("Order item added successfully");
	
	}
	
}
