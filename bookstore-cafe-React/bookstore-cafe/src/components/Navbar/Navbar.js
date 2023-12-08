import {
  faBars,
  faBasketShopping,
  faClock,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useState } from "react";
import "./Navbar.css";

const Navbar = () => {
  const [showMenuBars, setShowMenuBars] = useState(false);
  // const { isOpen, setIsOpen, handleClose } = useContext(BusketSideBar);

  const [busketOrderBar, setBusketOrderBar] = useState(false);
  const [historyOrderBar, setHistoryOrderBar] = useState(false);
  const [infoImageVisible, setinfoImageVisible] = useState(true);

  const ProductsNavigate = () => {
    const element = document.getElementById("products");
    if (element) {
      element.scrollIntoView({ behavior: "smooth" });
    } else {
      window.location.href = "/";
    }

    const element2 = document.getElementById("products");
    if (element2) {
      element2.scrollIntoView({ behavior: "smooth" });
    }
  };

  const ReservationNavigate = () => {
    const element3 = document.getElementById("reservation");
    if (element3) {
      element3.scrollIntoView({ behavior: "smooth" });
    } else {
      window.location.href = "/";
    }

    const element4 = document.getElementById("reservation");
    if (element4) {
      element4.scrollIntoView({ behavior: "smooth" });
    }
  };

  const EventsNavigate = () => {
    const element5 = document.getElementById("events");
    if (element5) {
      element5.scrollIntoView({ behavior: "smooth" });
    } else {
      window.location.href = "/";
    }

    const element6 = document.getElementById("events");
    if (element6) {
      element6.scrollIntoView({ behavior: "smooth" });
    }
  };

  const toggleMenu = () => {
    setShowMenuBars(!showMenuBars);
    setBusketOrderBar(false);
    console.log(
      "Menu zostało przełączone. Aktualny stan menuOpen:",
      !showMenuBars
    );
  };

  const busketBar = () => {
    setBusketOrderBar(!busketOrderBar);
    setShowMenuBars(false);
    setHistoryOrderBar(false);
    setinfoImageVisible(!infoImageVisible);
    console.log(
      "Menu zostało przełączone. Aktualny stan menuOpen:",
      !busketOrderBar,
      "Stan napisów",
      infoImageVisible
    );
  };

  const historyBar = () => {
    setHistoryOrderBar(!historyOrderBar);
    setShowMenuBars(false);
    setBusketOrderBar(false);
    setinfoImageVisible(!infoImageVisible);
    console.log(
      "Menu zostało przełączone. Aktualny stan menuOpen:",
      !historyOrderBar,
      "Stan napisów",
      infoImageVisible
    );
  };
  // const location = useLocation();

  // // Sprawdź, czy jesteś na stronie WydarzeniaPage
  // const isWydarzeniaPage = location.pathname === "/reservation";

  // // Ukryj Navbar na WydarzeniaPage
  // if (isWydarzeniaPage) {
  //   return null;
  // }
  return (
    <header>
      <nav className={`bar-icon ${showMenuBars ? "open" : ""}`}>
        <div className="logo-nav">
          <img src="https://storage.googleapis.com/springbootphoto/springbootphoto/Czytaj%20z%20Kaw%C4%85%20logo.png"></img>
        </div>
        <div onClick={toggleMenu} className="hamburger">
          <FontAwesomeIcon icon={faBars} />
        </div>

        <ul className="navbar">
          <li>
            {" "}
            <a href="/#">Strona główna</a>
          </li>
          <li>
            {" "}
            <a onClick={ProductsNavigate}>Produkty</a>
          </li>
          <li>
            <a onClick={ReservationNavigate}>Rezerwacja</a>
          </li>
          <li>
            {" "}
            <a onClick={EventsNavigate}>Wydarzenia</a>{" "}
          </li>

          <li className="sign-in">
            <a href="/login">Zaloguj</a>
          </li>
          <li className="sign-up">
            <a href="/login">Zarejestruj</a>
          </li>
        </ul>

        <div className="icon-container">
          <div className="busket-icon" onClick={busketBar}>
            {" "}
            <FontAwesomeIcon icon={faBasketShopping} />{" "}
          </div>
          <div
            className={`busket-sidebar${!busketOrderBar ? "openBusket" : ""}`}
          >
            {busketOrderBar && (
              <div className="order-item-list">
                <p>
                  <b>Nazwa produktu:</b>
                  <br />
                  Americano{" "}
                </p>
                <p>
                  <b>Ilość: </b>
                  <br />2{" "}
                </p>
                <p>
                  <b>Cena: </b>
                  <br />
                  40zł{" "}
                </p>
              </div>
            )}
            {busketOrderBar && (
              <div className="button-container">
                <button className="summary-button">Podsumowanie</button>
                <button className="clear-button">Wyczyść</button>
              </div>
            )}
          </div>

          <div className="history-icon" onClick={historyBar}>
            <FontAwesomeIcon icon={faClock} />
          </div>

          <div
            className={`history-sidebar${!historyOrderBar ? "openBusket" : ""}`}
          >
            {historyOrderBar && (
              <div className="order-item-list">
                <p>
                  <b>Nazwa produktu:</b>
                  <br />
                  Americano{" "}
                </p>
                <p>
                  <b>Ilość: </b>
                  <br />2{" "}
                </p>
                <p>
                  <b>Cena: </b>
                  <br />
                  40zł{" "}
                </p>
              </div>
            )}
            {historyOrderBar && (
              <div className="button-container">
                <button className="summary-button">Podsumowanie</button>
                <button className="clear-button">Wyczyść</button>
              </div>
            )}
          </div>
        </div>
      </nav>
    </header>
  );
};

export default Navbar;
