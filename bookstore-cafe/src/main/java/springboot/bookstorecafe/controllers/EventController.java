package springboot.bookstorecafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.DTO.EventDTO;
import springboot.bookstorecafe.models.Event;
import springboot.bookstorecafe.services.EventService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {

	@Autowired
	private EventService eventService;

	
	@GetMapping(value = "/events")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Event> getEvents() {
		return eventService.findAllItems();
	}

	@PostMapping(value = "/newEvent")
	public ResponseEntity<Event> addNewEvent(@RequestBody Event newEvent) {

		eventService.addItem(newEvent);
		return ResponseEntity.ok(newEvent);
	}

	@PutMapping(value = "/updateEvent")
	public ResponseEntity<String> updateEvent(@RequestParam Long id, @RequestBody EventDTO updateEventDTO) {
		Event event = eventService.findById(id);

		event.setEventName(updateEventDTO.eventName());
		event.setEventDescription(updateEventDTO.eventDescription());

		eventService.updateItem(event);
		return ResponseEntity.ok("The event has been updated");
	}

	@DeleteMapping(value = "/deleteEvent")
	public ResponseEntity<Event> deleteEvent(@RequestParam Long id) {
		Event event = eventService.findById(id);
		if (event != null) {
			eventService.deleteItem(eventService.findById(id));
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}
}
