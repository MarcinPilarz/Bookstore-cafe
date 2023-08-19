package springboot.bookstorecafe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.ProductImage;
import springboot.bookstorecafe.repositories.ProductImageRepository;

@Service
public class ProductImageService {

	
	@Autowired
	private ProductImageRepository imageRepo;
	
	public ProductImage createImage(ProductImage image) {
		
		return imageRepo.save(image);
	}
	
	public List<ProductImage> getAllImages(){
		
		return imageRepo.findAll();
	}
	
	
	public ProductImage findById(Long id) {
		return imageRepo.findById(id).get();
	}
}
