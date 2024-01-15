package springboot.bookstorecafe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_order_product")
	private Long idOrderProduct;

	@ManyToOne
	@JoinColumn(name = "id_whole_order_person", nullable = false)
	@JsonIgnore
	private WholeOrderPerson order;

	@ManyToOne
	@JoinColumn(name = "id_product")
	private Product product;

	@Column(name = "quantity")
	private int quantity;

	public Long getIdOrderProduct() {
		return idOrderProduct;
	}

	public void setIdOrderProduct(Long idOrderProduct) {
		this.idOrderProduct = idOrderProduct;
	}

	public WholeOrderPerson getOrder() {
		return order;
	}

	public void setOrder(WholeOrderPerson order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
