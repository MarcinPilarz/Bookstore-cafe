package springboot.bookstorecafe.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import springboot.bookstorecafe.models.Book;
import springboot.bookstorecafe.models.Coffee;
import springboot.bookstorecafe.models.Food;
import springboot.bookstorecafe.models.OrderHistory;
import springboot.bookstorecafe.models.OrderItem;
import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.models.ProductType;
import springboot.bookstorecafe.repositories.BookRepository;
import springboot.bookstorecafe.repositories.CoffeeRepository;
import springboot.bookstorecafe.repositories.FoodRepository;
import springboot.bookstorecafe.repositories.OrderHistoryRepository;
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

	@Autowired
	private OrderHistoryRepository orderHistoryRepo;

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

	public void addItem(Long idPerson, Long idProduct, int quantity) {
		Person person = personRepo.findById(idPerson)
				.orElseThrow(() -> new RuntimeException("There is no such person: " + idPerson));

		OrderItem orderItem = new OrderItem();
		Product products = null;

		List<ProductRepository<? extends Product>> repositories = List.of(coffeeRepo, bookRepo, foodRepo);

		for (ProductRepository<? extends Product> repository : repositories) {
			products = repository.findById(idProduct).orElse(null);
			if (products != null) {
				break; // Znaleziono produkt w jednym z repozytoriów, przerywamy pętlę
			}
		}

		if (products == null) {
			throw new RuntimeException("There is no such product: " + idProduct);
		}

		// person.setOrderItem(orderItem);
		orderItem.setPerson(person);
		orderItem.addProductWithQuantity(products, quantity);
		// orderItem.addProductWithQuantity(products, quantity);
		orderItem.setQuantity(quantity);
		orderItem.setDateOrder(LocalDateTime.now().withNano(0));

		Double totalPrice = orderItem.getTotalPrice(); // Obliczanie łącznej ceny na podstawie ilości zamówionych
														// produktów
		orderItem.setTotalPrice(totalPrice);

		orderRepo.save(orderItem);

		if (orderItem != null) {
			addItemToHistory(orderItem, person, products);
		}
//		Coffee coffee = coffeeRepo.findById(idProduct).orElse(null);
//
//		if (coffee == null) {
//			Food food = foodRepo.findById(idProduct).orElse(null);
//			if (food == null) {
//				Book book = bookRepo.findById(idProduct).orElse(null);
//				if (book != null) {
//					orderItem.setPerson(person);
//					orderItem.addProductWithQuantity(product, quantity);
//					orderItem.getProducts().add(book);
//					orderItem.setQuantity(quantity);
//					
//					orderItem.setDateOrder(LocalDateTime.now().withNano(0));
//					
//					//priceAllProducts(orderItem);
//					
//					Double totalPrice = orderItem.getTotalPrice(); // Obliczanie łącznej ceny na podstawie ilości zamówionych produktów
//				    orderItem.setTotalPrice(totalPrice);
//
//					orderRepo.save(orderItem);
//
//				}
//			} else {
//				orderItem.setPerson(person);
//				orderItem.addProductWithQuantity(product, quantity);
//				orderItem.getProducts().add(food);
//				orderItem.setQuantity(quantity);
//				orderItem.setDateOrder(LocalDateTime.now().withNano(0));
//			
//			//	priceAllProducts(orderItem);
//				Double totalPrice = orderItem.getTotalPrice(); // Obliczanie łącznej ceny na podstawie ilości zamówionych produktów
//			    orderItem.setTotalPrice(totalPrice);
//
//				orderRepo.save(orderItem);
//				
//
//			}
//		} else {
//			orderItem.setPerson(person);
//			orderItem.addProductWithQuantity(product, quantity);
//			orderItem.getProducts().add(coffee);
//			orderItem.setQuantity(quantity);
//			orderItem.setDateOrder(LocalDateTime.now().withNano(0));
//			
//			
//			Double totalPrice = orderItem.getTotalPrice(); // Obliczanie łącznej ceny na podstawie ilości zamówionych produktów
//		    orderItem.setTotalPrice(totalPrice);
//
//			//priceAllProducts(orderItem);
//			//coffee.getProductPrice();
//			
//			
//			orderRepo.save(orderItem);
//
//		}
//		orderRepo.save(orderItem);
	}

	public void addItemToHistory(OrderItem oldOrder, Person person2, Product products) {

		OrderHistory orderHistory = new OrderHistory();

		orderHistory.setIdOrderHistory(oldOrder.getIdOrder());
		orderHistory.setDateOrder(oldOrder.getDateOrder());
		orderHistory.setQuantity(oldOrder.getQuantity());
		orderHistory.setTotalPrice(oldOrder.getTotalPrice());
		orderHistory.setPerson(person2);
		orderHistory.setProduct(products);
		orderHistoryRepo.save(orderHistory);
	}

	// ?
//	public void updateItem(@RequestParam Long idOrderItem,OrderItem orderItem) {
//		OrderItem updateOrderItem= findById(idOrderItem);
//		
//	}

	public void deleteItem(Long idOrderItem) {

		OrderItem orderItem = orderRepo.findById(idOrderItem)
				.orElseThrow(() -> new RuntimeException("There is no such order: " + idOrderItem));

		orderRepo.delete(orderItem);
	}

	public OrderItem findById(Long id) {

		return orderRepo.findById(id).orElse(null);
	}

//	private void priceAllProducts(OrderItem orderItems) {
//
//		double totalPrice = 0.0;
//
//		for (Product product : orderItems.getProducts()) {
//			totalPrice += product.getProductPrice() * orderItems.getQuantity();
//		}
//
//		double previousTotalPrice = orderItems.getTotalPrice();
//		double minusTotalPrice = totalPrice - previousTotalPrice;
//
//		orderItems.setTotalPrice(totalPrice);
//		orderRepo.save(orderItems);
//	}

}
