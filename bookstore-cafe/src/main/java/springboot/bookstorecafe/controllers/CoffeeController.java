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

import springboot.bookstorecafe.models.Coffee;
import springboot.bookstorecafe.services.CoffeeService;

@RestController
public class CoffeeController {

	@Autowired
	private CoffeeService coffeeService;

	@GetMapping(value = "/coffee")
	public List<Coffee> getCoffee() {
		return coffeeService.findAllItems();
	}

	@PostMapping(value = "/newCoffee")
	public ResponseEntity<Coffee> newCoffee(@RequestBody Coffee newCoffee) {
		coffeeService.addItem(newCoffee);
		return ResponseEntity.ok(newCoffee);
	}

	@PutMapping(value = "/updateCoffee")
	public ResponseEntity<Coffee> updateCoffee(@RequestParam Long id, @RequestBody Coffee updateCoffee) {

		Coffee coffee = coffeeService.findById(id);
		updateCoffee.setIdCoffee(coffee.getIdCoffee());
		coffeeService.updateItem(updateCoffee);
		return ResponseEntity.ok(updateCoffee);
	}

	@DeleteMapping(value = "/deleteCoffee")
	public ResponseEntity<Coffee> deleteCoffee(@RequestParam Long id) {

		Coffee coffee = coffeeService.findById(id);
		if (coffee != null) {
			coffeeService.deleteItem(coffeeService.findById(id));
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}