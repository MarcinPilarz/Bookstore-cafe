package springboot.bookstorecafe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
