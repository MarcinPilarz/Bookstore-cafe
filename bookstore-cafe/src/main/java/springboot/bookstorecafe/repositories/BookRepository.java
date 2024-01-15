package springboot.bookstorecafe.repositories;

import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.Book;

@Repository
public interface BookRepository extends ProductRepository<Book> {

}
