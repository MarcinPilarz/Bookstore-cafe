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

import springboot.bookstorecafe.DTO.OrderItemDTO;
import springboot.bookstorecafe.models.OrderItem;
import springboot.bookstorecafe.models.Reservation;
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

	 @GetMapping("/orders/person")
	    public ResponseEntity<List<OrderItem>> getPersonOrders(@RequestParam Long personId) {
	        try {
	            List<OrderItem> orders = orderService.getOrdersPerson(personId);
	            return ResponseEntity.ok(orders);
	        } catch (RuntimeException e) {
	            // Możesz dostosować obsługę błędów w zależności od Twoich potrzeb
	            return ResponseEntity.notFound().build();
	        }
	    }
	 
	@GetMapping(value="/ordersDTO")
	public List<OrderItemDTO> getOrdersDTO(){
		return mapOrderItemtoOrderItemInfo(orderService.findAllItems());
	}
	@PostMapping("/addOrder")
	public ResponseEntity<String> addOrder(
            @RequestParam Long idPerson,
            @RequestParam Long idProduct,
            @RequestParam int quantity,
            @RequestParam String token) {

        try {
            orderService.addItem(idPerson, idProduct, quantity, token);
            return ResponseEntity.ok("Zamówienie zostało pomyślnie złożone");
        } catch (Exception e) {
            // Obsługa błędów, np. niepowodzenie płatności lub błędy serwera
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nie udało się złożyć zamówienia: " + e.getMessage());
        }
    }
	
	@DeleteMapping(value="/deleteOrder")
	public ResponseEntity<String> deleteOrderItem(@RequestParam Long idOrderItem){
		
		orderService.deleteItem(idOrderItem);
		return ResponseEntity.noContent().build();
		
	}
	
}
