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
