import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBasketShopping, faBars } from "@fortawesome/free-solid-svg-icons";
import { BusketSideBar } from "../SideBars/BusketSideBar";
import { useLocation } from "react-router-dom";
import "./Navbar.css";

const Navbar = () => {
  const [showMenuBars, setShowMenuBars] = useState(false);
  // const { isOpen, setIsOpen, handleClose } = useContext(BusketSideBar);

  const [busketOrderBar, setBusketOrderBar] = useState(false);

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
    console.log(
      "Menu zostało przełączone. Aktualny stan menuOpen:",
      !busketOrderBar
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
            <a href="/products">Produkty</a>{" "}
          </li>
          <li>
            <a href="/#">Rezerwacja</a>
          </li>
          <li>
            {" "}
            <a href="/#">Wydarzenia</a>{" "}
          </li>

          <li className="sign-in">
            <a href="/login">Zaloguj</a>
          </li>
          <li className="sign-up">
            <a href="/login">Zarejestruj</a>
          </li>
        </ul>

        <div className="busket-icon" onClick={busketBar}>
          {" "}
          <FontAwesomeIcon icon={faBasketShopping} />{" "}
        </div>
        <div className={`busket-sidebar${!busketOrderBar ? "openBusket" : ""}`}>
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
      </nav>
    </header>
  );
};

export default Navbar;
