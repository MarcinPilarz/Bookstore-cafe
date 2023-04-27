package springboot.bookstorecafe.models;

import java.sql.Date;

//import java.util.Date;  <-- Trzeba zobaczyć które jest poprawne 
import jakarta.persistence.*;


@Entity
@Table(name="event")
public class Event {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_event")
	private Long id_event;
	
	@Column(name="event_name")
	private String event_name;
	
	@Column(name="event_description")
	private String event_description;
	
	@Column(name="events_date")
	private Date events_date;

	public Long getId_event() {
		return id_event;
	}

	public void setId_event(Long id_event) {
		this.id_event = id_event;
	}

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public String getEvent_description() {
		return event_description;
	}

	public void setEvent_description(String event_description) {
		this.event_description = event_description;
	}

	public Date getEvents_date() {
		return events_date;
	}

	public void setEvents_date(Date events_date) {
		this.events_date = events_date;
	}
	
	
	
}
