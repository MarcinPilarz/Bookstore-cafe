import {
  faBars,
  faBasketShopping,
  faClock,
  faCircleUser,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useCart } from "../ProductSection/BusketProducts";
import "./Navbar.css";
import { useAuth } from "../Login/LoginInfoContext";
const Navbar = () => {
  const [showMenuBars, setShowMenuBars] = useState(false);
  // const { isOpen, setIsOpen, handleClose } = useContext(BusketSideBar);

  const [busketOrderBar, setBusketOrderBar] = useState(false);
  const [historyOrderBar, setHistoryOrderBar] = useState(false);
  const [infoImageVisible, setinfoImageVisible] = useState(true);
  const [userActionBar, setUserActionBar]= useState(false);
  const {
    busket,
    setBusket,
    clearBusket,
    updateProductQuantity,
    removeFromBusket,
  } = useCart();

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
  setinfoImageVisible(!infoImageVisible);
  console.log("UserBar", !userActionBar)
}
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

  const loadBusket = (idPerson) => {
    const busketKey = `busket_${idPerson}`;
    const savedBusket = localStorage.getItem(busketKey);
    return savedBusket ? JSON.parse(savedBusket) : [];
  };

  useEffect(() => {
    if (authData && authData.idPerson) {
      // Wczytanie i ustawienie stanu koszyka dla zalogowanego użytkownika
      const newBusket = loadBusket(authData.idPerson);
      setBusket(newBusket);
    } else {
      // Jeśli użytkownik nie jest zalogowany, wyczyść koszyk
      setBusket([]);
    }
  }, [authData, setBusket]);

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

  useEffect(() => {
    // Logika wczytywania koszyka lub resetowania stanu
    // w zależności od aktualnego idPerson
  }, [authData?.idPerson]);
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
                      <button
                        onClick={() => removeFromBusket(product.idProduct)}
                      >
                        X
                      </button>
                      {/* inne informacje o produkcie */}
                    </div>
                  ))}
                </div>
                <button>
                  <Link to="/summaryOrder"> Podsumowanie</Link>
                </button>
                <button onClick={clearBusket}>Wyczyść koszyk</button>{" "}
                {/* Przycisk Wyczyść */}
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

          <div className="user-icon" onClick={userBar}>
            <FontAwesomeIcon icon={faCircleUser} />
          </div>
          {userActionBar && authData?.token==null && (
            <div>
            <Link to="/signin">Zaloguj się</Link>
            <Link to="/signup">Zarejestruj się</Link>
            </div>
          )}

          {userActionBar && authData?.token && (
            <div>
              {authData?.roleType ==="Klient" &&(
                <Link to="/user-panel">Mój profil</Link>
                
              )}

{authData?.roleType ==="Pracownik" &&(
                <div>Profil pracownika</div>
                
              )}
              {authData?.roleType ==="Wlasciciel" &&(
                <div>Profil administratora</div>
                
              )}
              {/* zmien role na klienta jesli pracownik i wlasciciel ma wolne */}
              <div  onClick={logout}>Wyloguj</div>
            </div>
          )}


        </div>
      </nav>
    </header>
  );
};

export default Navbar;
