import React from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../Login/LoginInfoContext";
import { useCart } from "../ProductSection/BusketProducts";
import "./OrderItem.css";
const OrderItem = () => {
  const { busket, clearBusket, updateProductQuantity, removeFromBusket } =
    useCart();
  const { authData } = useAuth();
  const totalPrice = busket.reduce((total, product) => {
    return total + product.productPrice * product.quantity;
  }, 0);

  return (
    <div className="order-summary">
      <div className="order-items-container">
        {busket.map((product) => (
          <div className="order-item" key={product.idProduct}>
            <p className="product-name">
              <b>Nazwa produktu:</b> {product.productName}
            </p>

            <div className="quantity-price">
              <div className="quantity">
                <b>Ilość:</b>
                <input
                  type="number"
                  value={product.quantity}
                  onChange={(e) =>
                    updateProductQuantity(
                      product.idProduct,
                      Math.min(Math.max(1, e.target.value), 10)
                    )
                  }
                  min="1"
                  max="10"
                />
              </div>
              <div className="price">
                <b>Cena:</b>{" "}
                {(product.productPrice * product.quantity).toFixed(2)} zł
              </div>
            </div>
            <button
              className="remove-item"
              onClick={() => removeFromBusket(product.idProduct)}
            >
              Usuń
            </button>
            <p>
              {product.productType === "BOOK" &&
              product.numberBookStock < product.quantity ? (
                <span className="unavailable">
                  Ilość tego produktu w lokalu wynosi {product.numberBookStock}{" "}
                  jeśli chcesz kontynuuować, twoje zamówienie zostanie
                  podzielone na dwa części.{" "}
                </span>
              ) : (
                ""
              )}
            </p>
          </div>
        ))}
      </div>

      <div className="order-summary-details">
        <div className="total-price">
          <p>Łączna cena: {totalPrice.toFixed(2)} zł</p>
        </div>
        <div className="order-pickup-method">
          <p>
            Metoda odbioru: <strong>Odbiór tylko w lokalu</strong>
          </p>
        </div>
        <button className="pay-button">
          <Link to="/stripe">Zapłać</Link>
        </button>
      </div>
    </div>
  );
};
export default OrderItem;
