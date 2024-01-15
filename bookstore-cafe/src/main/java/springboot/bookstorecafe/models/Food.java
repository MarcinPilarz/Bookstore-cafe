package springboot.bookstorecafe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.validation.constraints.NotNull;

@Entity
public class Food extends Product {

	@NotNull
	@Column(name = "food_weight")
	private Double foodWeight;

	@NotNull
	@Column(name = "amount_of_calories")
	private Double amountOfCalories;

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

}
