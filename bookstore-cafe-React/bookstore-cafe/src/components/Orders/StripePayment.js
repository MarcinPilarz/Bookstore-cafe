import React, { useState } from "react";
import {
  useStripe,
  useElements,
  CardNumberElement,
  CardExpiryElement,
  CardCvcElement,
} from "@stripe/react-stripe-js";
import { useNavigate } from "react-router-dom";

import axios from "axios";
import { useCart } from "../ProductSection/BusketProducts";
import { useAuth } from "../Login/LoginInfoContext";
import "./StripePaymentStyle.css";

const StripePayment = () => {
  const stripe = useStripe();
  const elements = useElements();
  const navigate = useNavigate();
  const [paymentMessage, setPaymentMessage] = useState("");
  const CARD_ELEMENT_OPTIONS = {
    style: {
      base: {
        color: "#32325d",
        fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
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

  const { busket, clearBusket } = useCart();
  const { authData } = useAuth();

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!stripe || !elements) {
      console.log("Stripe.js hasn't loaded yet.");
      return;
    }

    const cardNumberElement = elements.getElement(CardNumberElement);
    const { error, token } = await stripe.createToken(cardNumberElement);

    if (error) {
      console.log("Error:", error);
    } else {
      await handleOrders(token.id);
    }
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
      setPaymentMessage(
        "Płatność udana. Zostaniesz przekierowany na stronę główną za 5 sekund."
      );

      setTimeout(() => {
        navigate("/");
      }, 5000); // Opóźnienie 5 sekund przed przekierowaniem
    } catch (error) {
      console.error("Błąd podczas tworzenia zamówienia:", error);
      setPaymentMessage("Wystąpił błąd przy płatności. Spróbuj ponownie.");
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
          <label>
            Numer karty
            <CardNumberElement options={CARD_ELEMENT_OPTIONS} />
          </label>
        </div>
        <div className="card-element-container">
          <label>
            Data ważności
            <CardExpiryElement options={CARD_ELEMENT_OPTIONS} />
          </label>
        </div>
        <div className="card-element-container">
          <label>
            CVC
            <CardCvcElement options={CARD_ELEMENT_OPTIONS} />
          </label>
        </div>
        <button type="submit" disabled={!stripe}>
          Zapłać
        </button>
      </form>
      {paymentMessage && (
        <div className="payment-message">{paymentMessage}</div>
      )}
    </div>
  );
};

export default StripePayment;
