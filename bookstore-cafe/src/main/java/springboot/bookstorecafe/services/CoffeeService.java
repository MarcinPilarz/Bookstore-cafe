package springboot.bookstorecafe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.Coffee;
import springboot.bookstorecafe.repositories.CoffeeRepository;

@Service
public class CoffeeService implements MainService<Coffee> {

	@Autowired
	private CoffeeRepository coffeeRepo;

	@Override
	public List<Coffee> findAllItems() {

		return coffeeRepo.findAll();
	}

	@Override
	public void addItem(Coffee coffee) {

		coffeeRepo.save(coffee);
	}

	@Override
	public void deleteItem(Coffee coffee) {
		coffeeRepo.delete(coffee);

	}

	@Override
	public void updateItem(Coffee coffee) {
		coffeeRepo.save(coffee);

	}

	@Override
	public Coffee findById(Long id) {
		return coffeeRepo.findById(id).orElse(null);
	}

	public Coffee findByTypeOfCoffee(String typeOfCoffee) {
		return coffeeRepo.findByTypeOfCoffee(typeOfCoffee);
	}

}
