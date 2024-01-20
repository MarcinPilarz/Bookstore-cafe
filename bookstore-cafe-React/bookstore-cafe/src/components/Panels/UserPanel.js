import axios from "axios";
import React, { useEffect, useState } from "react";
import Footer from "../Footer/Footer";
import { useAuth } from "../Login/LoginInfoContext";
import Navbar from "../Navbar/Navbar";
import "./UserPanel.css";

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
  const [selectedComments, setSelectedComments] = useState([]);
  const [editFormData, setEditFormData] = useState({
    firstName: "",
    lastName: "",
    phoneNumber: "",
  });
  const [passwordData, setPasswordData] = useState({
    oldPassword: "",
    newPassword: "",
  });

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

  const handleEditClick = () => {
    setIsEditing(true);
    setIsChangingPassword(false);
  };

  const handleChangePasswordClick = () => {
    setIsChangingPassword(true);
    setIsEditing(false);
  };

  const handleEditSubmit = async (event) => {
    event.preventDefault();
    await handleUpdateEmployee(clientData.idPerson, editFormData);
    setIsEditing(false);
  };

  const handleChangePasswordSubmit = async (event) => {
    event.preventDefault();

    setIsChangingPassword(false);
  };

  const handleUpdateEmployee = async (id, employeeData) => {
    if (!id) {
      console.error("Identyfikator użytkownika jest niezdefiniowany");
      return;
    }

    try {
      await axios.put(`http://localhost:8080/editUser?id=${id}`, employeeData);

      console.log("Użytkownik został pomyślnie zaktualizowany");
    } catch (error) {
      console.error("Error updating employee", error);
    }
  };
  useEffect(() => {
    if (idPerson) {
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
    }
  }, [idPerson]);

  const handleCheckboxChange = (commentId, isChecked) => {
    if (isChecked) {
      setSelectedComments([...selectedComments, commentId]);
    } else {
      setSelectedComments(selectedComments.filter((id) => id !== commentId));
    }
  };

  const deleteComments = async () => {
    const successfullyDeleted = [];

    for (const commentId of selectedComments) {
      try {
        const response = await fetch(
          `http://localhost:8080/deleteComment?id=${commentId}`,
          {
            method: "DELETE",
            headers: {
              Authorization: `Bearer ${authData?.token}`,
            },
          }
        );

        if (!response.ok) {
          throw new Error(`Error: ${response.status}`);
        }

        successfullyDeleted.push(commentId);
        console.log(`Komentarz o ID ${commentId} został usunięty`);
      } catch (error) {
        console.error(
          `Nie udało się usunąć komentarza o ID ${commentId}:`,
          error
        );
      }
    }

    const updatedReviews = clientReviewsData.filter(
      (review) => !successfullyDeleted.includes(review.idReview)
    );
    setClientReviewsData(updatedReviews);
    setSelectedComments([]);
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleString();
  };
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
        return (
          <div className="reservations-section">
            <div className="reservations-container">
              {Array.isArray(clientReservationData) &&
              clientReservationData.length > 0 ? (
                clientReservationData.map((reservation) => (
                  <div
                    key={reservation.idReservation}
                    className="reservation-item"
                  >
                    <p>
                      Data rezerwacji:{" "}
                      {new Date(reservation.bookingDate).toLocaleDateString()}
                    </p>
                    <p>
                      Ilość zarezerwowanych miejsc: {reservation.numberOfPeople}
                    </p>
                    {reservation.bookTable && (
                      <p>Numer stolika: {reservation.bookTable.idBookTable}</p>
                    )}
                  </div>
                ))
              ) : (
                <div className="no-reservations">Brak rezerwacji.</div>
              )}
            </div>
            <div className="contact-info">
              <p>
                Jeśli masz jakieś pytania, chcesz odwołać lub zmodyfikować
                rezerwację, zadzwoń pod numer:
              </p>
              <p>+48 123 456 789</p>
            </div>
          </div>
        );

      case "orderHistory":
        return Array.isArray(clientHistoryOrdersData) &&
          clientHistoryOrdersData.length > 0 ? (
          <div className="order-history-container">
            {clientHistoryOrdersData.map((historyOrders) => (
              <div
                key={historyOrders.idOrderHistory}
                className="order-history-item"
              >
                {historyOrders.product && (
                  <p>Nazwa produktu: {historyOrders.product.productName}</p>
                )}
                <p>Ilość: {historyOrders.quantity}</p>

                <p>
                  Data zamówienia:{" "}
                  {new Date(historyOrders.dateOrder).toLocaleDateString()}
                </p>
              </div>
            ))}
          </div>
        ) : (
          <div className="no-orders">Brak zamówień.</div>
        );
      case "myComments":
        return (
          <div className="comments-container">
            {Array.isArray(clientReviewsData) &&
            clientReviewsData.length > 0 ? (
              clientReviewsData.map((review) => (
                <div key={review.idReview} className="comment-item">
                  <p>Treść komenatarza: {review.reviewContent}</p>
                  <p>Ocena: {review.rating}</p>
                  <p>
                    Data opublikowania:{" "}
                    {new Date(review.reviewData).toLocaleDateString()}
                  </p>
                  <div className="checkbox-container">
                    <input
                      type="checkbox"
                      id={`checkbox-${review.idReview}`}
                      onChange={(e) =>
                        handleCheckboxChange(review.idReview, e.target.checked)
                      }
                    />
                    <label
                      htmlFor={`checkbox-${review.idReview}`}
                      className="checkbox-label"
                    >
                      Zaznacz do usunięcia
                    </label>
                  </div>
                </div>
              ))
            ) : (
              <div className="no-comments">Brak komentarzy.</div>
            )}
            <button className="delete-button" onClick={deleteComments}>
              Usuń zaznaczone
            </button>
          </div>
        );
      case "myOrders":
        return (
          <div className="orders-container">
            {Array.isArray(clientOrdersData) && clientOrdersData.length > 0 ? (
              clientOrdersData.map((orders, index) => {
                return (
                  <div
                    key={orders.idWholeOrderPerson}
                    className="order-container"
                  >
                    <p className="data-order-highlight">
                      Data zamówienia: {formatDate(orders.dateOrder)}
                    </p>

                    <div className="order-number">
                      Numer : <br />
                      {orders.idWholeOrderPerson}
                    </div>
                    <p>Status zamówienia: {orders.orderStatus}</p>
                    <p>Cena: {orders.totalPrice}</p>
                    {orders.order.map((productItem) => (
                      <div
                        key={productItem.idOrderProduct}
                        className="product-item"
                      >
                        <p>Ilość: {productItem.quantity}</p>
                        <p>Nazwa produktu: {productItem.product.productName}</p>
                      </div>
                    ))}
                  </div>
                );
              })
            ) : (
              <div>Brak rezerwacji.</div>
            )}
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
