package springboot.bookstorecafe.services;

import java.time.LocalDateTime; 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
//import springboot.bookstorecafe.models.OrderItem;
import springboot.bookstorecafe.models.OrderProduct;
import springboot.bookstorecafe.models.OrderStatus;
import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.models.ProductType;
import springboot.bookstorecafe.models.WholeOrderPerson;
import springboot.bookstorecafe.repositories.BookRepository;
import springboot.bookstorecafe.repositories.CoffeeRepository;
import springboot.bookstorecafe.repositories.FoodRepository;
import springboot.bookstorecafe.repositories.OrderHistoryRepository;
//import springboot.bookstorecafe.repositories.OrderItemRepository;
import springboot.bookstorecafe.repositories.OrderProductRepository;
import springboot.bookstorecafe.repositories.PersonRepository;
import springboot.bookstorecafe.repositories.ProductRepository;
import springboot.bookstorecafe.repositories.WholeOrderPersonRepository;

@Service
public class OrderService {

//	private OrderItemRepository orderRepo;

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
	private List<ProductRepository<? extends Product>> repositories;

	@Autowired
	private WholeOrderPersonRepository wholeOrderRepo;

	@Autowired
	private OrderProductRepository orderProductRepo;

	@Autowired
	public OrderService(PersonRepository personRepo, CoffeeRepository coffeeRepo,
			BookRepository bookRepo, FoodRepository foodRepo) {

		this.personRepo = personRepo;
		
		this.coffeeRepo = coffeeRepo;
		this.bookRepo = bookRepo;
		this.foodRepo = foodRepo;
	}

//	public List<OrderItem> findAllItems() {
//
//		return orderRepo.findAll();
//	}

	public List<WholeOrderPerson> getOrdersPerson(Long personId) {
		Person person = personRepo.findById(personId)
				.orElseThrow(() -> new RuntimeException("There is no such person: " + personId));
		return person.getOrders();
	}
//	public void addItem(Long idPerson, Long idProduct, int quantity, String token)throws StripeException {
//		Person person = personRepo.findById(idPerson)
//				.orElseThrow(() -> new RuntimeException("There is no such person: " + idPerson));
//
//		OrderItem orderItem = new OrderItem();
//		Product products = null;
//
//		List<ProductRepository<? extends Product>> repositories = List.of(coffeeRepo, bookRepo, foodRepo);
//
//		for (ProductRepository<? extends Product> repository : repositories) {
//			products = repository.findById(idProduct).orElse(null);
//			if (products != null) {
//				break; // Znaleziono produkt w jednym z repozytoriów, przerywamy pętlę
//			}
//		}
//
//		if (products == null) {
//			throw new RuntimeException("There is no such product: " + idProduct);
//		}
//		//Stripe.apiKey=stripeKey;
//		// person.setOrderItem(orderItem);
//		
//		
//		String firstName = person.getFirstName();
//	    String lastName = person.getLastName();
//	    String productName = products.getProductName();
//	    
//
//	        // Przetwarzanie płatności Stripe
//		
//		 double totalPrice = calculateTotalPrice(products, quantity);
//	        
//		//double totalPrice = orderItem.getTotalPrice();
//		//Double totalPrice = orderItem.getTotalPrice(); // Obliczanie łącznej ceny na podstawie ilości zamówionych
//        //stripeService.chargePayment(token, totalPrice);
//		orderItem.setTotalPrice(totalPrice);
//		orderItem.setPerson(person);
//		orderItem.addProductWithQuantity(products, quantity);
//		// orderItem.addProductWithQuantity(products, quantity);
//		orderItem.setQuantity(quantity);
//		orderItem.setDateOrder(LocalDateTime.now().withNano(0));
//		
//		if (totalPrice <= 0) {
//	        throw new IllegalArgumentException("Całkowita cena zamówienia musi być większa niż 0");
//	    }
//		Charge charge = stripeService.chargePayment(token, totalPrice,productName, firstName, lastName);
//        if (!charge.getPaid()) {
//            throw new RuntimeException("Płatność nie powiodła się");
//        }
//		orderRepo.save(orderItem);
//		addItemToHistory(orderItem, person, products);
	// Double totalPrice = orderItem.getTotalPrice(); // Obliczenie całkowitej ceny

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

	// int totalPriceCents = (int) (totalPrice * 100);

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
	// }

	public List<WholeOrderPerson> findAllOrders() {

		return wholeOrderRepo.findAll();
	}

	public void addItems(Long idPerson, List<Long> idProduct, List<Integer> quantity, String token)
			throws StripeException {
		Person person = personRepo.findById(idPerson)
				.orElseThrow(() -> new RuntimeException("There is no such person: " + idPerson));

		if (idProduct.size() != quantity.size()) {
			throw new IllegalArgumentException("Product quantity and quantity must be the same");
		}

		double totalPrice = 0;
		List<OrderProduct> orderProducts = new ArrayList<>();

		for (int i = 0; i < idProduct.size(); i++) {
			Product product = findProductById(idProduct.get(i));
			int quantitys = quantity.get(i);
			totalPrice += calculateTotalPrice(product, quantitys);

			OrderProduct orderProduct = new OrderProduct();
			orderProduct.setProduct(product);
			orderProduct.setQuantity(quantitys);
			orderProducts.add(orderProduct);
		}

		if (totalPrice <= 0) {
			throw new IllegalArgumentException("The total order price must be greater than 0");
		}

		Charge charge = stripeService.chargePayment(token, totalPrice, "Zamówienie", person.getFirstName(),
				person.getLastName());
		if (!charge.getPaid()) {
			throw new RuntimeException("Payment failed");
		}

		WholeOrderPerson order = new WholeOrderPerson();
		order.setDateOrder(LocalDateTime.now().withNano(0));
		order.setTotalPrice(totalPrice);
		order.setOrderStatus("W trakcie");
		order.setPerson(person);
		order = wholeOrderRepo.save(order);

		for (OrderProduct orderProduct : orderProducts) {
			orderProduct.setOrder(order);
			orderProductRepo.save(orderProduct);
		}
	}

	private Product findProductById(Long idProduct) {
		for (ProductRepository<? extends Product> repository : repositories) {
			Optional<? extends Product> product = repository.findById(idProduct);
			if (product.isPresent()) {
				return product.get();
			}
		}
		throw new RuntimeException("There is no such product: " + idProduct);
	}

	private double calculateTotalPrice(Product products, int quantity) {
		return products.getProductPrice() * quantity;
	}

	public void updateOrderStatus(Long orderId, String displayStatus) {
		WholeOrderPerson order = wholeOrderRepo.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found"));
		order.setOrderStatus(displayStatus); // Przekazuje ciąg znaków bezpośrednio
		wholeOrderRepo.save(order);
		if (displayStatus.equals("Odebrane")) {
			addOrderToHistory(order);
			deleteItem(orderId);
		}
	}

	public void addOrderToHistory(WholeOrderPerson order) {
		for (OrderProduct orderProduct : order.getOrder()) {
			OrderHistory orderHistory = new OrderHistory();

			orderHistory.setIdOrderHistory(order.getIdWholeOrderPerson());
			orderHistory.setDateOrder(order.getDateOrder());
			orderHistory.setQuantity(orderProduct.getQuantity());
			orderHistory.setTotalPrice(order.getTotalPrice());
			orderHistory.setPerson(order.getPerson());
			orderHistory.setProduct(orderProduct.getProduct());
			orderHistoryRepo.save(orderHistory);
		}

		// Sprawdzanie i usuwanie starych wpisów z historii, jeśli jest ich więcej niż
		// 20
		Long idPerson = order.getPerson().getIdPerson();
		List<OrderHistory> orderHistoryList = orderHistoryRepo.findByPersonIdPerson(idPerson);
		if (orderHistoryList.size() > 20) {
			OrderHistory oldestHistoryItem = orderHistoryList.get(0);
			orderHistoryRepo.delete(oldestHistoryItem);
		}
	}

	// ?
//	public void updateItem(@RequestParam Long idOrderItem,OrderItem orderItem) {
//		OrderItem updateOrderItem= findById(idOrderItem);
//		
//	}

	public void deleteItem(Long orderId) {

		WholeOrderPerson order = wholeOrderRepo.findById(orderId)
				.orElseThrow(() -> new RuntimeException("There is no such order: " + orderId));

		wholeOrderRepo.delete(order);
	}

//	public OrderItem findById(Long id) {
//
//		return orderRepo.findById(id).orElse(null);
//	}

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
