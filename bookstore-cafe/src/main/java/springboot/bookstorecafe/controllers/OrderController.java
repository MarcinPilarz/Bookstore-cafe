package springboot.bookstorecafe.controllers;

import java.time.LocalDateTime;  
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;

import springboot.bookstorecafe.DTO.OrderItemDTO;
import springboot.bookstorecafe.models.OrderItem;
import springboot.bookstorecafe.models.Reservation;
import springboot.bookstorecafe.models.WholeOrderPerson;
import springboot.bookstorecafe.services.OrderService;
//import static springboot.bookstorecafe.DTO.mapper.OrderItemDTOMapper.mapOrderItemtoOrderItemInfo;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
//	@GetMapping(value="/orders")
//	public List<OrderItem> getOrdersByPerson(){
//		return orderService.findAllItems();
//	}
	
	
//	@GetMapping("/orders")
//	public ResponseEntity<List<OrderItem>> getOrdersByPerson() {
//	    List<OrderItem> orders = orderService.findAllItems();
//	    if (orders != null) {
//	        return ResponseEntity.ok(orders);
//	    } else {
//	        return ResponseEntity.notFound().build();
//	    }
//	}

//	 @GetMapping("/orders/person")
//	    public ResponseEntity<List<OrderItem>> getPersonOrders(@RequestParam Long personId) {
//	        try {
//	            List<OrderItem> orders = orderService.getOrdersPerson(personId);
//	            return ResponseEntity.ok(orders);
//	        } catch (RuntimeException e) {
//	            // Możesz dostosować obsługę błędów w zależności od Twoich potrzeb
//	            return ResponseEntity.notFound().build();
//	        }
//	    }
	 
//	@GetMapping(value="/ordersDTO")
//	public List<OrderItemDTO> getOrdersDTO(){
//		return mapOrderItemtoOrderItemInfo(orderService.findAllItems());
//	}
	
	
	
	@GetMapping("/orders")
	public ResponseEntity<List<WholeOrderPerson>> getOrdersByPerson() {
	    List<WholeOrderPerson> orders = orderService.findAllOrders();
	    if (orders != null) {
	        return ResponseEntity.ok(orders);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PostMapping("/addOrder")
	public ResponseEntity<String> createOrder(@RequestParam Long idPerson,
            @RequestParam List<Long> idProduct,
            @RequestParam List<Integer> quantity,
            @RequestParam String token) {
try {
orderService.addItems(idPerson, idProduct, quantity, token);
return ResponseEntity.ok("Zamówienie zostało pomyślnie utworzone.");
} catch (StripeException e) {
return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Błąd płatności: " + e.getMessage());
} catch (RuntimeException e) {
return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Błąd: " + e.getMessage());
}
	}
	@DeleteMapping(value="/deleteOrder")
	public ResponseEntity<String> deleteOrderItem(@RequestParam Long idOrderItem){
		
		orderService.deleteItem(idOrderItem);
		return ResponseEntity.noContent().build();
		
	}
	
}
