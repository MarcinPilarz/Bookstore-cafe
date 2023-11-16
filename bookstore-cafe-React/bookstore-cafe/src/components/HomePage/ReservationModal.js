import React, { useState } from "react";
import "./ReservationModal.css";
const ReservationModal = ({ productData, closeModal }) => {
  const [currentProductIndex, setCurrentProductIndex] = useState(0);

  const handleNext = () => {
    if (currentProductIndex < productData.length - 1) {
      setCurrentProductIndex(currentProductIndex + 1);
    }
  };

  const handlePrev = () => {
    if (currentProductIndex > 0) {
      setCurrentProductIndex(currentProductIndex - 1);
    }
  };
  return (
    <div className="modal">
      {/* Wyświetlanie danych produktu na podstawie currentProductIndex */}
      {/* <button onClick={handlePrev}>Poprzedni</button>
      <button onClick={handleNext}>Następny</button> */}
      <button onClick={closeModal}>Zamknij</button>
    </div>
  );
};

export default ReservationModal;
