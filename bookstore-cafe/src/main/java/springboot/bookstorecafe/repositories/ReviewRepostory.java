package springboot.bookstorecafe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.Review;

@Repository
public interface ReviewRepostory extends JpaRepository<Review, Long> {

	// Review getBestComment(int numberOfLikes);

}
