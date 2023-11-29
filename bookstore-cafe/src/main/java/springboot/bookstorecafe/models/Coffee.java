package springboot.bookstorecafe.models;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Coffee extends Product {

	@NotNull
	@Min(value = 1, message = "Coffee intensity must be at least 1")
	@Max(value = 10, message = "Coffee intensity must be at most 10")
	@Column(name = "coffee_intensity")
	private int coffeeIntensity;

	public int getCoffeeIntensity() {
		return coffeeIntensity;
	}

	public void setCoffeeIntensity(int coffeeIntensity) {
		this.coffeeIntensity = coffeeIntensity;
	}

}
