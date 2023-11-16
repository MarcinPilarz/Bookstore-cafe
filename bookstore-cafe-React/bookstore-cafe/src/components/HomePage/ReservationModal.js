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
  return (
    <div className="modal">
      {/* <h3>{productData[currentProductIndex].name}</h3>
      <p>{productData[currentProductIndex].description}</p> */}

      {/* Date Picker */}
      <label>Data rezerwacji:</label>
      <DatePicker
        selected={reservationDate}
        onChange={(date) => setReservationDate(date)}
        dateFormat="yyyy-MM-dd"
        minDate={new Date()} // Minimalna data to dzisiejsza data
        maxDate={null} // Bez ograniczenia maksymalnej daty
      />
      <label>Dostępne stoliki:</label>
      <Select
        // options={tableOptions}
        // value={selectedTable}
        // onChange={handleTableChange}
        isSearchable={false} // Opcjonalne: aby wyłączyć wyszukiwanie w comboboxie
      />

      {/* Input na liczbę osób */}
      <label>Liczba osób:</label>
      <input
        type="number"
        value={numberOfPeople}
        onChange={(e) => setNumberOfPeople(parseInt(e.target.value))}
      />

      {/* Przyciski nawigacyjne */}
      {/* <button onClick={handlePrev}>Poprzedni</button>
      <button onClick={handleNext}>Następny</button> */}

      {/* Przycisk do dokonania rezerwacji */}
      <button onClick={handleReservation}>Zarezerwuj</button>
      <button onClick={closeModal}>Zamknij</button>
    </div>
  );
};

export default ReservationModal;
