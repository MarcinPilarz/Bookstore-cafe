package springboot.bookstorecafe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.OrderHistory;
import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.repositories.OrderHistoryRepository;
import springboot.bookstorecafe.repositories.PersonRepository;

@Service
public class OrderHistoryService {

	@Autowired
	private OrderHistoryRepository orderHistoryRepo;
	
	@Autowired
	private PersonRepository personRepo;
	
	
//	public List<OrderHistory> findAllHistory(Long idPerson){
//		
//		return orderHistoryRepo.findAll();
//	//	return orderHistoryRepo.findByHistoryOrders(person);
//		
//		
////		if(person !=null) {
////			//return orderHistoryRepo.findById(person);
////		}
//		
//		//return person;
//		
//	}
//	
//	public List<OrderHistory> findPersonById(Long idPerson){
//		
//	 return orderHistoryRepo.findByPerson(idPerson);
//	
//	}
	
	public List<OrderHistory> findPerson(Long personId){
		return orderHistoryRepo.findByPersonNative(personId);
	}
	
	 public List<OrderHistory> findByPersonId(Long personId) {
	        return orderHistoryRepo.findByPersonIdPerson(personId);
	    }
}
