import React, { useState } from "react";
import "./StripePaymentStyle.css";
const StripePayment = () => {
  const [email, setEmail] = useState("");
  const [fullName, setFullName] = useState("");
  const [cardDetails, setCardDetails] = useState({
    number: "",
    expiry: "",
    cvc: "",
  });

  const handleSubmit = (event) => {
    event.preventDefault();
    // Tutaj logika przetwarzania płatności
    console.log("Processing payment for:", email, fullName, cardDetails);
  };

  const handleCardDetailsChange = (event) => {
    const { name, value } = event.target;
    setCardDetails({
      ...cardDetails,
      [name]: value,
    });
  };
  return (
    <div className="payment-form">
      <form onSubmit={handleSubmit}>
        <div className="section">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="Enter your email"
            required
          />
        </div>

        <div className="section">
          <label htmlFor="fullName">Full Name</label>
          <input
            type="text"
            id="fullName"
            value={fullName}
            onChange={(e) => setFullName(e.target.value)}
            placeholder="Enter your full name"
            required
          />
        </div>

        <div className="section">
          <label htmlFor="cardNumber">Card Number</label>
          <input
            type="text"
            id="cardNumber"
            name="number"
            value={cardDetails.number}
            onChange={handleCardDetailsChange}
            placeholder="1234 5678 9012 3456"
            required
          />

          <label htmlFor="expiry">Expiry Date</label>
          <input
            type="text"
            id="expiry"
            name="expiry"
            value={cardDetails.expiry}
            onChange={handleCardDetailsChange}
            placeholder="MM/YY"
            required
          />

          <label htmlFor="cvc">CVC</label>
          <input
            type="text"
            id="cvc"
            name="cvc"
            value={cardDetails.cvc}
            onChange={handleCardDetailsChange}
            placeholder="CVC"
            required
          />
        </div>

        <button type="submit">Submit Payment</button>
      </form>
    </div>
  );
};

export default StripePayment;
