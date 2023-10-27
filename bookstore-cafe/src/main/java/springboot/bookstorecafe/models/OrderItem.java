package springboot.bookstorecafe.models;

import java.time.LocalDateTime; 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_order")
	private Long idOrder;

	// @NotNull
	@Column(name = "date_order")
	private LocalDateTime dateOrder;

	// @NotNull
	@Min(1)
	@Max(20)
	@Column(name = "quantity")
	private int quantity;

	@Column(name = "total_price")
	private Double totalPrice;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "id_person")
	private Person person;

	@ManyToMany
	@JoinTable(name = "order_has_product", joinColumns = @JoinColumn(name = "id_order"), inverseJoinColumns = @JoinColumn(name = "id_product"))
	private List<Product> products = new ArrayList<>();

//	@ElementCollection
//	@CollectionTable(name = "order_item_product_quantity", joinColumns = @JoinColumn(name = "id_order_item"))
//	@MapKeyJoinColumn(name = "id_product")
//	@Column(name = "quantity") a
	
	@Transient
	private Map<Product, Integer> productQuantity = new HashMap<>();

	
	
	public Long getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Long idOrder) {
		this.idOrder = idOrder;
	}

	public LocalDateTime getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(LocalDateTime currentOrderDate) {
		this.dateOrder = currentOrderDate;
	}

//	public Double getTotalPrice() {
//	    Double totalPrice = 0.0;
//	    for (Product product : products) {
//	        Integer quantity = productQuantity.get(product);
//	        if (quantity != null) {
//	            Double productPrice = product.getProductPrice();
//	            totalPrice += productPrice * quantity;
//	        }
//	    }
//	    return totalPrice;
//	}
	public Double getTotalPrice() {
        Double totalPrice = 0.0;
        for (Product product : products) {
            Double productPrice = product.getProductPrice();
            totalPrice += productPrice *quantity;
        }
        return totalPrice;
    }

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;

	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Map<Product, Integer> getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Map<Product, Integer> productQuantity) {
		this.productQuantity = productQuantity;
	}

//	public void addProductWithQuantity(Product product, int quantity) {
//		products.add(product);
//		productQuantity.put(product, quantity);
//	}
	 public void addProductWithQuantity(Product product, int quantity) {
	        products.add(product);
	        this.quantity += quantity; // Aktualizacja ilości produktów w zamówieniu
	    }

}
