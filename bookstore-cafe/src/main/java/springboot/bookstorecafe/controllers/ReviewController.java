package springboot.bookstorecafe.controllers;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import springboot.bookstorecafe.DTO.UpdateReviewDTO;
import springboot.bookstorecafe.models.Person;
import springboot.bookstorecafe.models.Reservation;
import springboot.bookstorecafe.models.Review;
import springboot.bookstorecafe.repositories.PersonRepository;
import springboot.bookstorecafe.services.ReviewService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired 
	private PersonRepository personRepo;
	
	@GetMapping(value ="/reviews")
	public List<Review> getReviews(){
		return reviewService.findAllItems();
	}
	
	@GetMapping(value= "/reviews/person")
	public ResponseEntity<List<Review>> getPersonReservations(@RequestParam Long personId) {
        try {
            List<Review> reviews = reviewService.getReviewPerson(personId);
            return ResponseEntity.ok(reviews);
        } catch (RuntimeException e) {
            // Możesz dostosować obsługę błędów w zależności od Twoich potrzeb
            return ResponseEntity.notFound().build();
        }
    }
	
	
	@PostMapping(value="/newComment")
	public ResponseEntity<Review> addReview(@RequestBody Review newReview, @RequestParam Long idPerson){
	
		Person person= personRepo.findById(idPerson).orElseThrow(() -> new EntityNotFoundException("A person with this id does not exist"));
		newReview.setPerson(person);
		reviewService.addItem(newReview);
		return ResponseEntity.ok(newReview);
		
	}
	
	
	@PutMapping(value="/editComment")
	public ResponseEntity<String> editReview(@RequestParam Long id, @RequestBody UpdateReviewDTO updateReviewDTO){
		Review review= reviewService.findById(id);
	
	if(review != null) {
		
		review.setNumberOfLikes(updateReviewDTO.numberOfLikes());
		reviewService.updateItem(review);
		return ResponseEntity.ok("");
	}
	else {
		 return ResponseEntity.notFound().build();
	}
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
