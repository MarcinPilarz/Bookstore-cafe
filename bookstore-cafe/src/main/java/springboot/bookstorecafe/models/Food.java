package springboot.bookstorecafe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Food extends Product{

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id_food")
//	private Long idFood;

//	@NotNull
//	@Column(name = "type_of_dish")
//	private String typeOfDish;

	@NotNull
	@Column(name = "food_weight")
	private Double foodWeight;

	@NotNull
	@Column(name = "amount_of_calories")
	private Double amountOfCalories;

//	@OneToOne
//    @JoinColumn(name = "id_product")
//    private Product product;

//	public Long getIdFood() {
//		return idFood;
//	}
//
//	public void setIdFood(Long idFood) {
//		this.idFood = idFood;
//	}

//	public String getTypeOfDish() {
//		return typeOfDish;
//	}
//
//	public void setTypeOfDish(String typeOfDish) {
//		this.typeOfDish = typeOfDish;
//	}

	public Double getFoodWeight() {
		return foodWeight;
	}

	public void setFoodWeight(Double foodWeight) {
		this.foodWeight = foodWeight;
	}

	public Double getAmountOfCalories() {
		return amountOfCalories;
	}

	public void setAmountOfCalories(Double amountOfCalories) {
		this.amountOfCalories = amountOfCalories;
	}

//	public Product getProduct() {
//		return product;
//	}
//
//	public void setProduct(Product product) {
//		this.product = product;
//	}

}
