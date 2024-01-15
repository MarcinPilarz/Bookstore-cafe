package springboot.bookstorecafe.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_event")
	private Long idEvent;

	@NotNull
	@Column(name = "event_name")
	private String eventName;

	@NotNull
	@Column(name = "event_description")
	private String eventDescription;

	@NotNull
	@Column(name = "events_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date eventsDate;

	@ManyToOne

	@JoinColumn(name = "id_person")
	private Person person;

	public Long getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(Long idEvent) {
		this.idEvent = idEvent;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public Date getEventsDate() {
		return eventsDate;
	}

	public void setEventsDate(Date eventsDate) {
		this.eventsDate = eventsDate;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
