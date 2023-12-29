package springboot.bookstorecafe.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import springboot.bookstorecafe.DTO.ProductaAndProductPhotoInfoDTO;
import springboot.bookstorecafe.models.Book;
import springboot.bookstorecafe.models.Coffee;
import springboot.bookstorecafe.models.Food;
import springboot.bookstorecafe.models.Product;
import springboot.bookstorecafe.models.ProductImage;
import springboot.bookstorecafe.models.ProductType;
import springboot.bookstorecafe.repositories.BookRepository;
import springboot.bookstorecafe.repositories.CoffeeRepository;
import springboot.bookstorecafe.repositories.FoodRepository;
import springboot.bookstorecafe.repositories.ProductRepository;

@Service
public class ProductService {

	
	@Autowired
	private Storage storage;

	@Autowired
	private CoffeeRepository coffeeRepo;
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private FoodRepository foodRepo;

//	public List<ProductaAndProductPhotoInfoDTO> getAllImagesWithProducts(ProductType productType) {
//		List<ProductaAndProductPhotoInfoDTO> photoProductList = new ArrayList<>();
//
//		List<ProductImage> productImages = imageRepo.findAll();
//
//		for (ProductImage productImage : productImages) {
//			Optional<Product> productOptional = getProductByProductId(productImage.getProduct().getIdProduct());
//
//			if (productOptional.isPresent()) {
//				Product product = productOptional.get();
//
//				if (productType == ProductType.ALLPRODUCTS || product.getProductType() == productType) {
//					String photoUrl = "https://storage.googleapis.com/springbootphoto/springbootphoto" + "/"
//							+ productImage.getImageName();
//
//					ProductaAndProductPhotoInfoDTO productInfo = new ProductaAndProductPhotoInfoDTO(photoUrl, product);
//					photoProductList.add(productInfo);
//				}
//			}
//		}
//
//		return photoProductList;
//	}

//	public void uploadProductImage(Long idProduct, MultipartFile file) {
//		String objectName = "springbootphoto/" + file.getOriginalFilename();
//
//		try {
//
//			BlobId blobId = BlobId.of("springbootphoto", objectName);
//			BlobInfo blobinfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
//
//			storage.create(blobinfo, file.getBytes());
//			ProductImage productImage = new ProductImage();
//			productImage.setImageName(file.getOriginalFilename());
//
//			Product products = null;
//
//			List<ProductRepository<? extends Product>> repositories = List.of(coffeeRepo, bookRepo, foodRepo);
//
//			for (ProductRepository<? extends Product> repository : repositories) {
//				products = repository.findById(idProduct).orElse(null);
//				if (products != null) {
//					break; 
//				}
//			}
//
//			productImage.setProduct(products);
//			imageRepo.save(productImage);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("Error uploading photo");
//		}
//
//	}

	
	public void uploadProductImage(Long idProduct, MultipartFile file) {
	    String objectName = "springbootphoto/" + file.getOriginalFilename();

	    try {
	        BlobId blobId = BlobId.of("springbootphoto", objectName);
	        BlobInfo blobinfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
	        storage.create(blobinfo, file.getBytes());

	        String imageUrl = "https://storage.googleapis.com/springbootphoto/" + file.getOriginalFilename();

	        Product product = getProductByProductId(idProduct)
	            .orElseThrow(() -> new RuntimeException("Product not found: " + idProduct));

	        product.setImageName(imageUrl); // Ustawienie URL obrazu

	        // Zaktualizuj produkt w odpowiednim repozytorium
	        if (product instanceof Coffee) {
	            coffeeRepo.save((Coffee) product);
	        } else if (product instanceof Book) {
	            bookRepo.save((Book) product);
	        } else if (product instanceof Food) {
	            foodRepo.save((Food) product);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Error uploading photo", e);
	    }
	}
	
	private Optional<Product> getProductByProductId(Long productId) {
//		List<ProductRepository<? extends Product>> repositories = List.of(coffeeRepo, bookRepo, foodRepo);
//
//		for (ProductRepository<? extends Product> repository : repositories) {
//			Optional<? extends Product> product = repository.findById(productId);
//			if (product.isPresent()) {
//				return Optional.of(product.get());
//			}
//		}
//
//		return Optional.empty();
		 Optional<? extends Product> product;

		    product = coffeeRepo.findById(productId);
		    if (product.isPresent()) {
		        return Optional.of(product.get());
		    }

		    product = bookRepo.findById(productId);
		    if (product.isPresent()) {
		        return Optional.of(product.get());
		    }

		    product = foodRepo.findById(productId);
		    if (product.isPresent()) {
		        return Optional.of(product.get());
		    }

		    return Optional.empty();
	}

	public Product findById(Long id) {
	    Optional<? extends Product> product;

	    product = coffeeRepo.findById(id);
	    if (product.isPresent()) {
	        return product.get();
	    }

	    product = bookRepo.findById(id);
	    if (product.isPresent()) {
	        return product.get();
	    }

	    product = foodRepo.findById(id);
	    if (product.isPresent()) {
	        return product.get();
	    }

	    throw new RuntimeException("Product not found with id: " + id);
	}
	
	
	
	
//	@Autowired
//	private ProductRepository productRepo;

//	public List<Product> findAllProducts() {
//		return productRepo.findAll();
//	}
//
//	public Product addCoffee(Product product, Coffee coffee) {
//
//		product.setCoffee(coffee);
//		return productRepo.save(product);
//
//	}
//
//	public Product addBook(Product product, Book book) {
//
//		product.setBook(book);
//		return productRepo.save(product);
//	}
//
//	public Product addFood(Product product, Food food) {
//
//		product.setFood(food);
//		return productRepo.save(product);
//	}
//
//	public void deleteItem(Product product) {
//		productRepo.delete(product);
//
//	}
//
//	public void updateItem(Product product) {
//		productRepo.save(product);
//
//	}
//
//	public Product findById(Long id) {
//
//		return productRepo.findById(id).orElse(null);
//	}
//
//	public Product findByProductName(String productName) {
//		return productRepo.findByProductName(productName);
//	}
}
