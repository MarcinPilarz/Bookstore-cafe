import React, { useState, useEffect } from "react";
import "./EmployeePanel.css";
import { useAuth } from "../Login/LoginInfoContext";
import axios from "axios";
const EmployeePanel = () => {
  const [activeTab, setActiveTab] = useState("dostepne zamowienia");
  const [eventsPanel, setEventsPanel] = useState([]);
  const [editEventId, setEditEventsId] = useState(null);
  const [editReservationId, setEditReservationId] = useState(null);
  const { authData } = useAuth();
  const [eventFormData, setEventFormData] = useState({
    eventName: "",
    eventDescription: "",
  });
  const [customEdtitReservationData, setCustomEditReservationData] = useState({
    numberOfPeople: "",
  });
  const [reservationPanel, setReservationPanel] = useState([]);
  const [orderPanel, setOrderPanel] = useState([]);

  const [orderStatuses, setOrderStatuses] = useState([]);
  const [selectedStatus, setSelectedStatus] = useState("");
  const [selectedStatuses, setSelectedStatuses] = useState({});
  const [selectedChangeStatus, setSelectedChangeStatus] = useState(orderStatuses[0] || "");
  const [selectedOrderId, setSelectedOrderId] = useState(null); // ID wybranego zamówienia do zaktualizowania
  const [statusPanel, setStatusPanel] = useState([]); 

  const handleAdd = () => {
    // Logika dodawania
    console.log("Dodawanie...");
  };

  const handleEdit = () => {
    // Logika edytowania
    console.log("Edytowanie...");
  };

  const handleDelete = () => {
    // Logika usuwania
    console.log("Usuwanie...");
  };

  useEffect(() => {
    const fetchEvents = async () => {
      if (authData?.token && new Date().getTime() < authData?.expirationTime) {
        try {
          // Ustawienie nagłówka autoryzacji
          const config = {
            headers: {
              Authorization: `Bearer ${authData.token}`,
            },
          };

          // Wykonanie zapytania GET z dodanym nagłówkiem
          const response = await axios.get(
            "http://localhost:8080/events",
            config
          );
          console.log("Pobrane dane z API:", response.data);
          setEventsPanel(response.data);
        } catch (error) {
          console.error("Błąd pobierania danych wydarzeń", error);
        }
      }
    };

    if (authData?.token) {
      fetchEvents();
    }
  }, [authData?.token]);

  const editEvent = (id) => {
    setEditEventsId(id);
    const eventToEdit = eventsPanel.find((event) => event.idEvent === id);
    setEventFormData({
      eventName: eventToEdit.eventName,
      eventDescription: eventToEdit.eventDescription,
    });
  };

  const updateEventInDatabase = async (id, updatedEvent) => {
    try {
      // Wysyłanie żądania PUT lub PATCH do Twojego API
      const response = await fetch(
        `http://localhost:8080/updateEvent?id=${id}`,
        {
          method: "PUT", // lub 'PATCH'
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(updatedEvent),
        }
      );

      if (!response.ok) {
        throw new Error("Network response was not ok");
      }

      // Możesz tutaj zaktualizować stan, jeśli jest to konieczne
      // na przykład, aby odzwierciedlić zmiany w UI
      setEventsPanel((currentEvents) => {
        return currentEvents.map((event) => {
          if (event.idEvent === id) {
            return { ...updatedEvent, idEvent: id };
          }
          return event;
        });
      });
    } catch (error) {
      console.error("There was a problem with the fetch operation:", error);
    }
  };

  const handleSave = (id) => {
    // Znajdź wydarzenie, które ma zostać zaktualizowane
    const eventToUpdate = eventsPanel.find((event) => event.idEvent === id);

    if (eventToUpdate) {
      // Przygotuj zaktualizowane dane wydarzenia
      const updatedEvent = {
        ...eventToUpdate,
        eventName: eventFormData.eventName,
        eventDescription: eventFormData.eventDescription,
      };

      // Zaktualizuj dane wydarzenia w Twoim stanie aplikacji lub bazie danych
      // Ta część zależy od Twojej architektury i sposobu przechowywania danych
      // Na przykład, możesz wywołać funkcję aktualizującą stan lub wysłać żądanie do API
      updateEventInDatabase(id, updatedEvent);

      // Resetuj stan formularza i wyjdź z trybu edycji
      setEditEventsId(null);
      setEventFormData({
        eventName: "",
        eventDescription: "",
      });
    }
  };

  const deleteEvent = async (id) => {
    // Potwierdzenie, że użytkownik chce usunąć wydarzenie
    if (!window.confirm("Czy na pewno chcesz usunąć to wydarzenie?")) {
      return;
    }

    // Usuwanie z bazy danych za pomocą API
    try {
      const response = await fetch(
        `http://localhost:8080/deleteEvent?id=${id}`,
        {
          method: "DELETE",
        }
      );

      if (!response.ok) {
        throw new Error("Network response was not ok");
      }

      // Usunięcie wydarzenia ze stanu lokalnego
      setEventsPanel((currentEvents) =>
        currentEvents.filter((event) => event.idEvent !== id)
      );
    } catch (error) {
      console.error("There was a problem with the fetch operation:", error);
    }
  };

  const handleCreateEvent = async (e) => {
    e.preventDefault();

    const formatDateForSpring = (date) => {
      const pad = (num) => (num < 10 ? `0${num}` : num);

      const year = date.getUTCFullYear();
      const month = pad(date.getUTCMonth() + 1);
      const day = pad(date.getUTCDate());

      return `${year}-${month}-${day}`;
    };
    const newEvent = {
      eventName: eventFormData.eventName,
      eventDescription: eventFormData.eventDescription,
      eventsDate: formatDateForSpring(new Date()), // data w formacie ISO
      person: {
        idPerson: authData.idPerson,
      },
    };

    try {
      const response = await fetch("http://localhost:8080/newEvent", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(newEvent),
      });
      console.log("response POST EVENTS", response.data);
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }

      // Dodaj wydarzenie do stanu lokalnego, lub odśwież listę wydarzeń
      // ...

      const addedEvent = await response.json(); // Nowo dodane wydarzenie z serwera

      // Aktualizacja stanu wydarzeń dodając nowe wydarzenie
      setEventsPanel((currentEvents) => [...currentEvents, addedEvent]);

      console.log("Formatted Date:", formatDateForSpring(new Date()));
      console.log("Added Event:", addedEvent);
      // Opcjonalnie: Resetuj formularz
      setEventFormData({
        eventName: "",
        eventDescription: "",
      });
    } catch (error) {
      console.error("There was a problem with the fetch operation:", error);
    }
  };

  //Rezerwacje

  useEffect(() => {
    const fetchRezervation = async () => {
      if (authData?.token && new Date().getTime() < authData?.expirationTime) {
        try {
          // Ustawienie nagłówka autoryzacji
          const config = {
            headers: {
              Authorization: `Bearer ${authData.token}`,
            },
          };

          // Wykonanie zapytania GET z dodanym nagłówkiem
          const response = await axios.get(
            "http://localhost:8080/reservations",
            config
          );
          console.log("Pobrane dane z API:", response.data);
          setReservationPanel(response.data);
        } catch (error) {
          console.error("Błąd pobierania danych rezerwacji", error);
        }
      }
    };

    if (authData?.token) {
      fetchRezervation();
    }
  }, [authData?.expirationTime, authData.token]);

  const editReservation = (id) => {
    setEditReservationId(id);
    const reservationToEdit = reservationPanel.find(
      (reservation) => reservation.idReservation === id
    );
    setCustomEditReservationData({
      numberOfPeople: reservationToEdit.numberOfPeople,
    });
  };

  const updateReservationInDatabase = async (id, updatedReservation) => {
    try {
      // Wysyłanie żądania PUT lub PATCH do Twojego API
      const response = await fetch(
        `http://localhost:8080/customNumberOfPeople?idReservation=${id}`,
        {
          method: "PUT", // lub 'PATCH'
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(updatedReservation),
        }
      );

      if (!response.ok) {
        throw new Error("Network response was not ok");
      }

      // Możesz tutaj zaktualizować stan, jeśli jest to konieczne
      // na przykład, aby odzwierciedlić zmiany w UI
      setReservationPanel((currentReservations) => {
        return currentReservations.map((reservation) => {
          if (reservation.idReservation === id) {
            return { ...updatedReservation, idReservation: id };
          }
          return reservation;
        });
      });
    } catch (error) {
      console.error("There was a problem with the fetch operation:", error);
    }
  };
  const handleSaveReservation = (id) => {
    // Znajdź wydarzenie, które ma zostać zaktualizowane
    const reservationToUpdate = reservationPanel.find(
      (reservation) => reservation.idReservation === id
    );

    if (reservationToUpdate) {
      // Przygotuj zaktualizowane dane wydarzenia
      const updatedReservation = {
        ...reservationToUpdate,
        numberOfPeople: customEdtitReservationData.numberOfPeople,
      };

      // Zaktualizuj dane wydarzenia w Twoim stanie aplikacji lub bazie danych
      // Ta część zależy od Twojej architektury i sposobu przechowywania danych
      // Na przykład, możesz wywołać funkcję aktualizującą stan lub wysłać żądanie do API
      updateReservationInDatabase(id, updatedReservation);

      // Resetuj stan formularza i wyjdź z trybu edycji
      setEditReservationId(null);
      setCustomEditReservationData({
        numberOfPeople: "",
      });
    }
  };

  const deleteReservation = async (id) => {
    // Potwierdzenie, że użytkownik chce usunąć wydarzenie
    if (!window.confirm("Czy na pewno chcesz usunąć tą rezerwacje?")) {
      return;
    }

    // Usuwanie z bazy danych za pomocą API
    try {
      const response = await fetch(
        `http://localhost:8080/cancleReservation?idReservation=${id}`,
        {
          method: "DELETE",
        }
      );

      if (!response.ok) {
        throw new Error("Network response was not ok");
      }

      // Usunięcie wydarzenia ze stanu lokalnego
      setReservationPanel((currentReservations) =>
        currentReservations.filter(
          (reservation) => reservation.idReservation !== id
        )
      );
    } catch (error) {
      console.error("There was a problem with the fetch operation:", error);
    }
  };

  useEffect(() => {
    const fetchOrder = async () => {
      if (authData?.token && new Date().getTime() < authData?.expirationTime) {
        try {
          const config = {
            headers: {
              Authorization: `Bearer ${authData.token}`,
            },
          };
          const response = await axios.get("http://localhost:8080/orders", config);
          console.log("Pobrane dane z API ZAMOWIENIA:", response.data);
          setOrderPanel(response.data);
  
          // Aktualizacja stanu selectedStatuses
          const newSelectedStatuses = {};
          response.data.forEach(order => {
            newSelectedStatuses[order.idWholeOrderPerson] = order.orderStatus;
          });
          setSelectedStatuses(newSelectedStatuses);
  
        } catch (error) {
          console.error("Błąd pobierania danych zamowien", error);
        }
      }
    };
  
    if (authData?.token) {
      fetchOrder();
    }
  }, [authData?.expirationTime, authData?.token]);;

  useEffect(() => {
    const fetchOrderStatuses = async () => {
      try {
        const response = await axios.get("http://localhost:8080/order-status");
        setOrderStatuses(response.data);
  
        // Aktualizacja wybranych statusów dla każdego zamówienia
        const newSelectedStatuses = {};
        response.data.forEach(order => {
          newSelectedStatuses[order.idWholeOrderPerson] = order.orderStatus;
        });
        setSelectedStatuses(newSelectedStatuses);
      } catch (error) {
        console.error("Error fetching order statuses", error);
      }
    };
  
    fetchOrderStatuses();
  }, []);
  const handleChangeStatus = (orderId, newStatus) => {
    setSelectedStatuses(prevStatuses => {
      const updatedStatuses = {...prevStatuses, [orderId]: newStatus};
      console.log('Updated statuses:', updatedStatuses);
      return updatedStatuses;
    });
  };
  const getCurrentStatusForOrder = (order) => {
    return selectedStatuses[order.idWholeOrderPerson] || order.orderStatus;
  };

  const updateOrderStatus = async (orderId, newStatus) => {
    try {
      const response = await fetch(`http://localhost:8080/update-order-status?orderId=${orderId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${authData.token}`,
        },
        body: JSON.stringify({ orderStatus: newStatus })
      });
  
      if (response.ok) {
        console.log("Status zamówienia zaktualizowany");
  
        // Aktualizacja stanu orderPanel z nowym statusem
        setOrderPanel(prevOrders =>
          prevOrders.map(order => 
            order.idWholeOrderPerson === orderId
              ? { ...order, orderStatus: newStatus }
              : order
          )
        );
      } else {
        console.error("Błąd podczas aktualizacji statusu zamówienia");
      }
    } catch (error) {
      console.error("Błąd: ", error);
    }
  };
const statusDisplayNames = {
  W_TRAKCIE: "W trakcie",
  GOTOWE_DO_ODBIORU: "Gotowe do odbioru",
  ODEBRANE: "Odebrane",
  OCZEKIWANIE_NA_DOSTAWE: "Oczekiwanie na dostawę",
  // Dodaj inne statusy według potrzeb
};

const getDisplayNameForStatus = (status) => {
  return statusDisplayNames[status] || status;
};
  return (
    <div className="dashboardContainer">
      <div className="dashboardSidebar">
        <h2 className="dashboardSidebarTitle">Panel pracownika</h2>
        <ul className="dashboardSidebarList">
          <li
            className="dashboardSidebarItem"
            onClick={() => setActiveTab("dostepne zamowienia")}
          >
            Zamówienia klientów
          </li>
          <li
            className="dashboardSidebarItem"
            onClick={() => setActiveTab("rezerwacje")}
          >
            Zarządzanie Rezerwacjami
          </li>
          <li
            className="dashboardSidebarItem"
            onClick={() => setActiveTab("wydarzenia")}
          >
            Zarządzanie Wydarzeniami
          </li>
        </ul>
      </div>
      <div className="dashboardContent">

        {activeTab === "rezerwacje" && (
          <section>
            <h3 className="dashboardContentTitle">Zarządzanie Rezerwacjami</h3>
            <table className="reservation-table">
              <thead>
                <tr>
                  <th>Osoba rezerwująca</th>
                  <th>Numer osoby rezerwującej</th>
                  <th>Data rezerwacji</th>
                  <th>Ilość osób</th>
                  <th>Numer stolika</th>
                </tr>
              </thead>
              <tbody>
                {reservationPanel.map((reservation) => (
                  <tr key={reservation.idReservation}>
                    <td>
                      {reservation.person.firstName}{" "}
                      {reservation.person.lastName}
                    </td>
                    <td>{reservation.person.phoneNumber}</td>
                    <td>{reservation.bokkingData}</td>

                    {editReservationId === reservation.idReservation ? (
                      <input
                        type="text"
                        value={customEdtitReservationData.numberOfPeople}
                        onChange={(e) =>
                          setCustomEditReservationData({
                            ...customEdtitReservationData,
                            numberOfPeople: e.target.value,
                          })
                        }
                      />
                    ) : (
                      reservation.numberOfPeople
                    )}

                    <td>{reservation.bookTable.tableNumber}</td>
                    <td>
                      {editReservationId === reservation.idReservation ? (
                        <>
                          <button
                            onClick={() =>
                              handleSaveReservation(reservation.idReservation)
                            }
                          >
                            Zapisz
                          </button>
                          <button onClick={() => setEditReservationId(null)}>
                            Anuluj
                          </button>
                        </>
                      ) : (
                        <>
                          <button
                            onClick={() =>
                              editReservation(reservation.idReservation)
                            }
                          >
                            Edytuj
                          </button>
                          <button
                            onClick={() =>
                              deleteReservation(reservation.idReservation)
                            }
                          >
                            Usuń
                          </button>
                        </>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>

           
            {/* Tabela rezerwacji lub lista */}
          </section>
        )}

        {activeTab === "wydarzenia" && (
          <section>
            <h3 className="dashboardContentTitle">Zarządzanie Wydarzeniami</h3>
            <table className="events-table">
              <thead>
                <tr>
                  <th>Nazwa wydarzenia</th>
                  <th>Opis wydarzenia</th>
                  <th>Data opublikowania</th>
                  <th>Osoba publikująca</th>
                </tr>
              </thead>
              <tbody>
                {eventsPanel.map((events) => (
                  <tr key={events.idEvent}>
                    <td>
                      {editEventId === events.idEvent ? (
                        <input
                          type="text"
                          value={eventFormData.eventName}
                          onChange={(e) =>
                            setEventFormData({
                              ...eventFormData,
                              eventName: e.target.value,
                            })
                          }
                        />
                      ) : (
                        events.eventName
                      )}
                    </td>
                    <td>
                      {editEventId === events.idEvent ? (
                        <input
                          type="text"
                          value={eventFormData.eventDescription}
                          onChange={(e) =>
                            setEventFormData({
                              ...eventFormData,
                              eventDescription: e.target.value,
                            })
                          }
                        />
                      ) : (
                        events.eventDescription
                      )}
                    </td>
                    <td>{events.eventsDate}</td>
                    <td>
                      {events.person.firstName} {events.person.lastName}
                    </td>
                    <td>
                      {editEventId === events.idEvent ? (
                        <>
                          <button onClick={() => handleSave(events.idEvent)}>
                            Zapisz
                          </button>
                          <button onClick={() => setEditEventsId(null)}>
                            Anuluj
                          </button>
                        </>
                      ) : (
                        <>
                          <button onClick={() => editEvent(events.idEvent)}>
                            Edytuj
                          </button>
                          <button onClick={() => deleteEvent(events.idEvent)}>
                            Usuń
                          </button>
                        </>
                      )}
                    </td>
                  </tr>
                ))}

                
              </tbody>
            </table>
            <section>
              <h3 className="dashboardContentTitle">Dodaj Nowe Wydarzenie</h3>
              <form onSubmit={handleCreateEvent}>
                <input
                  type="text"
                  value={eventFormData.eventName}
                  onChange={(e) =>
                    setEventFormData({
                      ...eventFormData,
                      eventName: e.target.value,
                    })
                  }
                  placeholder="Nazwa wydarzenia"
                />
                <input
                  type="text"
                  value={eventFormData.eventDescription}
                  onChange={(e) =>
                    setEventFormData({
                      ...eventFormData,
                      eventDescription: e.target.value,
                    })
                  }
                  placeholder="Opis wydarzenia"
                />
                <button className="dashboardButton" type="submit">
                  Dodaj Wydarzenie
                </button>
              </form>
            </section>
          </section>
        )}

        {activeTab === "dostepne zamowienia" && (
          <section className="orders-container">
             <ul>
              <li onClick={() => setActiveTab("dostepne zamowienia")}>
                Dostępne zamówienia
              </li>
              <li onClick={() => setActiveTab("oczekujace zamowienia")}>
                Oczękujące zamówienia
              </li>
            </ul>
            {orderPanel.map((order) => {
              if (order.orderStatus !== "OCZEKIWANIE_NA_DOSTAWE") {
                const currentStatus = getCurrentStatusForOrder(order);
                return (
                  <div key={order.idWholeOrderPerson} className="order-item">
                    <div className="order-date">
                      Data zamówienia: {order.dateOrder}
                    </div>
                    <div className="order-person">
                      Złożone przez: {order.person.firstName}{" "}
                      {order.person.lastName}
                    </div>
                    <div className="order-products">Zamówione produkty:</div>
                    <ul className="products-list">
                      {order.order.map((item) => (
                        <li key={item.idOrderProduct} className="product-item">
                          {item.product.productName} (Ilość: {item.quantity})
                        </li>
                      ))}
                    </ul>
                  
                    <div>

                    Aktualny status: {getDisplayNameForStatus(order.orderStatus)}
                    </div>
                  
                    <div>
      {orderStatuses.length > 0 && (
        <>
        <select value={getCurrentStatusForOrder(order)} onChange={(e) => handleChangeStatus(order.idWholeOrderPerson, e.target.value)}>
          {orderStatuses.map((status, index) => (
            <option key={index} value={status}>
              {status}
            </option>
          ))}
        </select>
          <button onClick={() => updateOrderStatus(order.idWholeOrderPerson, currentStatus)}>
          Zaktualizuj status
        </button>
          </>
      )}
    </div>
 
                  </div>
                );
              } else {
                return null;
              }
            })}
          </section>
        )}

         {activeTab === "oczekujace zamowienia" && (
          <section className="orders-container">
          <ul>
           <li onClick={() => setActiveTab("dostepne zamowienia")}>
             Dostępne zamówienia
           </li>
           <li onClick={() => setActiveTab("oczekujace zamowienia")}>
             Oczękujące zamówienia
           </li>
         </ul>
         {orderPanel.map((order) => {
           if (order.orderStatus === "OCZEKIWANIE_NA_DOSTAWE") {
             const currentStatus = getCurrentStatusForOrder(order);
             return (
               <div key={order.idWholeOrderPerson} className="order-item">
                 <div className="order-date">
                   Data zamówienia: {order.dateOrder}
                 </div>
                 <div className="order-person">
                   Złożone przez: {order.person.firstName}{" "}
                   {order.person.lastName}
                 </div>
                 <div className="order-products">Zamówione produkty:</div>
                 <ul className="products-list">
                   {order.order.map((item) => (
                     <li key={item.idOrderProduct} className="product-item">
                       {item.product.productName} (Ilość: {item.quantity})
                     </li>
                   ))}
                 </ul>
               
                 <div>

                 Aktualny status: {getDisplayNameForStatus(order.orderStatus)}
                 </div>
               
                 <div>
   {orderStatuses.length > 0 && (
     <>
     <select value={getCurrentStatusForOrder(order)} onChange={(e) => handleChangeStatus(order.idWholeOrderPerson, e.target.value)}>
       {orderStatuses.map((status, index) => (
         <option key={index} value={status}>
           {status}
         </option>
       ))}
     </select>
       <button onClick={() => updateOrderStatus(order.idWholeOrderPerson, currentStatus)}>
       Zaktualizuj status
     </button>
       </>
   )}
 </div>

               </div>
             );
           } else {
             return null;
           }
         })}
       </section>
     )}
      </div>
    </div>
  );
};

export default EmployeePanel;
