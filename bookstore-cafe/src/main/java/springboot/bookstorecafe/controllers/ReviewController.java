package springboot.bookstorecafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.models.Review;
import springboot.bookstorecafe.services.ReviewService;

@RestController
public class ReviewController {

	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping(value ="/reviews")
	public List<Review> getReviews(){
		return reviewService.findAllItems();
	}
	
	
	@PostMapping(value="/newComment")
	public ResponseEntity<Review> addReview(@RequestBody Review newReview){
		
		reviewService.addItem(newReview);
		return ResponseEntity.ok(newReview);
		
	}
	
	
	@PutMapping(value="/editComment")
	public ResponseEntity<Review> editReview(@RequestParam Long id, @RequestBody Review updateReview){
		Review review= reviewService.findById(id);
		updateReview.setIdReview(review.getIdReview());
		reviewService.updateItem(review);
		return ResponseEntity.ok(updateReview);
	}
	
	@DeleteMapping(value = "/deleteComment")
	public ResponseEntity<Review> deleteReview(@RequestParam Long id) {
		Review review = reviewService.findById(id);

		if (review != null) {
			reviewService.deleteItem(reviewService.findById(id));
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
