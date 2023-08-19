package springboot.bookstorecafe.models;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
//@DiscriminatorValue("Coffee")

public class Coffee extends Product   {

	
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@Column(name="id_coffee")
//	private Long idCoffee;
//	
//	@NotNull
//	@Column(name="types_of_coffee")
//	private String typeOfCoffee;
	
	@NotNull
	@Column(name="coffee_intensity")
	private int coffeeIntensity;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "product_type", insertable = false, updatable = false)
    private ProductType productType = ProductType.COFFEE;
//	@Column(name="coffee_description")
//	private String coffeeDescription;
	public Coffee() {
        setProductType(ProductType.COFFEE);
    }
//	@ManyToOne
//	@JoinColumn(name="id_product")
//	private Product product;
	
//	@OneToOne
//    @JoinColumn(name = "id_product")
//    private Product product;
	
//	public Long getIdCoffee() {
//		return idCoffee;
//	}
//
//	public void setIdCoffee(Long idCoffee) {
//		this.idCoffee = idCoffee;
//	}

//	public String getTypeOfCoffee() {
//		return typeOfCoffee;
//	}
//
//	public void setTypeOfCoffee(String typeOfCoffee) {
//		this.typeOfCoffee = typeOfCoffee;
//	}

	
	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public int getCoffeeIntensity() {
		return coffeeIntensity;
	}

	public void setCoffeeIntensity(int coffeeIntensity) {
		this.coffeeIntensity = coffeeIntensity;
	}

//	public String getCoffeeDescription() {
//		return coffeeDescription;
//	}
//
//	public void setCoffeeDescription(String coffeeDescription) {
//		this.coffeeDescription = coffeeDescription;
//	}

	

//	public Product getProduct() {
//		return product;
//	}
//
//	public void setProduct(Product product) {
//		this.product = product;
//	}

//	@Override
//	public int hashCode() {
//		return Objects.hash(idCoffee);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Coffee other = (Coffee) obj;
//		return Objects.equals(idCoffee, other.idCoffee);
//	}
	
	
	
}
