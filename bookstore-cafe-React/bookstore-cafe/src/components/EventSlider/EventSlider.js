import React, { useState, useEffect } from "react";
import axios from "axios";
import { useAuth } from "../Login/LoginInfoContext";
import "./EventSlider.css";

const EventSlider = () => {
  const [events, setEvents] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [direction, setDirection] = useState("next");
  const { authData } = useAuth();
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

  // useEffect(() => {
  //   console.log("Token przed ustawieniem nagłówka:", authData.token);

  //   axios.defaults.headers.common["Authorization"] = `Bearer ${authData.token}`;
  //   console.log(
  //     "Nagłówek Authorization ustawiony:",
  //     axios.defaults.headers.common["Authorization"]
  //   );

  //   axios
  //     .get("http://localhost:8080/events")
  //     .then((response) => {
  //       const eventsData = response.data;
  //       console.log("Pobrane dane z API:", eventsData);
  //       setEvents(eventsData);
  //     })
  //     .catch((error) => {
  //       console.error("Błąd pobierania danych wydarzeń", error);
  //     });
  // }, [authData.token]);

  useEffect(() => {
    const fetchEvents = async () => {
      if (authData.token && new Date().getTime() < authData.expirationTime) {
        try {
          // Ustawienie nagłówka autoryzacji
          const config = {
            headers: {
              Authorization: `Bearer ${authData.token}`,
            },
          };

          // Wykonanie zapytania GET z dodanym nagłówkiem
          const response = await axios.get(
            "http://localhost:8080/events",
            config
          );
          console.log("Pobrane dane z API:", response.data);
          setEvents(response.data);
        } catch (error) {
          console.error("Błąd pobierania danych wydarzeń", error);
        }
      }
    };

    if (authData.token) {
      fetchEvents();
    }
  }, [authData.token]);

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
      <button className="prev-button" onClick={handlePrev}>
        &#x2190;
      </button>
      <button className="next-button" onClick={handleNext}>
        &#8594;
      </button>
    </div>
  );
};

export default EventSlider;
