package springboot.bookstorecafe.services;

import java.time.LocalDateTime; 
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.Review;

import springboot.bookstorecafe.repositories.ReviewRepostory;

@Service
public class ReviewService implements MainService<Review> {

	@Autowired
	private ReviewRepostory reviewRepo;

	@Override
	public List<Review> findAllItems() {
		List<Review> reviews = reviewRepo.findAll();

		if (reviews != null) {
			return reviews;
		} else {
			System.out.println("No user has left a review yet!");
			return Collections.emptyList();
		}
	}

	@Override
	public void addItem(Review review) {

		review.setReviewData(new Date());

		reviewRepo.save(review);

	}

	@Override
	public void deleteItem(Review review) {
		reviewRepo.delete(review);

	}

	@Override
	public void updateItem(Review review) {
		reviewRepo.save(review);

	}

	@Override
	public Review findById(Long id) {
		// TODO Auto-generated method stub
		return reviewRepo.findById(id).orElse(null);
	}

}
