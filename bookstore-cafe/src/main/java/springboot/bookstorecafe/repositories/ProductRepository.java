package springboot.bookstorecafe.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.models.ProductType;

@NoRepositoryBean
public interface ProductRepository<T extends Product> extends CrudRepository<T, Long> {
	List<T> getProductsByProductType(ProductType productType);

	T findByProductName(String productName);
}
