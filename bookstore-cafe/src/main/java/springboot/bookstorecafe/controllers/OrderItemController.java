package springboot.bookstorecafe.controllers;

import java.time.LocalDateTime; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.DTO.OrderItemDTO;
import springboot.bookstorecafe.models.OrderItem;
import springboot.bookstorecafe.services.OrderItemService;
import static springboot.bookstorecafe.DTO.mapper.OrderItemDTOMapper.mapOrderItemtoOrderItemInfo;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class OrderItemController {

	@Autowired
	private OrderItemService orderService;
	
//	@GetMapping(value="/orders")
//	public List<OrderItem> getOrdersByPerson(){
//		return orderService.findAllItems();
//	}
	
	
	@GetMapping("/orders")
	public ResponseEntity<List<OrderItem>> getOrdersByPerson() {
	    List<OrderItem> orders = orderService.findAllItems();
	    if (orders != null) {
	        return ResponseEntity.ok(orders);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	@GetMapping(value="/ordersDTO")
	public List<OrderItemDTO> getOrdersDTO(){
		return mapOrderItemtoOrderItemInfo(orderService.findAllItems());
	}
	@PostMapping(value="/addOrder")
	public ResponseEntity<String> addOrderItem(@RequestParam Long idPerson, @RequestParam Long idProduct, LocalDateTime dateOrder, int quantity){
	
		//OrderItem orderItem= new OrderItem();
		
	
		orderService.addItem(idPerson, idProduct, quantity, null);
		return ResponseEntity.ok("Order item added successfully");
	
	}
	
	@DeleteMapping(value="/deleteOrder")
	public ResponseEntity<String> deleteOrderItem(@RequestParam Long idOrderItem){
		
		orderService.deleteItem(idOrderItem);
		return ResponseEntity.noContent().build();
		
	}
	
}
