import React from "react";
import "./MainPhoto.css";
const MainPhoto = () => {
  return (
    <div className="blackscreen">
      Odwiedz nas
      <div className="homepage-photo  img-main-page-photo">
        <img
          id="main-photo"
          src="https://storage.googleapis.com/springbootphoto/springbootphoto/MainPhotoHomePage.jpg"
          alt="Nasza firma"
        />
      </div>
    </div>
  );
};

export default MainPhoto;
