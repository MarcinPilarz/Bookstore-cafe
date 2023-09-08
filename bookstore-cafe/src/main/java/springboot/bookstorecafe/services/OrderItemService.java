package springboot.bookstorecafe.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.Book;
import springboot.bookstorecafe.models.Coffee;
import springboot.bookstorecafe.models.Food;
import springboot.bookstorecafe.models.OrderItem;
import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.repositories.BookRepository;
import springboot.bookstorecafe.repositories.CoffeeRepository;
import springboot.bookstorecafe.repositories.FoodRepository;
import springboot.bookstorecafe.repositories.OrderItemRepository;
import springboot.bookstorecafe.repositories.PersonRepository;
import springboot.bookstorecafe.repositories.ProductRepository;

@Service
public class OrderItemService {

	private OrderItemRepository orderRepo;

	private PersonRepository personRepo;

	private CoffeeRepository coffeeRepo;

	private BookRepository bookRepo;

	private FoodRepository foodRepo;

//	@Autowired
//	private ProductRepository productRepo;
	@Autowired
	public OrderItemService(PersonRepository personRepo, OrderItemRepository orderRepo, CoffeeRepository coffeeRepo,
			BookRepository bookRepo, FoodRepository foodRepo) {

		this.personRepo = personRepo;
		this.orderRepo = orderRepo;
		this.coffeeRepo = coffeeRepo;
		this.bookRepo = bookRepo;
		this.foodRepo = foodRepo;
	}

	public List<OrderItem> findAllItems() {

		return orderRepo.findAll();
	}

	public void addItem(Long idPerson, Long idProduct, LocalDateTime currentOrderDate) {
		Person person = personRepo.findById(idPerson)
				.orElseThrow(() -> new RuntimeException("There is no such person: " + idPerson));

		OrderItem orderItem = new OrderItem();

		Coffee coffee = coffeeRepo.findById(idProduct).orElse(null);

		if (coffee == null) {
			Food food = foodRepo.findById(idProduct).orElse(null);
			if (food == null) {
				Book book = bookRepo.findById(idProduct).orElse(null);
				if (book != null) {
					orderItem.setPerson(person);
					orderItem.getProducts().add(book);
					orderItem.setDateOrder(LocalDateTime.now().withNano(0).withSecond(0));
					orderRepo.save(orderItem);

				}
			} else {
				orderItem.setPerson(person);
				orderItem.getProducts().add(food);
				orderItem.setDateOrder(LocalDateTime.now().withNano(0).withSecond(0));
				orderRepo.save(orderItem);

			}
		} else {
			orderItem.setPerson(person);
			orderItem.getProducts().add(coffee);
			orderItem.setDateOrder(LocalDateTime.now().withNano(0).withSecond(0));
			orderRepo.save(orderItem);

		}

		
	
	}

	public void deleteItem(OrderItem obejct) {
		// TODO Auto-generated method stub

	}

	public void updateItem(OrderItem object) {
		// TODO Auto-generated method stub

	}

	public OrderItem findById(Long id) {
		// TODO Auto-generated method stub
		return orderRepo.findById(id).orElse(null);
	}

}
