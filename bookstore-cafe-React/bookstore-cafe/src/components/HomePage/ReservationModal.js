import React, { useState } from "react";
import axios from "axios";
import DatePicker from "react-datepicker";
import Select from "react-select";
import "react-datepicker/dist/react-datepicker.css";
import "./ReservationModal.css";

// productData,
const ReservationModal = ({ closeModal }) => {
  const [currentProductIndex, setCurrentProductIndex] = useState(0);
  const [reservationDate, setReservationDate] = useState(new Date());
  const [numberOfPeople, setNumberOfPeople] = useState(1);

  // const handleNext = () => {
  //   if (currentProductIndex < productData.length - 1) {
  //     setCurrentProductIndex(currentProductIndex + 1);
  //   }
  // };

  // const handlePrev = () => {
  //   if (currentProductIndex > 0) {
  //     setCurrentProductIndex(currentProductIndex - 1);
  //   }
  // };

  const handleNumberOfPeopleChange = (e) => {
    const value = parseInt(e.target.value, 10);
    if (!isNaN(value) && value >= 1) {
      setNumberOfPeople(value);
    }
  };

  const handleReservation = () => {
    const formData = {
      // productId: productData[currentProductIndex].id,
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

  // const isTimeDisabled = (date) => {
  //   // Pobierz obecną godzinę i minutę
  //   const currentHour = new Date().getHours();
  //   const currentMinute = new Date().getMinutes();

  //   // Pobierz wybraną godzinę i minutę
  //   const selectedHour = date.getHours();
  //   const selectedMinute = date.getMinutes();

  //   // Sprawdź, czy wybrana godzina jest wcześniejsza niż obecna
  //   return (
  //     selectedHour < currentHour ||
  //     (selectedHour === currentHour && selectedMinute < currentMinute)
  //   );
  // };

  return (
    <div className="modal">
      {/* <h3>{productData[currentProductIndex].name}</h3>
      <p>{productData[currentProductIndex].description}</p> */}

      {/* Date Picker */}
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
            minDate={new Date()} // Minimalna data to dzisiejsza data
            maxDate={null} // Bez ograniczenia maksymalnej daty
            // filterTime={isTimeDisabled}
            // timeCaption="Godzina"
          />
        </div>

        <div className="reservation-table">
          <label className="">Dostępne stoliki:</label>
          <Select
            className="input-select"
            // options={tableOptions}
            // value={selectedTable}
            // onChange={handleTableChange}
            isSearchable={false} // Opcjonalne: aby wyłączyć wyszukiwanie w comboboxie
          />
        </div>
        {/* Input na liczbę osób */}
        <div className="reservation-value-people">
          <label>Liczba osób:</label>
          <input
            className="input-value-people"
            type="number"
            value={numberOfPeople}
            onChange={handleNumberOfPeopleChange}
          />
        </div>

        {/* Przyciski nawigacyjne */}
        {/* <button onClick={handlePrev}>Poprzedni</button>
      <button onClick={handleNext}>Następny</button> */}

        {/* Przycisk do dokonania rezerwacji */}
      </div>
      <div className="reservation-button-container">
        <button className="close-modal-reservation" onClick={closeModal}>
          Zamknij
        </button>
        <button
          className="accept-modal-reservation"
          onClick={handleReservation}
        >
          Zarezerwuj
        </button>
      </div>
    </div>
  );
};

export default ReservationModal;
