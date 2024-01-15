package springboot.bookstorecafe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class Book extends Product {

	@NotNull
	@Column(name = "author")
	private String author;

	@NotNull
	@Column(name = "genere")
	private String genere;

	@NotNull
	@Column(name = "publishing_house")
	private String publishingHouse;

	@NotNull
	@Column(name = "language")
	private String language;

	@NotNull
	@Column(name = "publication_date")
	private String publicationDate;

	@NotNull
	@Column(name = "book_cover")
	private String bookCover;

	@NotNull
	@Column(name = "number_page")
	private int numberPage;

	@Column(name = "number_book_stock")
	private int numberBookStock;

	@Column(name = "is_available")
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

}
