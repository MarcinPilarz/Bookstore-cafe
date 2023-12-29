package springboot.bookstorecafe.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.Coffee;
import springboot.bookstorecafe.models.Food;
import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.models.ProductType;
import springboot.bookstorecafe.repositories.FoodRepository;
import springboot.bookstorecafe.repositories.ProductRepository;

@Service
public class FoodService {

	@Autowired
	private FoodRepository foodRepo;

	@Qualifier("foodRepository")
	@Autowired
	private ProductRepository productRepo;

	public Iterable<Food> findAllFoods() {

		return foodRepo.findAll();
	}

	public Iterable<Product> findAllFoodsByType(ProductType productType) {
		return productRepo.getProductsByProductType(productType);
	}

	
	public List<Product> findAllFoodByType(ProductType productType) {
        if (productType == ProductType.FOOD) {
            return new ArrayList<>(foodRepo.getProductsByProductType(productType));
        }
        return new ArrayList<>();
    }
	public void addFood(Food food) {
	
		Food existingFood= foodRepo.findByProductName(food.getProductName());
		if (existingFood !=null) {
			throw new IllegalArgumentException("Product with the same name already exists: " + food.getProductName());
		}
		food.setProductName(changingFoodNameSize(food.getProductName()));
		foodRepo.save(food);

	}

	public static String changingFoodNameSize(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}

		return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}

	public void deleteFood(Long idFood) {
		foodRepo.deleteById(idFood);

	}

	public void updateFood(Food food) {
		
		food.setProductName(changingFoodNameSize(food.getProductName()));
		foodRepo.save(food);

	}

	public Product findById(Long id) {

		return foodRepo.findById(id).orElse(null);
	}

	public void addAllItemsToList(List<Product> productList, Iterable<? extends Product> items) {
		for (Product item : items) {
			productList.add(item);
		}
	}
}
