import React, { useState, useEffect } from "react";
import axios from "axios";
import "./UserPanel.css";
import Footer from "../Footer/Footer";
import Navbar from "../Navbar/Navbar";
import { useAuth } from "../Login/LoginInfoContext";

const UserPanel = () => {
  const [activeTab, setActiveTab] = useState("personalData");
  const [clientData, setClientData] = useState([]);
  const [clientReservationData, setClientReservationData] = useState([]);
  const [clientHistoryOrdersData, setClientHistoryOrdersData] = useState([]);
  const [clientReviewsData, setClientReviewsData] = useState([]);
  const [clientOrdersData, setClientOrdersData] = useState([]);
  const { authData } = useAuth();
  const idPerson = authData?.idPerson;

  const [isEditing, setIsEditing] = useState(false);
  const [isChangingPassword, setIsChangingPassword] = useState(false);

  const [editFormData, setEditFormData] = useState({
    firstName: "",
    lastName: "",
    phoneNumber: "",
  });
  const [passwordData, setPasswordData] = useState({
    oldPassword: "",
    newPassword: "",
  });

  // Obsługa zmiany wartości w formularzach
  const handleEditFormChange = (event) => {
    setEditFormData({
      ...editFormData,
      [event.target.name]: event.target.value,
    });
  };

  const handlePasswordFormChange = (event) => {
    setPasswordData({
      ...passwordData,
      [event.target.name]: event.target.value,
    });
  };

  // Funkcje do obsługi kliknięcia przycisku
  const handleEditClick = () => {
    setIsEditing(true);
    setIsChangingPassword(false);
  };

  const handleChangePasswordClick = () => {
    setIsChangingPassword(true);
    setIsEditing(false);
  };

  // Funkcja do obsługi przesyłania formularza edycji
  const handleEditSubmit = async (event) => {
    event.preventDefault();
    await handleUpdateEmployee(clientData.idPerson, editFormData);
    setIsEditing(false);
  };

  // Funkcja do obsługi przesyłania formularza zmiany hasła
  const handleChangePasswordSubmit = async (event) => {
    event.preventDefault();
    // Tutaj zaimplementuj logikę zmiany hasła
    setIsChangingPassword(false);
  };

  // Przykład funkcji aktualizującej pracownika
  const handleUpdateEmployee = async (id, employeeData) => {
    if (!id) {
      console.error("Identyfikator użytkownika jest niezdefiniowany");
      return;
    }

    try {
      await axios.put(`http://localhost:8080/editUser?id=${id}`, employeeData);
      // fetchEmployees();
      console.log("Użytkownik został pomyślnie zaktualizowany");
    } catch (error) {
      console.error("Error updating employee", error);
    }
  };
  useEffect(() => {
    if (idPerson) {
      // Pierwsze żądanie HTTP
      axios
        .get(`http://localhost:8080/personDetails?id=${idPerson}`)
        .then((response) => {
          const clientInfo = response.data;
          console.log("Pobrane dane produktow z API:", clientInfo);
          setClientData(clientInfo);
        })
        .catch((error) => {
          console.error("Błąd pobierania danych wydarzeń", error);
        });

      // Drugie żądanie HTTP
      axios
        .get(`http://localhost:8080/reservations/person?personId=${idPerson}`)
        .then((response) => {
          const clientReservationInfo = response.data;
          console.log("Pobrane dane rezerwacji z API:", clientReservationInfo);
          setClientReservationData(clientReservationInfo);
        })
        .catch((error) => {
          console.error("Błąd pobierania danych rezerwacji", error);
        });

      axios
        .get(`http://localhost:8080/history?personId=${idPerson}`)
        .then((response) => {
          const clientHistoryOrdersInfo = response.data;
          console.log(
            "Pobrane dane historii zamówień z API:",
            clientHistoryOrdersInfo
          );
          setClientHistoryOrdersData(clientHistoryOrdersInfo);
        })
        .catch((error) => {
          console.error("Błąd pobierania danych historii zamówień", error);
        });
      axios
        .get(`http://localhost:8080/history?personId=${idPerson}`)
        .then((response) => {
          const clientHistoryOrdersInfo = response.data;
          console.log(
            "Pobrane dane historii zamówień z API:",
            clientHistoryOrdersInfo
          );
          setClientHistoryOrdersData(clientHistoryOrdersInfo);
        })
        .catch((error) => {
          console.error("Błąd pobierania danych historii zamówień", error);
        });
    } else {
      console.log("idPerson jest undefined lub pusty");
    }
  }, [idPerson]);

  useEffect(() => {
    if (idPerson) {
      axios
        .get(`http://localhost:8080/history?personId=${idPerson}`)
        .then((response) => {
          const clientHistoryOrdersInfo = response.data;
          console.log(
            "Pobrane dane historii zamówień z API:",
            clientHistoryOrdersInfo
          );
          setClientHistoryOrdersData(clientHistoryOrdersInfo);
        })
        .catch((error) => {
          console.error("Błąd pobierania danych historii zamówień", error);
        });
    } else {
      console.log("idPerson jest undefined lub pusty");
      // Możesz tutaj obsłużyć sytuację, gdy idPerson jest undefined lub pusty
    }
  }, [idPerson]);

  useEffect(() => {
    if (idPerson) {
      axios
        .get(`http://localhost:8080/reviews/person?personId=${idPerson}`)
        .then((response) => {
          const clientReviewsInfo = response.data;
          console.log("Pobrane danych komentarzy z API:", clientReviewsInfo);
          setClientReviewsData(clientReviewsInfo);
        })
        .catch((error) => {
          console.error("Błąd pobierania danych komentarzy z API", error);
        });
    } else {
      console.log("idPerson jest undefined lub pusty");
      // Możesz tutaj obsłużyć sytuację, gdy idPerson jest undefined lub pusty
    }
  }, [idPerson]);

  useEffect(() => {
    if (idPerson) {
      axios
        .get(`http://localhost:8080/orders/person?personId=${idPerson}`)
        .then((response) => {
          const clientOrdersInfo = response.data;
          console.log("Pobrane danych zamówień z API:", clientOrdersInfo);
          setClientOrdersData(clientOrdersInfo);
        })
        .catch((error) => {
          console.error("Błąd pobierania zamówień", error);
        });
    } else {
      console.log("idPerson jest undefined lub pusty");
      // Możesz tutaj obsłużyć sytuację, gdy idPerson jest undefined lub pusty
    }
  }, [idPerson]);

  const renderContent = () => {
    switch (activeTab) {
      case "personalData":
        return (
          <div className="user-details-container">
            <div className="user-details-title">Dane osobowe</div>
            {clientData && (
              <div className="user-details">
                <p className="user-detail">Imię: {clientData.firstName}</p>
                <p className="user-detail">Nazwisko: {clientData.lastName}</p>
                <p className="user-detail">Telefon: {clientData.phoneNumber}</p>
                {clientData.loginPerson && clientData.loginPerson.email && (
                  <p className="user-detail">
                    E-mail: {clientData.loginPerson.email}
                  </p>
                )}
                <button className="edit-button" onClick={handleEditClick}>
                  Edytuj Dane
                </button>
                <button
                  className="change-password-button"
                  onClick={handleChangePasswordClick}
                >
                  Zmień Hasło
                </button>

                {isEditing && (
                  <form className="edit-form" onSubmit={handleEditSubmit}>
                    <input
                      className="edit-input"
                      type="text"
                      name="firstName"
                      placeholder="Imię"
                      value={editFormData.firstName}
                      onChange={handleEditFormChange}
                    />
                    <input
                      className="edit-input"
                      type="text"
                      name="lastName"
                      placeholder="Nazwisko"
                      value={editFormData.lastName}
                      onChange={handleEditFormChange}
                    />
                    <input
                      className="edit-input"
                      type="text"
                      name="phoneNumber"
                      placeholder="Telefon"
                      value={editFormData.phoneNumber}
                      onChange={handleEditFormChange}
                    />
                    <button className="submit-button" type="submit">
                      Zapisz zmiany
                    </button>
                  </form>
                )}

                {isChangingPassword && (
                  <form
                    className="change-password-form"
                    onSubmit={handleChangePasswordSubmit}
                  >
                    <input
                      className="password-input"
                      type="password"
                      name="oldPassword"
                      placeholder="Stare Hasło"
                      value={passwordData.oldPassword}
                      onChange={handlePasswordFormChange}
                    />
                    <input
                      className="password-input"
                      type="password"
                      name="newPassword"
                      placeholder="Nowe Hasło"
                      value={passwordData.newPassword}
                      onChange={handlePasswordFormChange}
                    />
                    <button className="submit-button" type="submit">
                      Zmień Hasło
                    </button>
                  </form>
                )}
              </div>
            )}
          </div>
        );

      case "myReservations":
        return Array.isArray(clientReservationData) &&
          clientReservationData.length > 0 ? (
          clientReservationData.map((reservation) => (
            <div key={reservation.idReservation}>
              {" "}
              {/* Użyj unikalnego klucza dla każdej rezerwacji */}
              <p>Data rezerwacji: {reservation.bokkingData}</p>
              <p>Ilość zarezerwowanych miejsc: {reservation.numberOfPeople}</p>
              {reservation.bookTable && (
                <p>Numer stolika: {reservation.bookTable.idBookTable}</p>
              )}
            </div>
          ))
        ) : (
          <div>Brak rezerwacji.</div>
        ); // Wyświetlane, gdy nie ma rezerwacji
      case "orderHistory":
        return Array.isArray(clientHistoryOrdersData) &&
          clientHistoryOrdersData.length > 0 ? (
          clientHistoryOrdersData.map((historyOrders) => (
            <div key={historyOrders.idOrderHistory}>
              {" "}
              {/* Użyj unikalnego klucza dla każdej rezerwacji */}
              {historyOrders.product && (
                <p>Nazwa produktu: {historyOrders.product.productName}</p>
              )}
              <p>Ilość: {historyOrders.quantity}</p>
              <p>Cena: {historyOrders.totalPrice}</p>
              <p>Data zamówienia: {historyOrders.dateOrder}</p>
            </div>
          ))
        ) : (
          <div>Brak rezerwacji.</div>
        ); // Wyświetlane, gdy nie ma rezerwacji
      case "myComments":
        return Array.isArray(clientReviewsData) &&
          clientReviewsData.length > 0 ? (
          clientReviewsData.map((reviews) => (
            <div key={reviews.idReview}>
              {" "}
              {/* Użyj unikalnego klucza dla każdej rezerwacji */}
              <p>Treść komenatza: {reviews.reviewContent}</p>
              <p>Ocena: {reviews.rating}</p>
              <p>Data opublikowania: {reviews.reviewData}</p>
            </div>
          ))
        ) : (
          <div>Brak komentarzy.</div>
        ); // Wyświetlane, gdy nie ma rezerwacji
      case "myOrders":
        // return (
        //     Array.isArray(clientOrdersData) && clientOrdersData.length > 0 ?
        //         clientOrdersData.map((orders) => (
        //             <div key={orders.idOrder}> {/* Użyj unikalnego klucza dla każdej rezerwacji */}
        //                 <p>Data zamówienia: {orders.dateOrder}</p>
        //                 <p>Ilośc: {orders.quantity}</p>
        //                 <p>Cena: {orders.totalPrice}</p>

        //                 {orders.products.map((product) => (
        //                     <p key={product.idProduct}>Nazwa produktu: {product.productName}</p>
        //                 ))}

        //             </div>
        //         )) :
        //         <div>Brak rezerwacji.</div> // Wyświetlane, gdy nie ma rezerwacji
        // );
        return (
          <div className="orders-container">
            {
              Array.isArray(clientOrdersData) && clientOrdersData.length > 0 ? (
                clientOrdersData.map((orders, index) => {
                  const isCloseToNextOrder =
                    index < clientOrdersData.length - 1 &&
                    Math.abs(
                      new Date(orders.dateOrder).getTime() -
                        new Date(
                          clientOrdersData[index + 1].dateOrder
                        ).getTime()
                    ) < 20000;

                  return (
                    <div
                      key={orders.idWholeOrderPerson}
                      className="order-container"
                    >
                      {!isCloseToNextOrder && (
                        <p className="data-order-highlight">
                          Data zamówienia: {orders.dateOrder}
                        </p>
                      )}
                      <p>Status zamówienia: {orders.orderStatus}</p>
                      <p>Cena: {orders.totalPrice}</p>
                      {orders.order.map((productItem) => (
                        <div
                          key={productItem.idOrderProduct}
                          className="product-item"
                        >
                          <p>Ilość: {productItem.quantity}</p>
                          <p>
                            Nazwa produktu: {productItem.product.productName}
                          </p>
                        </div>
                      ))}
                    </div>
                  );
                })
              ) : (
                <div>Brak rezerwacji.</div>
              ) // Wyświetlane, gdy nie ma rezerwacji
            }
          </div>
        );
      default:
        return <div>Dane osobowe</div>;
    }
  };

  return (
    <>
      <Navbar />
      <div className="dashboard">
        <div className="tabs">
          <button onClick={() => setActiveTab("personalData")}>
            Dane osobowe
          </button>
          <button onClick={() => setActiveTab("myOrders")}>
            Moje zamówienia
          </button>
          <button onClick={() => setActiveTab("orderHistory")}>
            Historia zamówień
          </button>
          <button onClick={() => setActiveTab("myReservations")}>
            Moje rezerwacje
          </button>
          <button onClick={() => setActiveTab("myComments")}>
            Moje komentarze
          </button>
        </div>
        <div className="content">{renderContent()}</div>
      </div>
      <Footer />
    </>
  );
};

export default UserPanel;
