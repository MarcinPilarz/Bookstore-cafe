package springboot.bookstorecafe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import springboot.bookstorecafe.models.OrderHistory;
import springboot.bookstorecafe.models.Person;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

	//List<OrderHistory> findByPerson(Long idPerson);//
	
	List<OrderHistory> findByPersonIdPerson(Long personId);
	
	@Query(value="SELECT * FROM order_history WHERE id_person = :idPerson", nativeQuery=true)
	List<OrderHistory> findByPersonNative(@Param("idPerson") Long idPerson);
}
