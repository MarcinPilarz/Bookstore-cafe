package springboot.bookstorecafe.DTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import springboot.bookstorecafe.models.Product;

public record OrderItemDTO(Long id, LocalDateTime dateOrder, int quantity, List<Product> productNames, String firstName,
		String lastName) {

	public List<String> getProductNames() {
		return productNames.stream().map(Product::getProductName).collect(Collectors.toList());
	}

}
