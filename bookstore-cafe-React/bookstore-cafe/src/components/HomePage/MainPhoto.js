import React, { useState } from "react";
import "./MainPhoto.css";
const MainPhoto = ({ infoImageVisible }) => {
  return (
    // <div className="blackscreen">
    //   Odwiedz nas

    <div className="homepage-photo  img-main-page-photo">
      <img
        id="main-photo"
        src="https://storage.googleapis.com/springbootphoto/springbootphoto/MainPhotoHomePage.jpg"
        alt="Nasza firma"
      />

      {/* <div className="logo-nav">
        <img src="https://storage.googleapis.com/springbootphoto/springbootphoto/Czytaj%20z%20Kaw%C4%85%20logo.png"></img>
      </div> */}
      {/* {infoImageVisible ? ( */}
      <div className="info-image-container">
        {/* {infoImageVisible && (
          <> */}

        <h1 className="h1-image">Czytaj z kawą</h1>
        <p className="p-image">Zamów kawe i wejdz do świata ksiązki.</p>
        <button className="button-image">Produkty</button>
        {/* </div> */}
        {/* </>
        )} */}
      </div>
      {/* ) : null} */}
    </div>
  );
};

export default MainPhoto;
