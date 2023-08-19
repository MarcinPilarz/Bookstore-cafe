package springboot.bookstorecafe.models;

import java.sql.Blob;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="product_image")
public class ProductImage {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_product_image")
	private Long idProductImage;
	
//	@NotNull
	@Lob
	@Column(name="image")
	private Blob image;
	
	
	@ManyToOne
	@JoinColumn(name="id_product")
	private Product product;

	public Long getIdProductImage() {
		return idProductImage;
	}

	public void setIdProductImage(Long idProductImage) {
		this.idProductImage = idProductImage;
	}

	

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
	
	
}
