import React, { createContext, useContext, useState, useEffect } from "react";
import { useAuth } from "../Login/LoginInfoContext";
const BusketProducts = createContext();

export const CartProvider = ({ children }) => {
  const { authData } = useAuth(); // Pobranie danych autentykacji
  const idPerson = authData.idPerson;
  const busketKey = `busket_${idPerson}`;
  // const [busket, setBusket] = useState(() => {
  //   const savedBusket = localStorage.getItem("busket");
  //   const initialBusket = savedBusket ? JSON.parse(savedBusket) : [];
  //   console.log("Initial busket from localStorage:", initialBusket);
  //   return initialBusket;
  // const [busket, setBusket] = useState(() => {
  //   const savedBusket = localStorage.getItem(busketKey);
  //   return savedBusket ? JSON.parse(savedBusket) : [];
  // });

  const [busket, setBusket] = useState([]);

  useEffect(() => {
    // Klucz dla koszyka bazujący na idPerson
    const busketKey = `busket_${authData.idPerson}`;

    // Funkcja do ładowania koszyka z localStorage
    const loadBusket = () => {
      const savedBusket = localStorage.getItem(busketKey);
      return savedBusket ? JSON.parse(savedBusket) : [];
    };

    // Ustawienie stanu koszyka po zmianie użytkownika
    setBusket(loadBusket());
  }, [authData.idPerson, authData.token]); // Uruchom ponownie, gdy zmienia się idPerson lub token

  const addToBusket = (product) => {
    setBusket((prevCart) => {
      const existingProductIndex = prevCart.findIndex(
        (item) => item.idProduct === product.idProduct
      );

      if (existingProductIndex !== -1) {
        // Sprawdź, czy dodanie kolejnej sztuki nie przekroczy limitu 10
        if (prevCart[existingProductIndex].quantity < 10) {
          const updatedCart = [...prevCart];
          updatedCart[existingProductIndex] = {
            ...updatedCart[existingProductIndex],
            quantity: updatedCart[existingProductIndex].quantity + 1,
          };

          // localStorage.setItem("busket", JSON.stringify(updatedCart));
          localStorage.setItem(busketKey, JSON.stringify(updatedCart));
          return updatedCart;
        } else {
          // Jeśli limit zostałby przekroczony, nie dodawaj więcej i zwróć obecny koszyk
          alert("Możesz dodać maksymalnie 10 sztuk tego produktu.");
          return prevCart;
        }
      } else {
        // Dodaj nowy produkt do koszyka z ilością 1
        const updatedCart = [...prevCart, { ...product, quantity: 1 }];

        // localStorage.setItem("busket", JSON.stringify(updatedCart));
        localStorage.setItem(busketKey, JSON.stringify(updatedCart));
        return updatedCart;
      }
    });
  };
  const removeFromBusket = (productId) => {
    setBusket((prevCart) => {
      const updatedCart = prevCart.filter(
        (item) => item.idProduct !== productId
      );

      // Zapisz zaktualizowany koszyk do localStorage
      // localStorage.setItem("busket", JSON.stringify(updatedCart));
      localStorage.setItem(busketKey, JSON.stringify(updatedCart));
      return updatedCart;
    });
  };

  const clearBusket = () => {
    setBusket([]); // Resetuje stan busket
    //localStorage.removeItem("busket"); // Usuwa dane z localStorage
    localStorage.removeItem(busketKey);
  };

  const updateProductQuantity = (idProduct, newQuantity) => {
    setBusket((prevCart) => {
      const updatedCart = prevCart.map((item) => {
        if (item.idProduct === idProduct) {
          return { ...item, quantity: newQuantity };
        }
        return item;
      });

      // Zapisz zaktualizowany koszyk do localStorage
      // localStorage.setItem("busket", JSON.stringify(updatedCart));
      localStorage.setItem(busketKey, JSON.stringify(updatedCart));
      return updatedCart;
    });
  };
  return (
    <BusketProducts.Provider
      value={{
        busket,
        setBusket,
        addToBusket,
        removeFromBusket,
        clearBusket,
        updateProductQuantity,
      }}
    >
      {children}
    </BusketProducts.Provider>
  );
};
export const useCart = () => {
  const context = useContext(BusketProducts);
  if (!context) {
    throw new Error(" usecART MUST BE USED WITHIN A ProductBusket");
  }
  return context;
};
