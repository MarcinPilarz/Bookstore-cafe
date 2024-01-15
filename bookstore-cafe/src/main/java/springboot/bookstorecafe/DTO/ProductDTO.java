package springboot.bookstorecafe.DTO;

import springboot.bookstorecafe.models.ProductType;

public class ProductDTO {
	private String productName;
	private double productPrice;
	private String productDescription;
	private ProductType productType;

	private int coffeeIntensity;

	private String author;
	private String genere;

	private String publishingHouse;

	private String language;

	private String publicationDate;

	private String bookCover;

	private int numberPage;

	private int numberBookStock;

	private boolean isAvailable;

	private Double foodWeight;
	private Double amountOfCalories;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public int getCoffeeIntensity() {
		return coffeeIntensity;
	}

	public void setCoffeeIntensity(int coffeeIntensity) {
		this.coffeeIntensity = coffeeIntensity;
	}

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

	public Double getFoodWeight() {
		return foodWeight;
	}

	public void setFoodWeight(Double foodWeight) {
		this.foodWeight = foodWeight;
	}

	public Double getAmountOfCalories() {
		return amountOfCalories;
	}

	public void setAmountOfCalories(Double amountOfCalories) {
		this.amountOfCalories = amountOfCalories;
	}

}
