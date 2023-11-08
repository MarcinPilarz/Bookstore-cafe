import React, { useState, useEffect } from "react";
import axios from "axios";
import "./EventSlider.css";

const EventSlider = () => {
  const [events, setEvents] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [direction, setDirection] = useState("next");
  const prevIndex = currentIndex === 0 ? events.length - 1 : currentIndex - 1;
  const nextIndex = currentIndex === events.length - 1 ? 0 : currentIndex + 1;

  const currentEvent = events[currentIndex];
  const prevEvent = events[prevIndex];
  const nextEvent = events[nextIndex];

  const handleNext = () => {
    setCurrentIndex(nextIndex);
    setDirection("next"); // Ustaw kierunek na Następny
  };

  const handlePrev = () => {
    setCurrentIndex(prevIndex);
    setDirection("prev"); // Ustaw kierunek na Poprzedni
  };

  useEffect(() => {
    axios
      .get("http://localhost:8080/events")
      .then((response) => {
        const eventsData = response.data;
        console.log("Pobrane dane z API:", eventsData);
        setEvents(eventsData);
      })
      .catch((error) => {
        console.error("Błąd pobierania danych wydarzeń", error);
      });
  }, []);

  //   useEffect(() => {
  //     // Przesunięcie slidera o jedno wydarzenie w prawo co kilka sekund
  //     const interval = setInterval(() => {
  //       handleNext();
  //     }, 5000); // Zmień co 5 sekund

  //     return () => {
  //       clearInterval(interval);
  //     };
  //   }, [currentIndex]);

  const EventCard = ({ event }) => (
    <div className={`event-card ${direction === "next" ? "transition" : ""}`}>
      {event ? (
        <>
          <h2>{event.eventName}</h2>
          <p>{event.eventDescription}</p>
        </>
      ) : (
        <p>Brak dostępnych wydarzeń</p>
      )}
    </div>
  );

  return (
    <div className="slider">
      <div className="slider-container">
        <div className="slider-window small">
          <EventCard event={prevEvent} />
        </div>
        <div className="slider-window main-event">
          <EventCard event={currentEvent} />
        </div>
        <div className="slider-window small">
          <EventCard event={nextEvent} />
        </div>
      </div>
      <button onClick={handlePrev}>Poprzedni</button>
      <button onClick={handleNext}>Następny</button>
    </div>
  );
};

export default EventSlider;
