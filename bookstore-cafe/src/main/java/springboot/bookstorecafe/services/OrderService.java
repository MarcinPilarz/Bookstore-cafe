package springboot.bookstorecafe.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import springboot.bookstorecafe.models.Book;
import springboot.bookstorecafe.models.OrderHistory;

import springboot.bookstorecafe.models.OrderProduct;

import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.models.Product;

import springboot.bookstorecafe.models.WholeOrderPerson;
import springboot.bookstorecafe.repositories.BookRepository;
import springboot.bookstorecafe.repositories.OrderHistoryRepository;

import springboot.bookstorecafe.repositories.OrderProductRepository;
import springboot.bookstorecafe.repositories.PersonRepository;
import springboot.bookstorecafe.repositories.ProductRepository;
import springboot.bookstorecafe.repositories.WholeOrderPersonRepository;

@Service
public class OrderService {

	private PersonRepository personRepo;

	@Autowired
	private StripeService stripeService;

	@Autowired
	private OrderHistoryRepository orderHistoryRepo;

	@Autowired
	private List<ProductRepository<? extends Product>> repositories;

	@Autowired
	private WholeOrderPersonRepository wholeOrderRepo;

	@Autowired
	private OrderProductRepository orderProductRepo;

	@Autowired 
	private BookRepository bookRepo;
	
	@Autowired
	public OrderService(PersonRepository personRepo) {

		this.personRepo = personRepo;

	}

	public List<WholeOrderPerson> getOrdersPerson(Long personId) {
		Person person = personRepo.findById(personId)
				.orElseThrow(() -> new RuntimeException("There is no such person: " + personId));
		return person.getOrders();
	}

	public List<WholeOrderPerson> findAllOrders() {

		return wholeOrderRepo.findAll();
	}

//	public void addItems(Long idPerson, List<Long> idProduct, List<Integer> quantity, String token)
//			throws StripeException {
//		Person person = personRepo.findById(idPerson)
//				.orElseThrow(() -> new RuntimeException("There is no such person: " + idPerson));
//
//		if (idProduct.size() != quantity.size()) {
//			throw new IllegalArgumentException("Product quantity and quantity must be the same");
//		}
//
//		double totalPrice = 0;
//		List<OrderProduct> orderProducts = new ArrayList<>();
//
//		for (int i = 0; i < idProduct.size(); i++) {
//			Product product = findProductById(idProduct.get(i));
//			int quantitys = quantity.get(i);
//			totalPrice += calculateTotalPrice(product, quantitys);
//
//			OrderProduct orderProduct = new OrderProduct();
//			orderProduct.setProduct(product);
//			orderProduct.setQuantity(quantitys);
//			orderProducts.add(orderProduct);
//		}
//
//		if (totalPrice <= 0) {
//			throw new IllegalArgumentException("The total order price must be greater than 0");
//		}
//
//		Charge charge = stripeService.chargePayment(token, totalPrice, "Zamówienie", person.getFirstName(),
//				person.getLastName());
//		if (!charge.getPaid()) {
//			throw new RuntimeException("Payment failed");
//		}
//
//		WholeOrderPerson order = new WholeOrderPerson();
//		order.setDateOrder(LocalDateTime.now().withNano(0));
//		order.setTotalPrice(totalPrice);
//		order.setOrderStatus("W trakcie");
//		order.setPerson(person);
//		order = wholeOrderRepo.save(order);
//
//		for (OrderProduct orderProduct : orderProducts) {
//			orderProduct.setOrder(order);
//			orderProductRepo.save(orderProduct);
//		}
//	}

	public void addItems(Long idPerson, List<Long> idProduct, List<Integer> quantity, String token) throws StripeException {
	    Person person = personRepo.findById(idPerson)
	            .orElseThrow(() -> new RuntimeException("There is no such person: " + idPerson));

	    if (idProduct.size() != quantity.size()) {
	        throw new IllegalArgumentException("Product quantity and quantity must be the same");
	    }

	    double totalPrice = 0;
	    List<OrderProduct> orderProducts = new ArrayList<>();

	    // Obliczanie całkowitego kosztu zamówienia
	    for (int i = 0; i < idProduct.size(); i++) {
	        Product product = findProductById(idProduct.get(i));
	        int requestedQuantity = quantity.get(i);
	        totalPrice += calculateTotalPrice(product, requestedQuantity);
	    }

	    if (totalPrice <= 0) {
		throw new IllegalArgumentException("The total order price must be greater than 0");
	}
	    // Sprawdzenie płatności przed utworzeniem zamówienia
	    Charge charge = stripeService.chargePayment(token, totalPrice, "Order", person.getFirstName(), person.getLastName());
	    if (!charge.getPaid()) {
	        throw new RuntimeException("Payment failed");
	    }

	    // Tworzenie głównego zamówienia
	    WholeOrderPerson order = new WholeOrderPerson();
	    order.setDateOrder(LocalDateTime.now().withNano(0));
	    order.setTotalPrice(totalPrice);
	    order.setOrderStatus("W trakcie");
	    order.setPerson(person);
	    order = wholeOrderRepo.save(order);

	    // Przetwarzanie poszczególnych produktów
	    for (int i = 0; i < idProduct.size(); i++) {
	        Product product = findProductById(idProduct.get(i));
	        int requestedQuantity = quantity.get(i);
	        int stockQuantity = product instanceof Book ? ((Book) product).getNumberBookStock() : requestedQuantity;

	        if (stockQuantity > 0) {
	            int orderQuantity = Math.min(requestedQuantity, stockQuantity);

	            OrderProduct orderProduct = new OrderProduct();
	            orderProduct.setProduct(product);
	            orderProduct.setQuantity(orderQuantity);
	            orderProducts.add(orderProduct);
	            orderProduct.setOrder(order);
	            orderProductRepo.save(orderProduct);

	            // Aktualizacja stanu magazynowego dla książek
	            if (product instanceof Book) {
	                ((Book) product).setNumberBookStock(stockQuantity - orderQuantity);
	                bookRepo.save((Book) product);
	            }

	            // Tworzenie zamówienia częściowego dla brakujących produktów
	            if (requestedQuantity > orderQuantity) {
	                createPartialOrder(person, product, requestedQuantity - orderQuantity);
	            }
	        } else {
	            // Całe zamówienie przechodzi do statusu "Oczekiwanie na dostawę"
	            createPartialOrder(person, product, requestedQuantity);
	        }
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

	
	private void createPartialOrder(Person person, Product product, int quantity) {
	    WholeOrderPerson partialOrder = new WholeOrderPerson();
	    partialOrder.setDateOrder(LocalDateTime.now().withNano(0));
	    partialOrder.setTotalPrice(calculateTotalPrice(product, quantity));
	    partialOrder.setOrderStatus("Oczekiwanie na dostawę");
	    partialOrder.setPerson(person);

	    partialOrder = wholeOrderRepo.save(partialOrder);

	    OrderProduct orderProduct = new OrderProduct();
	    orderProduct.setProduct(product);
	    orderProduct.setQuantity(quantity);
	    orderProduct.setOrder(partialOrder);

	    orderProductRepo.save(orderProduct);
	}
	
	public void updateOrderStatus(Long orderId, String displayStatus) {
		WholeOrderPerson order = wholeOrderRepo.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found"));
		order.setOrderStatus(displayStatus);
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

		Long idPerson = order.getPerson().getIdPerson();
		List<OrderHistory> orderHistoryList = orderHistoryRepo.findByPersonIdPerson(idPerson);
		if (orderHistoryList.size() > 20) {
			OrderHistory oldestHistoryItem = orderHistoryList.get(0);
			orderHistoryRepo.delete(oldestHistoryItem);
		}
	}

	public void deleteItem(Long orderId) {

		WholeOrderPerson order = wholeOrderRepo.findById(orderId)
				.orElseThrow(() -> new RuntimeException("There is no such order: " + orderId));

		wholeOrderRepo.delete(order);
	}

}
