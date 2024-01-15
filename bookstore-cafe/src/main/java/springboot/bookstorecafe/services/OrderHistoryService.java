package springboot.bookstorecafe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.OrderHistory;

import springboot.bookstorecafe.repositories.OrderHistoryRepository;

@Service
public class OrderHistoryService {

	@Autowired
	private OrderHistoryRepository orderHistoryRepo;

	public List<OrderHistory> findByPersonId(Long personId) {
		return orderHistoryRepo.findByPersonIdPerson(personId);

	}
}
