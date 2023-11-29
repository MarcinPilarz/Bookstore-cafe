package springboot.bookstorecafe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;


@Entity
public class Book extends Product {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id_book")
//	private Long idBook;

	// @NotNull
//	@Column(name = "title")
//	private String title;

	// @NotNull
	@Column(name = "author")
	private String author;

	// @NotNull
	@Column(name = "genere")
	private String genere;

	// @NotNull
//	@Column(name="description")
//	private String description;

//	@OneToOne
//    @JoinColumn(name = "id_product")
//    private Product product;

//	public Long getIdBook() {
//		return idBook;
//	}
//
//	public void setIdBook(Long idBook) {
//		this.idBook = idBook;
//	}

//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
	
	@Column(name="quantity_stock")
	int quantityStock;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public int getQuantityStock() {
		return quantityStock;
	}

	public void setQuantityStock(int quantityStock) {
		this.quantityStock = quantityStock;
	}
	
	
	

//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}

//	public Product getProduct() {
//		return product;
//	}
//
//	public void setProduct(Product product) {
//		this.product = product;
//	}

}
