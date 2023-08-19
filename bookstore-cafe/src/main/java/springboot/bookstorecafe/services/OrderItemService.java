package springboot.bookstorecafe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.OrderItem;
import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.repositories.OrderItemRepository;
import springboot.bookstorecafe.repositories.PersonRepository;

@Service
public class OrderItemService {

	private OrderItemRepository orderRepo;

	private PersonRepository personRepo;

	@Autowired
	public OrderItemService(PersonRepository personRepo, OrderItemRepository orderRepo) {

		this.personRepo = personRepo;
		this.orderRepo = orderRepo;
	}

	public List<OrderItem> findAllItems() {

		return orderRepo.findAll();
	}

	public void addItem(Long idPerson) {
		Person person = personRepo.findById(idPerson)
				.orElseThrow(() -> new RuntimeException("There is no such person: " + idPerson));

		OrderItem orderItem = new OrderItem();

		orderItem.setPerson(person);

		orderRepo.save(orderItem);

	}

	public void deleteItem(OrderItem obejct) {
		// TODO Auto-generated method stub

	}

	public void updateItem(OrderItem object) {
		// TODO Auto-generated method stub

	}

	public OrderItem findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
