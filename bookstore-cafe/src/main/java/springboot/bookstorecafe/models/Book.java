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

//	@Column(name="quantity_stock")
//	int quantityStock;

	@Column(name = "publishing_house")
	private String publishingHouse;

	@Column(name = "language")
	private String language;
	
	@Column(name = "publication_date")
	private String publicationDate;
	
	@Column(name = "book_cover")
	private String bookCover;

	@Column(name = "number_page")
	private int numberPage;
	
	@Column(name="number_book_stock")
	private int numberBookStock;

	@Column(name="is_available")
	private boolean isAvailable;
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

	public String getPublishingHouse() {
		return publishingHouse;
	}

	public void setPublishingHouse(String publishingHouse) {
		this.publishingHouse = publishingHouse;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getBookCover() {
		return bookCover;
	}

	public void setBookCover(String bookCover) {
		this.bookCover = bookCover;
	}

	public int getNumberPage() {
		return numberPage;
	}

	public void setNumberPage(int numberPage) {
		this.numberPage = numberPage;
	}

	public int getNumberBookStock() {
		return numberBookStock;
	}

	public void setNumberBookStock(int numberBookStock) {
		this.numberBookStock = numberBookStock;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	
	
//	public int getQuantityStock() {
//		return quantityStock;
//	}
//
//	public void setQuantityStock(int quantityStock) {
//		this.quantityStock = quantityStock;
//	}

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
