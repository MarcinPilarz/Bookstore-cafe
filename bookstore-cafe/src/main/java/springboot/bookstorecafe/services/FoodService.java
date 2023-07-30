package springboot.bookstorecafe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.Food;
import springboot.bookstorecafe.repositories.FoodRepository;

@Service
public class FoodService implements MainService<Food> {

	@Autowired
	private FoodRepository foodRepo;

	@Override
	public List<Food> findAllItems() {

		return foodRepo.findAll();
	}

	@Override
	public void addItem(Food food) {
		foodRepo.save(food);

	}

	@Override
	public void deleteItem(Food food) {
		foodRepo.delete(food);

	}

	@Override
	public void updateItem(Food food) {
		foodRepo.save(food);

	}

	@Override
	public Food findById(Long id) {

		return foodRepo.findById(id).orElse(null);
	}

}
