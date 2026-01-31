package springboot.bookstorecafe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.LoginPerson;

@Repository
public interface LoginPersonRepository  extends JpaRepository<LoginPerson, Long>{

}
