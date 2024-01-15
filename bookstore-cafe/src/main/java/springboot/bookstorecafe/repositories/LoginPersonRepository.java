package springboot.bookstorecafe.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.LoginPerson;
import springboot.bookstorecafe.models.RoleType;

@Repository
public interface LoginPersonRepository extends JpaRepository<LoginPerson, Long> {

	Optional<LoginPerson> findByEmail(String email);

	List<LoginPerson> findByRoleType(RoleType roleType);

}
