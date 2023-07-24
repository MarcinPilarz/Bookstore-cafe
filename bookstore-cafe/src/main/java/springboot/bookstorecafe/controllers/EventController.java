package springboot.bookstorecafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import springboot.bookstorecafe.models.Event;
import springboot.bookstorecafe.services.EventService;

@RestController
public class EventController {

	@Autowired
	private EventService eventService;
	
	@GetMapping(value="/events")
	public List<Event> getEvents(){
		return eventService.findAllItems();
	}
	
	@PostMapping(value="/newEvent")
	public ResponseEntity<Event> addNewEvent(@RequestBody Event newEvent){
		
		eventService.addItem(newEvent);
		return ResponseEntity.ok(newEvent);
	}
}
