import {
  faBars,
  faBasketShopping,
  faBell,
  faCircleUser,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../Login/LoginInfoContext";
import { useCart } from "../ProductSection/BusketProducts";
import "./Navbar.css";
import RoleToggle from "./RoleToggle";
const Navbar = () => {
  const [showMenuBars, setShowMenuBars] = useState(false);

  const [busketOrderBar, setBusketOrderBar] = useState(false);
  const [historyOrderBar, setHistoryOrderBar] = useState(false);
  const [infoImageVisible, setinfoImageVisible] = useState(true);
  const [userActionBar, setUserActionBar] = useState(false);
  const {
    busket,
    setBusket,
    clearBusket,
    updateProductQuantity,
    removeFromBusket,
  } = useCart();
  const [barNotification, setBarNotification] = useState([]);
  const { authData, logout } = useAuth();
  const idPerson = authData?.idPerson;
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

  const userBar = () => {
    setUserActionBar(!userActionBar);
    setShowMenuBars(false);
    setBusketOrderBar(false);
    setHistoryOrderBar(false);
    setinfoImageVisible(!infoImageVisible);
    console.log("UserBar", !userActionBar);
  };
  const toggleMenu = () => {
    setShowMenuBars(!showMenuBars);
    setBusketOrderBar(false);
    setUserActionBar(false);
    console.log(
      "Menu zostało przełączone. Aktualny stan menuOpen:",
      !showMenuBars
    );
  };

  const busketBar = () => {
    setBusketOrderBar(!busketOrderBar);
    setUserActionBar(false);
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
    setUserActionBar(false);
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

  useEffect(() => {
    setCartItemCount(busket.reduce((total, item) => total + item.quantity, 0));
  }, [busket]);

  const loadBusket = (idPerson) => {
    const busketKey = `busket_${idPerson}`;
    const savedBusket = localStorage.getItem(busketKey);
    return savedBusket ? JSON.parse(savedBusket) : [];
  };

  useEffect(() => {
    if (authData && authData.idPerson) {
      const newBusket = loadBusket(authData.idPerson);
      setBusket(newBusket);
    } else {
      setBusket([]);
    }
  }, [authData, setBusket]);

  useEffect(() => {}, [authData?.idPerson]);

  useEffect(() => {
    if (idPerson) {
      axios
        .get(`http://localhost:8080/orders/person?personId=${idPerson}`)
        .then((response) => {
          const clientOrdersInfo = response.data;
          console.log("Pobrane danych zamówień z API:", clientOrdersInfo);
          setBarNotification(clientOrdersInfo);
        })
        .catch((error) => {
          console.error("Błąd pobierania zamówień", error);
        });
    } else {
      console.log("idPerson jest undefined lub pusty");
    }
  }, [idPerson]);
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
        </ul>
      </nav>
      <div className="icon-container">
        <div className="busket-icon" onClick={busketBar}>
          {" "}
          <FontAwesomeIcon icon={faBasketShopping} />
          <span className="amount-of-products">{cartItemCount}</span>
        </div>
        <div className={`busket-sidebar${!busketOrderBar ? "openBusket" : ""}`}>
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
                    <p>
                      <b>Nazwa produktu:</b>
                      <br />
                      {product.productName}
                    </p>
                    <p>
                      <b>Ilość: </b>
                      <br />
                      <input
                        type="number"
                        value={product.quantity}
                        onChange={(e) =>
                          updateProductQuantity(
                            product.idProduct,
                            Math.min(Math.max(1, e.target.value), 10)
                          )
                        }
                        min="1"
                        max="10"
                      />
                    </p>
                    <button onClick={() => removeFromBusket(product.idProduct)}>
                      X
                    </button>
                  </div>
                ))}
              </div>
              <button id="button-busket-color-reasume">
                <Link to="/summaryOrder"> Podsumowanie</Link>
              </button>
              <button id="button-busket-color-delete" onClick={clearBusket}>
                Wyczyść koszyk
              </button>{" "}
            </div>
          )}
        </div>

        <div className="history-icon" onClick={historyBar}>
          <FontAwesomeIcon icon={faBell} />
        </div>

        <div
          className={`history-sidebar${!historyOrderBar ? "openBusket" : ""}`}
        >
          {historyOrderBar && (
            <div className="order-item-list">
              {barNotification.map((status) => (
                <div key={status.idWholeOrderPerson}>
                  <div>
                    Twoje zamówienie o numerze {status.idWholeOrderPerson} jest{" "}
                    {status.orderStatus}
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
        <div className="user-icon" onClick={userBar}>
          <FontAwesomeIcon icon={faCircleUser} />
        </div>
        {userActionBar && authData?.token == null && (
          <div className="user-action-bar">
            <Link to="/signin">Zaloguj się</Link>
            <Link to="/signup">Zarejestruj się</Link>
          </div>
        )}

        {userActionBar && authData?.token && (
          <div className="user-action-bar">
            {authData?.roleType === "Klient" && (
              <>
                <Link to="/user-panel">Mój profil</Link>
                <RoleToggle />
              </>
            )}

            {authData?.roleType === "Pracownik" && (
              <>
                <Link to="/employee-panel">Profil pracownika</Link>
                <RoleToggle />
              </>
            )}
            {authData?.roleType === "Wlasciciel" && (
              <>
                <Link to="/owner-panel">Profil administratora</Link>
                <RoleToggle />
              </>
            )}
            <div onClick={logout}>Wyloguj</div>
          </div>
        )}
      </div>
    </header>
  );
};

export default Navbar;
