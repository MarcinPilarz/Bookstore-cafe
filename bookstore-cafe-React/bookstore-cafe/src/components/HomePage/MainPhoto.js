import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./MainPhoto.css";
const MainPhoto = ({ infoImageVisible }) => {
  const navigate = useNavigate();

  const handleProductNavigate = () => {
    navigate("/products-page/ALLPRODUCTS");
  };

  return (
    <div className="homepage-photo  img-main-page-photo">
      <img
        id="main-photo"
        src="https://storage.googleapis.com/springbootphoto/springbootphoto/MainPhotoHomePage.jpg"
        alt="Nasza firma"
      />

      <div className="info-image-container">
        <h1 className="h1-image">Czytaj z kawą</h1>
        <p className="p-image">Zamów kawe i wejdz do świata ksiązki.</p>
        <button onClick={handleProductNavigate} className="button-image">
          Produkty
        </button>
      </div>
    </div>
  );
};

export default MainPhoto;
