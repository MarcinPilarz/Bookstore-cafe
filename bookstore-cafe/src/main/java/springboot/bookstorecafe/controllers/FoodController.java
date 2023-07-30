package springboot.bookstorecafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.models.Food;
import springboot.bookstorecafe.services.FoodService;

@RestController
public class FoodController {

	@Autowired
	private FoodService foodService;

	@GetMapping(value = "/foods")
	public List<Food> getAllFoods() {
		return foodService.findAllItems();
	}

	@PostMapping(value = "/addFood")
	public ResponseEntity<Food> newFood(@RequestBody Food newFood) {

		foodService.addItem(newFood);
		return ResponseEntity.ok(newFood);
	}

	@PutMapping("value=/updateFood")
	public ResponseEntity<Food> updateFood(@RequestParam Long id, @RequestBody Food updateFood) {

		Food food = foodService.findById(id);
		updateFood.setIdFood(food.getIdFood());
		foodService.updateItem(updateFood);
		return ResponseEntity.ok(updateFood);
	}

	@DeleteMapping(value = "/deleteFood")
	public ResponseEntity<Food> deleteFood(@RequestParam Long id) {

		Food food = foodService.findById(id);
		if (food != null) {
			foodService.deleteItem(foodService.findById(id));
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
