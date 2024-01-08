import React, { useState, useEffect } from "react";
import "./EmployeePanel.css";
import "./OwnerPanel.css"
import { useAuth } from "../Login/LoginInfoContext";
import axios from "axios";
import { useParams, Link } from "react-router-dom";
const OwnerPanel = () => {
  const [activeTab, setActiveTab] = useState("zamowienia klientow");
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

  const[productPanel, setProductPanel]= useState([]);
  const [activeProductType, setActiveProductType] = useState('COFFEE'); // Domyślny typ produktu
  const [showAddModal, setShowAddModal] = useState(false); 
  const [newProductData, setNewProductData] = useState({
    productName: '',
    productPrice: '',
    productDescription: '',
    // Dodaj więcej pól zależnie od typu produktu
    ...(activeProductType === 'BOOK' ? { numberBookStock: 0, isAvailable: false } : {})
  });
  const [editingProduct, setEditingProduct] = useState(null);
  const [editingProductData, setEditingProductData] = useState({});
  const [showImageUploadModal, setShowImageUploadModal] = useState(false);
  const [selectedProductId, setSelectedProductId] = useState(null);
  const [selectedFile, setSelectedFile] = useState(null);

  const [employeePanel, setEmployeePanel] = useState([]);
  const [showAddEditModal, setShowAddEditModal] = useState(false);
  const [isEditMode, setIsEditMode] = useState(false);
  const [selectedEmployee, setSelectedEmployee] = useState(null);
  const [newEmployeeData, setNewEmployeeData] = useState({
    firstName: '',
    lastName: '',
    phoneNumber: '',
    email: '',
    roleType: 'Pracownik',
    password: ''
  });
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


//Produkty

useEffect(() => {
  axios.get(`http://localhost:8080/products?productType=${activeProductType}`)
    .then(response => {
      setProductPanel(response.data);
      console.log("PRODUKTY Z PANELU", response.data)
    })
    .catch(error => {
      console.error("Błąd pobierania danych produktów", error);
    });
}, [activeProductType]);

const renderEditableCell = (value, name, id, placeholder, type = "text") => {
  if (type === "number") {
    return (
      <input
        type="number"
        value={value}
        name={name}
        min="0"
        placeholder={placeholder}
        onChange={(e) => handleInputChange(e, id)}
      />
    );
  } else {
    return (
      <input
        type="text"
        value={value}
        name={name}
        placeholder={placeholder}
        onChange={(e) => handleInputChange(e, id)}
      />
    );
  }
};
const renderTableHeaders = () => {
  const commonHeaders = [<th key="name">Nazwa</th>, <th key="price">Cena</th>, <th key="description">Opis</th>];

  switch (activeProductType) {
    case 'COFFEE':
      return [...commonHeaders, <th key="intensity">Intensywność</th>];
    case 'BOOK':
      return [...commonHeaders, <th key="author">Autor</th>, <th key="genere">Gatunek</th>,<th key="publishingHouse">Wydawnictwo</th>,<th key="language">Język</th>, <th key="publicationDate">Data publikacji</th>, <th key="bookCover">Rodzaj okładki</th>,<th key="numberPage">Ilość stron</th>,<th key="numberBookStock">Ilość ksiązek w magazynie</th> ];
    case 'FOOD':
      return [...commonHeaders, <th key="amountOfCalories">Kalorie</th>, <th key="foodWeight">Waga</th>];
    default:
      return commonHeaders;
  }
};

// const renderTableRows = () => {
//   return productPanel.map((product, index) => (
//     <tr key={index}>
//       <td>{product.productName}</td>
//       <td>{product.productPrice}</td>
//       <td>{product.productDescription}</td>
//       {activeProductType === 'COFFEE' && <td>{product.coffeeIntensity}</td>}
//       {activeProductType === 'BOOK' && (
//         <>
//           <td>{product.author}</td>
//           <td>{product.genere}</td>
//           <td>{product.publishingHouse}</td>
//           <td>{product.language}</td>
//           <td>{product.publicationDate}</td>
//           <td>{product.bookCover}</td>
//           <td>{product.numberPage}</td>
          
//           <td>{product.numberBookStock}</td>

//         </>
//       )}
//       {activeProductType === 'FOOD' && (
//       <>
//       <td>{product.amountOfCalories}</td> 
//       <td>{product.foodWeight}</td>
//       </>
//       )}
//     </tr>
//   ));
// };


const handleUploadImageClick = (productId) => {
  // Ustaw stan dla ID produktu, do którego dodawane jest zdjęcie
  setSelectedProductId(productId);
  setShowImageUploadModal(true);
};
const handleFileChange = (e) => {
  setSelectedFile(e.target.files[0]);
};


const handleImageUploadSubmit = async (e) => {
  e.preventDefault();
  if (!selectedFile) {
    console.error("Nie wybrano pliku");
    return;
  }

  const formData = new FormData();
  formData.append('file', selectedFile);

  try {
    await axios.post(`http://localhost:8080/uploadImage?idProduct=${selectedProductId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    console.log("Zdjęcie przesłane pomyślnie");
    setShowImageUploadModal(false);
    setSelectedFile(null);
    setSelectedProductId(null);
  } catch (error) {
    console.error("Błąd przesyłania zdjęcia", error);
  }
};
const renderTableRows = () => {
  return productPanel.map((product) => (
    <tr key={product.idProduct}>
      {editingProduct === product.idProduct ? (
        <>
          {renderEditableCell(editingProductData.productName, 'productName', product.idProduct, "Nazwa produktu")}
          {renderEditableCell(editingProductData.productPrice, 'productPrice', product.idProduct, "Cena produktu")}
          {renderEditableCell(editingProductData.productDescription, 'productDescription', product.idProduct, "Opis produktu")}

          {activeProductType === 'COFFEE' && (
            <>
              {renderEditableCell(editingProductData.coffeeIntensity, 'coffeeIntensity', product.idProduct, "Intensywność kawy")}
              {/* Dodaj tutaj inne pola specyficzne dla kawy do edycji */}
            </>
          )}

          {activeProductType === 'BOOK' && (
            <>
              {renderEditableCell(editingProductData.author, 'author', product.idProduct, "Autor")}
              {renderEditableCell(editingProductData.genere, 'genere', product.idProduct, "Rodzaj")}
              {renderEditableCell(editingProductData.publishingHouse, 'publishingHouse', product.idProduct, "Wydawnictwo")}
              {renderEditableCell(editingProductData.language, 'language', product.idProduct, "Język")}
              {renderEditableCell(editingProductData.publicationDate, 'publicationDate', product.idProduct, "Rok produkcji")}
              {renderEditableCell(editingProductData.bookCover, 'bookCover', product.idProduct, "Rodzaj okładki")}
              {renderEditableCell(editingProductData.numberPage, 'numberPage', product.idProduct, "Ilość stron", "number")}
              {renderEditableCell(editingProductData.numberBookStock, 'numberBookStock', product.idProduct, "Ilość w magazynie")}
              {/* Dodaj tutaj inne pola specyficzne dla książek do edycji */}
            </>
          )}

          {activeProductType === 'FOOD' && (
            <>
              {renderEditableCell(editingProductData.amountOfCalories, 'amountOfCalories', product.idProduct, "Kalorie")}
              {renderEditableCell(editingProductData.foodWeight, 'foodWeight', product.idProduct, "Waga")}
              {/* Dodaj tutaj inne pola specyficzne dla jedzenia do edycji */}
            </>
          )}
        </>
      ) : (
        <>
          <td>{product.productName}</td>
          <td>{product.productPrice}</td>
          <td>{product.productDescription}</td>
          {activeProductType === 'COFFEE' && <td>{product.coffeeIntensity}</td>}
          {activeProductType === 'BOOK' && (
            <>
              <td>{product.author}</td>
              <td>{product.genere}</td>
              <td>{product.publishingHouse}</td>
           <td>{product.language}</td>
           <td>{product.publicationDate}</td>
           <td>{product.bookCover}</td>
           <td>{product.numberPage}</td>
             <td>{product.numberBookStock}</td>
              {/* Wyświetl inne pola książki */}
            </>
          )}
          {activeProductType === 'FOOD' && (
            <>
              <td>{product.amountOfCalories}</td>
              <td>{product.foodWeight}</td>
              {/* Wyświetl inne pola jedzenia */}
            </>
          )}
        </>
      )}
      <td>
        {editingProduct === product.idProduct ? (
          <button onClick={() => handleSaveClick(product.idProduct)}>Zapisz</button>
        ) : (
          <button onClick={() => handleEditClick(product)}>Edytuj</button>
        )}
      </td>
      <td>
        <button onClick={() => handleUploadImageClick(product.idProduct)}>Dodaj Zdjęcie</button>
      </td>
      <td> <button onClick={() => handleDeleteClick(product.idProduct)}>Usuń</button></td>
    </tr>
  ));
};

const handleAddProductClick = () => {
  setShowAddModal(true);
};

const handleCloseModal = () => {
  setShowAddModal(false);
};

// const handleInputChange = (e) => {
//   const { name, value } = e.target;
//   const updatedData = { ...newProductData, [name]: value };

//   if (activeProductType === 'BOOK' && name === 'numberBookStock') {
//     // Ustawienie isAvailable na TRUE, gdy numberBookStock > 0
//     updatedData.isAvailable = parseInt(value, 10) > 0;
//   }

//   setNewProductData(updatedData);
// };

const handleInputChange = (e, id) => {
  const { name, value } = e.target;
  let updatedData = { ...editingProductData, [name]: value };
  const newData = { ...newProductData, [name]: value };
  if (activeProductType === 'BOOK' && name === 'numberBookStock') {
    const stockNumber = parseInt(value, 10);
    updatedData = {
      ...updatedData,
      available: stockNumber > 0,
     
    };
  }
  console.log("updatedata", updatedData);
  setNewProductData(newData);
  setEditingProductData(updatedData);
};



const handleEditClick = (product) => {
  setEditingProduct(product.idProduct);
  setEditingProductData(product);
};
const handleSaveClick = async (id) => {
  try {
    // Aktualizacja isAvailable dla Book
    let updatedData = {...editingProductData};
    if (activeProductType === 'BOOK') {
      const stockNumber = parseInt(updatedData.numberBookStock, 10);
      updatedData.available = stockNumber > 0;
    }

    await axios.put(`http://localhost:8080/updateProducts?id=${id}`, updatedData);
    console.log("Produkt zaktualizowany");

    // Aktualizacja listy produktów w stanie
    const updatedProductPanel = productPanel.map((product) => {
      if (product.idProduct === id) {
        return { ...product, ...updatedData };
      }
      return product;
    });
    setProductPanel(updatedProductPanel);

    // Zakończenie edycji
    setEditingProduct(null);
    setEditingProductData({});
    // Opcjonalnie: Odśwież listę produktów
  } catch (error) {
    console.error("Błąd aktualizacji produktu", error);
  }
};
const handleSubmit = async (e) => {
  e.preventDefault();
  try {
    const response = await axios.post(`http://localhost:8080/addProduct`, {
      ...newProductData,
      productType: activeProductType
    });
    console.log("Produkt dodany", response.data);
    setShowAddModal(false);
    // Opcjonalnie: Odśwież listę produktów
  } catch (error) {
    console.error("Błąd dodawania produktu", error);
  }
};

const handleDeleteClick = async (id) => {
  try {
    await axios.delete(`http://localhost:8080/deleteCoffee?id=${id}`);
    console.log("Produkt usunięty");
    // Aktualizacja listy produktów po usunięciu
    const updatedProductPanel = productPanel.filter(product => product.idProduct !== id);
    setProductPanel(updatedProductPanel);
  } catch (error) {
    console.error("Błąd usuwania produktu", error);
  }
};
const renderAdditionalFields = () => {
  switch (activeProductType) {
    case 'COFFEE':
      return (
        <>
          <input
            type="number"
            name="coffeeIntensity"
            value={newProductData.coffeeIntensity || ''}
            onChange={handleInputChange}
            placeholder="Intensywność kawy"
          />
          {/* Dodaj tutaj inne specyficzne pola dla kawy */}
        </>
      );
    case 'BOOK':
      return (
        <>
          <input
            type="text"
            name="author"
            value={newProductData.author || ''}
            onChange={handleInputChange}
            placeholder="Autor"
          />
          <input
            type="text"
            name="genere"
            value={newProductData.genere || ''}
            onChange={handleInputChange}
            placeholder="Gatunek"
          />
          <input
            type="text"
            name="publishingHouse"
            value={newProductData.publishingHouse || ''}
            onChange={handleInputChange}
            placeholder="Wydawnictwo"
          />
          <input
            type="text"
            name="language"
            value={newProductData.language || ''}
            onChange={handleInputChange}
            placeholder="Język"
          />
          <input
            type="text"
            name="publicationDate"
            value={newProductData.publicationDate || ''}
            onChange={handleInputChange}
            placeholder="Rok publikacji"
          />
          <input
            type="text"
            name="bookCover"
            value={newProductData.bookCover || ''}
            onChange={handleInputChange}
            placeholder="Rodzaj okładki"
          />
          <input
            type="number"
            name="numberPage"
            value={newProductData.numberPage || ''}
            onChange={handleInputChange}
            placeholder="Liczba stron"
          />
          <input
            type="number"
            name="numberBookStock"
            value={newProductData.numberBookStock || ''}
            onChange={handleInputChange}
            placeholder="Ilość książek w magazynie"
          />
          {/* Dodaj tutaj inne specyficzne pola dla książek */}
        </>
      );
    case 'FOOD':
      return (
        <>
          <input
            type="number"
            name="amountOfCalories"
            value={newProductData.amountOfCalories || ''}
            onChange={handleInputChange}
            placeholder="Kalorie"
          />
          <input
            type="number"
            name="foodWeight"
            value={newProductData.foodWeight || ''}
            onChange={handleInputChange}
            placeholder="Waga"
          />
          {/* Dodaj tutaj inne specyficzne pola dla jedzenia */}
        </>
      );
    default:
      return null;
  }
};

//PRACOWNICY-------------------------------------------------------------
useEffect(() => {
  fetchEmployees();
}, []);
const fetchEmployees = async () => {
  try {
    const response = await axios.get('http://localhost:8080/employees');
    setEmployeePanel(response.data);
  } catch (error) {
    console.error('Error fetching employees', error);
  }
};

const handleAddEmployeeClick = () => {
  setShowAddEditModal(true);
  setIsEditMode(false);
  setNewEmployeeData({
    firstName: '',
    lastName: '',
    phoneNumber: '',
    email: '',
    roleType: 'Pracownik',
    password: ''
  });
};

const handleEditEmployeeClick = (employee) => {
  setShowAddEditModal(true);
  setIsEditMode(true);
  setSelectedEmployee(employee);
  setNewEmployeeData({
    ...employee,
    password: '' // Ustaw puste hasło
  });
};


const handleAddEmployee = async (employeeData) => {
  
  if (!employeeData.email.includes('@')) {
    alert('Podany e-mail jest niepoprawny. Upewnij się, że zawiera znak @.');
    return; 
  }
  try {
    await axios.post('http://localhost:8080/newPerson', employeeData);
    fetchEmployees(); // Ponowne pobranie listy pracowników po dodaniu nowego
    console.log("Pracownik został pomyślnie zarejestrowany")
  } catch (error) {
    console.error('Error adding employee', error);
  }
};

const handleInputEmployeeChange = (e) => {
  const { name, value } = e.target;
  setNewEmployeeData({ ...newEmployeeData, [name]: value });
};

// Funkcja aktualizująca dane pracownika
const handleUpdateEmployee = async (id, employeeData) => {
  if (!id) {
    console.error('Identyfikator użytkownika jest niezdefiniowany');
    return;
  }

  try {
    await axios.put(`http://localhost:8080/editUser?id=${id}`, employeeData);
    fetchEmployees();
    console.log("Użytkownik został pomyślnie zaktualizowany");
  } catch (error) {
    console.error('Error updating employee', error);
  }
};

const handleCloseModalEmployee = () => {
  setShowAddEditModal(false);
};

// Funkcja usuwająca pracownika
const handleDeleteEmployee = async (id) => {
  try {
    await axios.delete(`http://localhost:8080/deletePerson?id=${id}`);
    fetchEmployees(); // Ponowne pobranie listy pracowników po usunięciu
    console.log("Usunięto Pracownika");
  } catch (error) {
    console.error('Error deleting employee', error);
  }
};

// Obsługa formularza do dodawania/edycji pracownika
const handleSubmitEmployee = (e) => {
  e.preventDefault();
  const employeeData = { ...newEmployeeData };

  if (isEditMode) {
    handleUpdateEmployee(selectedEmployee.idPerson, employeeData);
  } else {
    handleAddEmployee(employeeData);
  }

  setShowAddEditModal(false);
};

const handleGeneratePassword = () => {
  const password = generateRandomPassword();
  setNewEmployeeData({ ...newEmployeeData, password });
};

const generateRandomPassword = () => {
  const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  let password = '';
  for (let i = 0; i < 8; i++) {
    password += characters.charAt(Math.floor(Math.random() * characters.length));
  }
  return password;
};
const renderModalForm = () => {
  return (
    <div className="modal">
      <form onSubmit={handleSubmitEmployee}>
        <input
          type="text"
          name="firstName"
          value={newEmployeeData.firstName}
          onChange={handleInputEmployeeChange}
          placeholder="Imię"
        />
        <input
          type="text"
          name="lastName"
          value={newEmployeeData.lastName}
          onChange={handleInputEmployeeChange}
          placeholder="Nazwisko"
        />
        <input
          type="text"
          name="phoneNumber"
          maxLength={9}
          value={newEmployeeData.phoneNumber}
          onChange={handleInputEmployeeChange}
          placeholder="Numer telefonu"
        />
        <input
          type="email"
          name="email"
          value={newEmployeeData.email}
          onChange={handleInputEmployeeChange}
          placeholder="Email"
        />
        {!isEditMode && (
        <input
          type="text"
          name="password"
          value={newEmployeeData.password}
          onChange={handleInputEmployeeChange}
          placeholder="Hasło"
        />
      )}

{!isEditMode && (
        <button type="button" onClick={handleGeneratePassword}>Generuj Hasło</button>

)}


        <button type="submit">{isEditMode ? 'Zapisz zmiany' : 'Dodaj Pracownika'}</button>
      </form>
      <button onClick={handleCloseModalEmployee}>Zamknij</button>
    </div>
  );
};

  return (
    <div className="dashboardContainer">
      <div className="dashboardSidebar">
        <h2 className="dashboardSidebarTitle">Panel administratora</h2>
        <ul className="dashboardSidebarList">
          <li
            className="dashboardSidebarItem"
            onClick={() => setActiveTab("dostepne zamowienia")}
          >
            Zamówienia klientów
          </li>
          <li
            className="dashboardSidebarItem"
            onClick={() => setActiveTab("pracownicy")}
          >
            Zarządzanie Pracownikami
          </li>
          <li
            className="dashboardSidebarItem"
            onClick={() => setActiveTab("rezerwacje")}
          >
            Zarządzanie Rezerwacjami
          </li>
          <li
            className="dashboardSidebarItem"
            onClick={() => setActiveTab("produkty")}
          >
            Zarządzanie Produktami
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


{activeTab === "produkty" && (
    <section className="product-container">
    <h3 className="dashboardContentTitle">Zarządzanie Produktami</h3>
    <div className="product-tabs">
      <button onClick={() => setActiveProductType('COFFEE')}>Kawa</button>
      <button onClick={() => setActiveProductType('BOOK')}>Książki</button>
      <button onClick={() => setActiveProductType('FOOD')}>Jedzenie</button>
    </div>
    <div className="product-list">
      <div className="table-container">

      <table >
        <thead>
          <tr>{renderTableHeaders()}</tr>
        </thead>
        <tbody>{renderTableRows()}</tbody>
      </table>
      </div>
    </div>
    <button onClick={handleAddProductClick}>Dodaj {activeProductType}</button>

    
    {showAddModal && (
        <div className="add-product-modal">
          <form onSubmit={handleSubmit}>
            {/* Wspólne pola dla wszystkich produktów */}
            <input
              type="text"
              name="productName"
              value={newProductData.productName}
              onChange={handleInputChange}
              placeholder="Nazwa produktu"
            />
            <input
              type="number"
              name="productPrice"
              value={newProductData.productPrice}
              onChange={handleInputChange}
              placeholder="Cena produktu"
            />
            <textarea
              name="productDescription"
              value={newProductData.productDescription}
              onChange={handleInputChange}
              placeholder="Opis produktu"
            />

            {/* Specyficzne pola dla poszczególnych typów produktów */}
            {renderAdditionalFields()}

            <button type="submit">Dodaj produkt</button>
          </form>
          <button onClick={handleCloseModal}>Zamknij</button>
        </div>
      )}
      {showImageUploadModal && (
  <div className="modal">
    <form onSubmit={handleImageUploadSubmit}>
      <input type="file" onChange={handleFileChange} />
      <button type="submit">Prześlij Zdjęcie</button>
    </form>
    <button onClick={() => setShowImageUploadModal(false)}>Zamknij</button>
    
  </div>
)}
    </section>
)}
{activeTab === "pracownicy" && (
    <section className="product-container">
    <h3 className="dashboardContentTitle">Zarządzanie Pracownikami</h3>
    
    <div>
    <button onClick={handleAddEmployeeClick}>Dodaj Pracownika</button>

    {/* Tabela pracowników */}
    <table>
      <thead>
        <tr>
          <th>Imię</th>
          <th>Nazwisko</th>
          <th>Numer Telefonu</th>
          <th>Email</th>
          <th>Akcje</th>
        </tr>
      </thead>
      <tbody>
        {employeePanel.map((employee) => (
          <tr key={employee.id}>
            <td>{employee.firstName}</td>
            <td>{employee.lastName}</td>
            <td>{employee.phoneNumber}</td>
            <td>{employee.email}</td>
            <td>
              <button onClick={() => handleEditEmployeeClick(employee)}>Edytuj</button>
              <button onClick={() => handleDeleteEmployee(employee.idPerson)}>Usuń</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
    {showAddEditModal && renderModalForm()}
    {/* Tutaj umieść formularz modalny dla dodawania/edycji pracownika
    {showAddEditModal && (
      // ...formularz modalny...
    )} */}
  </div>
    
    </section>
)}
      </div>
    </div>
  );
};

export default OwnerPanel;
