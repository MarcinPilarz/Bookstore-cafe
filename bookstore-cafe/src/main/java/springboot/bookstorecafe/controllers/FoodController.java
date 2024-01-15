package springboot.bookstorecafe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.models.Food;
import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.models.ProductType;
import springboot.bookstorecafe.services.FoodService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FoodController {

	@Autowired
	private FoodService foodService;

	@PostMapping(value = "/addFood")
	public ResponseEntity<Food> newFood(@RequestParam ProductType foodType, @RequestBody Food newFood) {
		if (foodType == ProductType.FOOD) {
			newFood.setProductType(foodType);
			foodService.addFood(newFood);
			return ResponseEntity.ok(newFood);
		}

		return null;
	}

	@PutMapping(value = "/updateFood")
	public ResponseEntity<Food> updateFood(@RequestParam Long id, @RequestParam ProductType foodType,
			@RequestBody Food updateFood) {
		if (foodType == ProductType.FOOD) {
			Product food = foodService.findById(id);
			updateFood.setIdProduct(food.getIdProduct());
			updateFood.setProductType(foodType);
			foodService.updateFood(updateFood);

			return ResponseEntity.ok(updateFood);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@DeleteMapping(value = "/deleteFood")
	public ResponseEntity<Food> deleteFood(@RequestParam Long id) {

		Product food = foodService.findById(id);
		if (food != null) {
			foodService.deleteFood(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
