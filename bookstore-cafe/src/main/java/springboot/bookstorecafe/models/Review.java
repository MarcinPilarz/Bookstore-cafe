package springboot.bookstorecafe.models;

import java.sql.Date;
//import java.util.Date;  <-- Trzeba zobaczyć które jest poprawne 

import jakarta.persistence.*;
@Entity
@Table(name="review")
public class Review {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_review")
	private Long id_review;
	
	@Column(name="review_content")
	private String review_content;
	
	
	@Column(name="rating")
	private Integer rating;
	
	@Column(name="review_data")
	private Date review_data;

	public Long getId_review() {
		return id_review;
	}

	public void setId_review(Long id_review) {
		this.id_review = id_review;
	}

	public String getReview_content() {
		return review_content;
	}

	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Date getReview_data() {
		return review_data;
	}

	public void setReview_data(Date review_data) {
		this.review_data = review_data;
	}

	
	
}
