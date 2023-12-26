import React, { useState, useEffect } from 'react';
import axios from 'axios';
import "./UserPanel.css";
import Footer from '../Footer/Footer';
import Navbar from '../Navbar/Navbar';
import { useAuth } from '../Login/LoginInfoContext';

const UserPanel = () => {
  const [activeTab, setActiveTab] = useState('personalData');
  const [clientData, setClientData] =useState([]);
  const [clientReservationData, setClientReservationData] = useState([]);
  const [clientHistoryOrdersData, setClientHistoryOrdersData]= useState([]);
  const [clientReviewsData, setClientReviewsData]= useState([]);
  const [clientOrdersData, setClientOrdersData]=useState([]);
    const {authData}= useAuth();
    const idPerson=authData?.idPerson;

   useEffect(() => {
    if (idPerson) {
        // Pierwsze żądanie HTTP
        axios.get(`http://localhost:8080/personDetails?id=${idPerson}`)
            .then(response => {
                const clientInfo = response.data;
                console.log("Pobrane dane produktow z API:", clientInfo);
                setClientData(clientInfo);
            })
            .catch(error => {
                console.error("Błąd pobierania danych wydarzeń", error);
            });

        // Drugie żądanie HTTP
        axios.get(`http://localhost:8080/reservations/person?personId=${idPerson}`)
            .then(response => {
                const clientReservationInfo = response.data;
                console.log("Pobrane dane rezerwacji z API:", clientReservationInfo);
                setClientReservationData(clientReservationInfo);
            })
            .catch(error => {
                console.error("Błąd pobierania danych rezerwacji", error);
            });

            axios
            .get(`http://localhost:8080/history?personId=${idPerson}`)
            .then((response) => {
                const clientHistoryOrdersInfo = response.data;
                console.log("Pobrane dane historii zamówień z API:", clientHistoryOrdersInfo);
                setClientHistoryOrdersData(clientHistoryOrdersInfo);
            })
            .catch((error) => {
                console.error("Błąd pobierania danych historii zamówień", error);
            });
 axios
          .get(`http://localhost:8080/history?personId=${idPerson}`)
          .then((response) => {
              const clientHistoryOrdersInfo = response.data;
              console.log("Pobrane dane historii zamówień z API:", clientHistoryOrdersInfo);
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
              console.log("Pobrane dane historii zamówień z API:", clientHistoryOrdersInfo);
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
        case 'personalData':
          
                return (
                    <div>
                        <div>Dane osobowe</div>
                        {clientData && (
                        <div>
                            <p>Imię: {clientData.firstName}</p>
                            <p>Nazwisko: {clientData.lastName}</p>
                            {/* Sprawdź, czy loginPerson i email istnieją zanim spróbujesz wyrenderować email */}
                            {clientData.loginPerson && clientData.loginPerson.email && (
                                <p>E-mail: {clientData.loginPerson.email}</p>
                            )}
                        </div>
                    )}
                    </div>
                );
           
              
          case 'myReservations':
            return (
                Array.isArray(clientReservationData) && clientReservationData.length > 0 ?
                    clientReservationData.map((reservation) => (
                        <div key={reservation.idReservation}> {/* Użyj unikalnego klucza dla każdej rezerwacji */}
                            <p>Data rezerwacji: {reservation.bokkingData}</p>
                            <p>Ilość zarezerwowanych miejsc: {reservation.numberOfPeople}</p>

                            {reservation.bookTable && (
                                
                                <p>Numer stolika: {reservation.bookTable.idBookTable}</p>
                              

                            )}
                        </div>
                    )) :
                    <div>Brak rezerwacji.</div> // Wyświetlane, gdy nie ma rezerwacji
            );
          case 'orderHistory':
              return  (
                Array.isArray(clientHistoryOrdersData) && clientHistoryOrdersData.length > 0 ?
                clientHistoryOrdersData.map((historyOrders) => (
                    <div key={historyOrders.idOrderHistory}> {/* Użyj unikalnego klucza dla każdej rezerwacji */}
                    {historyOrders.product&& (
                        
                        <p>Nazwa produktu: {historyOrders.product.productName}</p>
                      
    
                    )}
                            <p>Ilość: {historyOrders.quantity}</p>
                            <p>Cena: {historyOrders.totalPrice}</p>
                            <p>Data zamówienia: {historyOrders.dateOrder}</p>

                        </div>
                    )) :
                    <div>Brak rezerwacji.</div> // Wyświetlane, gdy nie ma rezerwacji
            );
          case 'myComments':
            return (
                Array.isArray(clientReviewsData) && clientReviewsData.length > 0 ?
                clientReviewsData.map((reviews) => (
                        <div key={reviews.idReview}> {/* Użyj unikalnego klucza dla każdej rezerwacji */}
                            <p>Treść komenatza: {reviews.reviewContent}</p>
                            <p>Ocena: {reviews.rating}</p>
                            <p>Data opublikowania: {reviews.reviewData}</p>

                           
                        </div>
                    )) :
                    <div>Brak komentarzy.</div> // Wyświetlane, gdy nie ma rezerwacji
            );
          case 'myOrders':
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
                Array.isArray(clientOrdersData) && clientOrdersData.length > 0 ?
                    clientOrdersData.map((orders, index) => {
                        // Sprawdź, czy istnieje następne zamówienie i czy różnica czasu jest mniejsza niż 20 sekund
                        const isCloseToNextOrder = index < clientOrdersData.length - 1 && 
                                                   Math.abs(new Date(orders.dateOrder).getTime() - new Date(clientOrdersData[index + 1].dateOrder).getTime()) < 20000;
            
                        return (
                            <div key={orders.idOrder}>
                                {/* Wyświetl dateOrder tylko jeśli nie jest blisko następnego zamówienia */}
                                {!isCloseToNextOrder && <p>Data zamówienia: {orders.dateOrder}</p>}
                                <p>Ilość: {orders.quantity}</p>
                                <p>Cena: {orders.totalPrice}</p>
                                {orders.products.map((product) => (
                                    <p key={product.idProduct}>Nazwa produktu: {product.productName}</p>
                                ))}
                            </div>
                        );
                    }) :
                    <div>Brak rezerwacji.</div> // Wyświetlane, gdy nie ma rezerwacji
            );
          default:
              return <div>Dane osobowe</div>;
      }
  };

  return (
    <>
    <Navbar/>
      <div className="dashboard">
          <div className="tabs">
              <button onClick={() => setActiveTab('personalData')}>Dane osobowe</button>
              <button onClick={() => setActiveTab('myOrders')}>Moje zamówienia</button>
              <button onClick={() => setActiveTab('orderHistory')}>Historia zamówień</button>
              <button onClick={() => setActiveTab('myReservations')}>Moje rezerwacje</button>
              <button onClick={() => setActiveTab('myComments')}>Moje komentarze</button>
          </div>
          <div className="content">
              {renderContent()}
          </div>
      </div>
      <Footer/>
    </>
  );
};

export default UserPanel;
