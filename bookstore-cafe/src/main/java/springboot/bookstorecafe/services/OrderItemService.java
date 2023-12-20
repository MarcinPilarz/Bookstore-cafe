package springboot.bookstorecafe.services;

import java.time.LocalDateTime; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import springboot.bookstorecafe.models.Book;
import springboot.bookstorecafe.models.ChargeCreateParamsBuilder;
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
	    private StripeService stripeService;
	
	@Autowired
	private OrderHistoryRepository orderHistoryRepo;

	@Autowired
	private OrderHistoryService orderHistoryService;

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

	public void addItem(Long idPerson, Long idProduct, int quantity, String token)throws StripeException {
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
		//Stripe.apiKey=stripeKey;
		// person.setOrderItem(orderItem);
		
		
		String firstName = person.getFirstName();
	    String lastName = person.getLastName();
	    String productName = products.getProductName();

	        // Przetwarzanie płatności Stripe
		
		 double totalPrice = calculateTotalPrice(products, quantity);
	        
		//double totalPrice = orderItem.getTotalPrice();
		//Double totalPrice = orderItem.getTotalPrice(); // Obliczanie łącznej ceny na podstawie ilości zamówionych
        //stripeService.chargePayment(token, totalPrice);
		orderItem.setTotalPrice(totalPrice);
		orderItem.setPerson(person);
		orderItem.addProductWithQuantity(products, quantity);
		// orderItem.addProductWithQuantity(products, quantity);
		orderItem.setQuantity(quantity);
		orderItem.setDateOrder(LocalDateTime.now().withNano(0));
		
		if (totalPrice <= 0) {
	        throw new IllegalArgumentException("Całkowita cena zamówienia musi być większa niż 0");
	    }
		Charge charge = stripeService.chargePayment(token, totalPrice,productName, firstName, lastName);
        if (!charge.getPaid()) {
            throw new RuntimeException("Płatność nie powiodła się");
        }
		orderRepo.save(orderItem);
		
		 //Double totalPrice = orderItem.getTotalPrice(); // Obliczenie całkowitej ceny

	        // Najpierw próbuj przetworzyć płatność
//	        boolean paymentSuccess = stripeService.chargePayment(tokenCreditCard, totalPrice);
//	        
//	        if (paymentSuccess) {
//	            // Jeśli płatność się powiedzie, zapisz zamówienie
//	            orderRepo.save(orderItem);
//	        } else {
//	            // W przeciwnym razie rzuć wyjątek lub zwróć informację o błędzie
//	            throw new RuntimeException("Payment failed");
//	        }
	    
		
	//	int totalPriceCents = (int) (totalPrice * 100);
		
//		try {
//	        Charge charge = Charge.create(new ChargeCreateParamsBuilder()
//	            .setAmount((long) totalPriceCents)
//	            .setCurrency("usd")
//	            .setSource(tokenCreditCard) // Token karty kredytowej klienta
//	            .setDescription("Opis płatności")
//	            .build()
//	        );
//		
		
//
//		 try {
//	            Stripe.apiKey = stripeKey;
//
//	            // Przygotuj dane do płatności w Stripe
//	            Long totalPriceCents = (long) (totalPrice * 100); // W centach
//	            Charge charge = Charge.create(new ChargeCreateParamsBuilder()
//	                .setAmount(totalPriceCents)
//	                .setCurrency("usd")
//	                .setSource(tokenCreditCard) // Token karty kredytowej klienta
//	                .setDescription("Opis płatności")
//	                .build()
//	            );
//
//	            // Jeśli płatność przebiegnie pomyślnie, zapisz zamówienie
//	            orderRepo.save(orderItem);
//
//	            if (orderItem != null) {
//	                addItemToHistory(orderItem, person, products);
//	            } } catch (StripeException e) {
//	                // Obsłuż błąd płatności
//	                throw new RuntimeException("Błąd płatności: " + e.getMessage());
//	            }

//		 } catch (StripeException e) {
//		        // Płatność nie powiodła się, obsłuż błąd
//		        throw new RuntimeException("Błąd płatności: " + e.getMessage());
//		    }
	}

	
	 private double calculateTotalPrice(Product products, int quantity) {
	        return products.getProductPrice() * quantity;
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

		Long idPerson = orderHistory.getPerson().getIdPerson();
		List<OrderHistory> orderHistoryList = orderHistoryRepo.findByPersonIdPerson(idPerson);

		if (orderHistoryList.size() > 20) {
			orderHistory = orderHistoryList.get(0);

			orderHistoryRepo.delete(orderHistory);
		}

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
