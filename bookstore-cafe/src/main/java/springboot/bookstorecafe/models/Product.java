package springboot.bookstorecafe.models;

import java.util.ArrayList; 
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;


@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name = "typProduktu", discriminatorType = DiscriminatorType.STRING)
//@DiscriminatorValue("Product")
public abstract class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_product")
	private Long idProduct;

	//@NotNull
	@Column(name = "product_name")
	private String productName;

	//@NotNull
	@Column(name = "product_price")
	private Double productPrice;

	@Column(name="product_description")
	private String productDescription;
	
//	@ManyToOne
//	@JoinColumn(name = "id_coffee")
//	private Coffee coffee;
//
//	@ManyToOne
//	@JoinColumn(name = "id_book")
//	private Book book;
//
//	@ManyToOne
//	@JoinColumn(name = "id_food")
//	private Food food;

	@Enumerated(EnumType.STRING)
	@Column(name = "product_type")
	private ProductType productType;
	
	
//	@JsonIgnore
//	@OneToOne(mappedBy = "product")
//	private Coffee coffee;
//	
	
//	@JsonIgnore
//	@OneToOne(mappedBy = "product")
//	private Book book;
	
//	@JsonIgnore
//	@OneToOne(mappedBy = "product")
//	private Food food;
	
	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private ProductImage productImage;

	@ManyToMany(mappedBy = "products")
	private List<OrderItem> orderItems = new ArrayList<>();

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

//	public Coffee getCoffee() {
//		return coffee;
//	}

//	public void setCoffee(Coffee coffee) {
//		this.coffee = coffee;
//	}
//
//	public Book getBook() {
//		return book;
//	}
//
//	public void setBook(Book book) {
//		this.book = book;
//	}
//
//	public Food getFood() {
//		return food;
//	}
//
//	public void setFood(Food food) {
//		this.food = food;
//	}

	
	public ProductImage getProductImage() {
		return productImage;
	}

//	public Coffee getCoffee() {
//		return coffee;
//	}
//
//	public void setCoffee(Coffee coffee) {
//		this.coffee = coffee;
//	}

	public void setProductImage(ProductImage productImage) {
		this.productImage = productImage;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

//	public Book getBook() {
//		return book;
//	}
//
//	public void setBook(Book book) {
//		this.book = book;
//	}

//	public Food getFood() {
//		return food;
//	}
//
//	public void setFood(Food food) {
//		this.food = food;
//	}

}
