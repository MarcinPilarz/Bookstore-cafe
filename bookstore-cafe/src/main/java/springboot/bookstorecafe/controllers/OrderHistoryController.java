package springboot.bookstorecafe.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.models.OrderHistory;
import springboot.bookstorecafe.services.OrderHistoryService;

@RestController
public class OrderHistoryController {

	@Autowired
	private OrderHistoryService orderService;
	
//	@GetMapping(value="/history")
//	public List<OrderHistory> getOrdersHistory(@RequestParam Long idPerson){
//		
//		List<OrderHistory> history= orderService.get
//		return orderService.findAllHistory(idPerson);
//	}
	
//	 @GetMapping("/byperson")
//	    public ResponseEntity<List<OrderHistory>> getSampleDataByPersonId(@RequestParam Long idPerson) {
//	        List<OrderHistory> data = orderService.findPersonById(idPerson);
//	        if (!data.isEmpty()) {
//	            return ResponseEntity.ok(data);
//	        } else {
//	            return ResponseEntity.notFound().build();
//	        }
//	    }
	@GetMapping("/history/{personId}")
    public ResponseEntity<List<OrderHistory>> getHistoryByPerson(@PathVariable Long personId) {
        List<OrderHistory> orderHistoryList = orderService.findByPersonId(personId);
        return ResponseEntity.ok(orderHistoryList);
    }
//	@GetMapping("/history")
//	public List<OrderHistory> getHistoryPerson(@RequestParam("personId") Long personId){
//		
//		return orderService.findPerson(personId);
//	}
}
