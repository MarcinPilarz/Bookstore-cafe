package springboot.bookstorecafe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.bookstorecafe.models.Event;
import springboot.bookstorecafe.repositories.EventRepository;

@Service
public class EventService implements MainService<Event> {

	@Autowired
	private EventRepository eventRepo;

	@Override
	public List<Event> findAllItems() {

		return eventRepo.findAll();
	}

	@Override
	public void addItem(Event event) {

		eventRepo.save(event);

	}

	@Override
	public void deleteItem(Event event) {
		eventRepo.delete(event);

	}

	@Override
	public void updateItem(Event event) {
		eventRepo.save(event);

	}

	@Override
	public Event findById(Long id) {
	
		return eventRepo.findById(id).orElse(null);
	}

}
