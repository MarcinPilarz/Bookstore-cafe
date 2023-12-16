import React, { createContext, useContext, useState } from "react";

const BusketProducts = createContext();

export const CartProvider = ({ children }) => {
  const [busket, setBusket] = useState(() => {
    const savedBusket = localStorage.getItem("busket");
    const initialBusket = savedBusket ? JSON.parse(savedBusket) : [];
    console.log("Initial busket from localStorage:", initialBusket);
    return initialBusket;
  });
  const addToBusket = (product) => {
    setBusket((prevCart) => {
      console.log("PrevCart", prevCart);
      const existingProduct = prevCart.find((item) => {
        console.log("PrevCart w find", prevCart);
        const isMatch = item.idProduct === product.idProduct;
        console.log(
          `Produkt ${item.idProduct} porównanie z ${product.idProduct}:`,
          isMatch
        );
        return isMatch;
      });

      if (existingProduct) {
        // Aktualizacja ilości istniejącego produktu
        const updatedCart = prevCart.map((item) =>
          item.id === product.idProduct
            ? { ...item, quantity: item.quantity + 1 }
            : item
        );
        console.log(
          "Koszyk po aktualizacji istniejącego produktu:",
          updatedCart
        );
        localStorage.setItem("busket", JSON.stringify(updatedCart));
        console.log("Updated busket:", updatedCart);
        return updatedCart;
      } else {
        // Dodanie nowego produktu
        const updatedCart = [...prevCart, { ...product, quantity: 1 }];
        console.log("Koszyk po dodaniu nowego produktu:", updatedCart);
        localStorage.setItem("busket", JSON.stringify(updatedCart));
        console.log("Updated busket:", updatedCart);
        return updatedCart;
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
