package springboot.bookstorecafe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.WholeOrderPerson;

@Repository
public interface WholeOrderPersonRepository extends JpaRepository<WholeOrderPerson, Long> {

}
