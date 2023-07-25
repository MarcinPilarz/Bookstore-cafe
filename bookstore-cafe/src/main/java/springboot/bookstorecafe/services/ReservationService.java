package springboot.bookstorecafe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.Reservation;
import springboot.bookstorecafe.repositories.ReservationRepository;

@Service
public class ReservationService implements MainService<Reservation> {

	
	@Autowired
	private ReservationRepository resevationRepo;
	
	@Override
	public List<Reservation> findAllItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addItem(Reservation object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteItem(Reservation obejct) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateItem(Reservation object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Reservation findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	public void cancleReservation(Long idReservation) {
		Reservation reservation= resevationRepo.findById(idReservation).orElseThrow(() -> new RuntimeException("name reservation not found"));
	}
}
