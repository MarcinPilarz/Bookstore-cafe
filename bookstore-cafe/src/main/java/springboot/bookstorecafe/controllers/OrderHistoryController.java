package springboot.bookstorecafe.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.models.OrderHistory;
import springboot.bookstorecafe.services.OrderHistoryService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class OrderHistoryController {

	@Autowired
	private OrderHistoryService orderService;
	

	@GetMapping("/history")
    public ResponseEntity<List<OrderHistory>> getHistoryByPerson(@RequestParam Long personId) {
        List<OrderHistory> orderHistory = orderService.findByPersonId(personId);
        return ResponseEntity.ok(orderHistory);
        
    }

	
	
}
