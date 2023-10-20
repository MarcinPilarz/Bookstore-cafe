package springboot.bookstorecafe.services;

import java.util.List;

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
	
	
	public List<OrderHistory> findAllHistory(Long idPerson){
		
		Person person=personRepo.findById(idPerson).orElse(null);
		
		return null;
	//	return orderHistoryRepo.findByHistoryOrders(person);
		
		
//		if(person !=null) {
//			//return orderHistoryRepo.findById(person);
//		}
		
		//return person;
		
	}
}
