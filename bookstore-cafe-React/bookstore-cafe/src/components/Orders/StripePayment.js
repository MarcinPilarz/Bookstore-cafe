import React from "react";
import { useStripe, useElements, CardElement } from "@stripe/react-stripe-js";
import axios from "axios";
import { useCart } from "../ProductSection/BusketProducts";
import { useAuth } from "../Login/LoginInfoContext";

const StripePayment = () => {
  const stripe = useStripe();
  const elements = useElements();

  const CARD_ELEMENT_OPTIONS = {
    style: {
      base: {
        color: "#32325d",
        fontFamily: "Arial, sans-serif",
        fontSmoothing: "antialiased",
        fontSize: "16px",
        "::placeholder": {
          color: "#aab7c4",
        },
      },
      invalid: {
        color: "#fa755a",
        iconColor: "#fa755a",
      },
    },
  };

  const { busket, clearBusket, updateProductQuantity, removeFromBusket } =
    useCart();
  const { authData } = useAuth();
  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!stripe || !elements) {
      console.log("Stripe.js hasn't loaded yet.");
      return;
    }

    // for (const item of busket) {
    const cardElement = elements.getElement(CardElement);
    const { error, token } = await stripe.createToken(cardElement);

    if (error) {
      console.log("Error:", error);
    } else {
      await handleOrders(token.id);
    }
    //  }
  };

  const handleOrders = async (stripeToken) => {
    const idsProducts = busket.map((item) => item.idProduct);
    const quantities = busket.map((item) => item.quantity);
    // for (const product of convertProdcutToProductId) {
    //   await OrderProducts(product, stripeToken);
    // }

    try {
      const response = await axios.post(
        `http://localhost:8080/addOrder?idPerson=${authData.idPerson}&idProduct=${idsProducts}&quantity=${quantities}&token=${stripeToken}`
      );

      console.log("Odpowiedź serwera:", response.data);
      clearBusket(); // Opcjonalnie czyści koszyk po zakończeniu zamówienia
    } catch (error) {
      console.error("Błąd podczas tworzenia zamówienia:", error);
    }
  };

  const processPaymentForProduct = async (product, stripeToken) => {
    const url = `http://localhost:8080/addOrder?idPerson=${authData.idPerson}&idProduct=${product.idProduct}&quantity=${product.quantity}&token=${stripeToken}`;

    try {
      const response = await axios.post(url);
      console.log("Odpowiedź serwera:", response.data);
    } catch (error) {
      console.error(
        "Błąd podczas przetwarzania płatności dla produktu:",
        error
      );
    }
  };
  return (
    <div className="stripe-payment-form">
      <form onSubmit={handleSubmit}>
        <div className="card-element-container">
          <CardElement options={CARD_ELEMENT_OPTIONS} />
        </div>
        <button type="submit" disabled={!stripe}>
          Zapłać
        </button>
      </form>
    </div>
  );
};

export default StripePayment;
