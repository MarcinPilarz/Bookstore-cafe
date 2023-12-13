import React, { createContext, useContext, useState } from "react";

const BusketProducts = createContext();

export const CartProvider = ({ children }) => {
  const [busket, setBusket] = useState([]);

  const addToBusket = (product) => {
    setBusket((prevCart) => {
      const existingProductIndex = prevCart.findIndex(
        (item) => item.productId === product.idProduct
      );

      if (existingProductIndex !== -1) {
        // Produkt jest już w koszyku, zwiększ ilość
        return prevCart.map((item, index) =>
          index === existingProductIndex
            ? { ...item, quantity: item.quantity + 1 }
            : item
        );
      } else {
        // Produktu nie ma jeszcze w koszyku, dodaj go
        return [...prevCart, { ...product, quantity: 1 }];
      }
    });
  };
  const removeFromBusket = (productId) => {
    setBusket((prevCart) =>
      prevCart.filter((item) => item.productId !== productId)
    );
  };

  return (
    <BusketProducts.Provider value={{ busket, addToBusket, removeFromBusket }}>
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
