import React, { useState } from "react";
import axios from "axios";
import DatePicker from "react-datepicker";
import Select from "react-select";
import { useAuth } from "../Login/LoginInfoContext";
import "react-datepicker/dist/react-datepicker.css";
import "./ReservationModal.css";

const ReservationModal = ({ closeModal }) => {
  const [currentProductIndex, setCurrentProductIndex] = useState(0);
  const [reservationDate, setReservationDate] = useState(new Date());
  const [numberOfPeople, setNumberOfPeople] = useState(1);
  const { authData, logout } = useAuth();

  const handleNumberOfPeopleChange = (e) => {
    const value = parseInt(e.target.value, 10);
    if (!isNaN(value) && value >= 1) {
      setNumberOfPeople(value);
    }
  };

  const handleReservation = () => {
    const formData = {
      reservationDate: reservationDate.toISOString().split("T")[0], // Konwersja daty do formatu "YYYY-MM-DD"
      numberOfPeople,
    };

    axios
      .post("/api/reservations", formData)
      .then((response) => {
        console.log("Reservation successful:", response.data);
        closeModal();
      })
      .catch((error) => {
        console.error("Error during reservation:", error);
      });
  };

  const handleSubmitReservation = async () => {
    const formattedDate = reservationDate.toISOString().split("T")[0];
    var idPerson = authData.idPerson;

    try {
      const response = await axios.post(
        `http://localhost:8080/newReservation?idPerson=${authData.idPerson}&bokkingData=${formattedDate}&numberOfPeople=${numberOfPeople}`,
        {}
      );
      console.log("Pomyslna rejestracja!");
    } catch (error) {}
  };

  return (
    <div className="modal">
      <div className="reservation-container">
        <div className="reservation-date">
          <label>Data rezerwacji:</label>
          <DatePicker
            className="date-picker"
            selected={reservationDate}
            onChange={(date) => setReservationDate(date)}
            showTimeSelect
            timeFormat="HH:mm"
            timeIntervals={15}
            dateFormat="yyyy-MM-dd HH:mm"
            minDate={new Date()}
            maxDate={null}
          />
        </div>

        <div className="reservation-value-people">
          <label>Liczba osób:</label>
          <input
            className="input-value-people"
            type="number"
            value={numberOfPeople}
            onChange={handleNumberOfPeopleChange}
          />
        </div>
      </div>
      <div className="reservation-button-container">
        <button className="close-modal-reservation" onClick={closeModal}>
          Zamknij
        </button>
        <button
          className="accept-modal-reservation"
          onClick={handleSubmitReservation}
        >
          Zarezerwuj
        </button>
      </div>
    </div>
  );
};

export default ReservationModal;
