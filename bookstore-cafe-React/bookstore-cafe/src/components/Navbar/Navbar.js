import {
  faBars,
  faBasketShopping,
  faClock,
  faCircleUser,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useState, useEffect } from "react";
import { useCart } from "../ProductSection/BusketProducts";
import "./Navbar.css";

const Navbar = () => {
  const [showMenuBars, setShowMenuBars] = useState(false);
  // const { isOpen, setIsOpen, handleClose } = useContext(BusketSideBar);

  const [busketOrderBar, setBusketOrderBar] = useState(false);
  const [historyOrderBar, setHistoryOrderBar] = useState(false);
  const [infoImageVisible, setinfoImageVisible] = useState(true);

  const { busket, addToBusket } = useCart();
  const [cartItemCount, setCartItemCount] = useState(0);
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

  useEffect(() => {
    // Aktualizuj liczbę produktów w koszyku po zmianie koszyka
    setCartItemCount(busket.reduce((total, item) => total + item.quantity, 0));
  }, [busket]);

  // const getProductInfo = (productId) => {
  //   const product = busket.find((item) => item.productId === productId);

  //   if (product && product.product) {
  //     return {
  //       productName:
  //         product.product.productName || "Nazwa produktu niedostępna",
  //       // inne parametry produktu, np. cena itp.
  //     };
  //   } else {
  //     return {
  //       productName: "Nazwa produktu niedostępna",
  //       // inne domyślne wartości dla pozostałych parametrów
  //     };
  //   }
  // };

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
            <FontAwesomeIcon icon={faBasketShopping} />
            <span className="amount-of-products">{cartItemCount}</span>
          </div>
          <div
            className={`busket-sidebar${!busketOrderBar ? "openBusket" : ""}`}
          >
            {busket.length > 0 && busketOrderBar && (
              <div className="busket-sidebar openBusket">
                <div className="order-item-list">
                  {busket.map((product) => (
                    <div key={product.idProduct}>
                      {console.log("Produkt w koszyku:", product)}
                      {console.log(
                        "product.productId W nVBAR",
                        product.productId
                      )}{" "}
                      {/* Użyj unikalnego identyfikatora */}
                      <p>
                        <b>Nazwa produktu:</b>
                        <br />
                        {product.productName}
                      </p>
                      <p>
                        <b>Ilość: </b>
                        <br />
                        {product.quantity}
                      </p>
                      {/* inne informacje o produkcie */}
                    </div>
                  ))}
                </div>
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
          <div className="user-icon">
            <FontAwesomeIcon icon={faCircleUser} />
          </div>
        </div>
      </nav>
    </header>
  );
};

export default Navbar;
