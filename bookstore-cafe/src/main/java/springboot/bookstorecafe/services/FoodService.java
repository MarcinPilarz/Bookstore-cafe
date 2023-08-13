package springboot.bookstorecafe.services;

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
public class FoodService  {

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
	
	public void addFood(Food food) {
		foodRepo.save(food);

	}

	
	public void deleteFood(Food food) {
		foodRepo.delete(food);

	}

	
	public void updateFood(Food food) {
		foodRepo.save(food);

	}

//	public List<Food> getAllFoods(ProductType productType) {
//		return foodRepo.getFoodByType();
//	}
	
//public Iterable<Product> findAllFoodsByType(ProductType productType) {
//		
//		return foodRepo.getProductsByType(ProductType.FOOD);
//		
		
//	}
//	public Product findById(Long id) {
//
//		return foodRepo.findById(id).orElse(null);
//	}

}
