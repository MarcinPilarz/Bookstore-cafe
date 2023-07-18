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
	private Long idReview;
	
	@Column(name="review_content")
	private String reviewContent;
	
	
	@Column(name="rating")
	private Integer rating;
	
	@Column(name="review_data")
	private Date reviewData;

	@ManyToOne
	@JoinColumn(name="id_person")
	private Person person;
	
	public Long getIdReview() {
		return idReview;
	}

	public void setIdReview(Long idReview) {
		this.idReview = idReview;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Date getReviewData() {
		return reviewData;
	}

	public void setReviewData(Date reviewData) {
		this.reviewData = reviewData;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	
	
	
}
