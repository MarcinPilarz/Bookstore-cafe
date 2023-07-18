package springboot.bookstorecafe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Coffee {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_coffee")
	private Long idCoffee;
	
	@NotNull
	@Column(name="types_of_coffee")
	private String typeOfCoffee;
	
	@NotNull
	@Column(name="coffee_intensity")
	private Integer coffeeIntensity;
	
	@Column(name="coffee_description")
	private String coffeeDescription;

	@ManyToOne
	@JoinColumn(name="id_product")
	private Product product;
	
	public Long getIdCoffee() {
		return idCoffee;
	}

	public void setIdCoffee(Long idCoffee) {
		this.idCoffee = idCoffee;
	}

	public String getTypeOfCoffee() {
		return typeOfCoffee;
	}

	public void setTypeOfCoffee(String typeOfCoffee) {
		this.typeOfCoffee = typeOfCoffee;
	}

	public Integer getCoffeeIntensity() {
		return coffeeIntensity;
	}

	public void setCoffeeIntensity(Integer coffeeIntensity) {
		this.coffeeIntensity = coffeeIntensity;
	}

	public String getCoffeeDescription() {
		return coffeeDescription;
	}

	public void setCoffeeDescription(String coffeeDescription) {
		this.coffeeDescription = coffeeDescription;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
}
