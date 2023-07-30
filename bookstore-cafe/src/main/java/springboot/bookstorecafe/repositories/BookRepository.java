package springboot.bookstorecafe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
