package springboot.bookstorecafe.DTO;

import springboot.bookstorecafe.models.Product;

public class ProductPhotoInfo {

	  private String imageUrl;
	    private Product product;
	    
	    
	    public ProductPhotoInfo(String imageUrl, Product product) {
	        this.imageUrl = imageUrl;
	        this.product = product;
	    }

	    public String getImageUrl() {
	        return imageUrl;
	    }

	    public Product getProduct() {
	        return product;
	    }
}
