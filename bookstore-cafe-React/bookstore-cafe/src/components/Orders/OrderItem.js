import React from "react";
import { useCart } from "../ProductSection/BusketProducts";
import { useAuth } from "../Login/LoginInfoContext";
import axios from "axios";
import { Link } from "react-router-dom";

const OrderItem = () => {
  const { busket, clearBusket, updateProductQuantity, removeFromBusket } =
    useCart();
  const { authData } = useAuth();
  const totalPrice = busket.reduce((total, product) => {
    return total + product.productPrice * product.quantity;
  }, 0);

  // const convertProdcutToProductId = busket.map((item) => ({
  //   idProduct: item.idProduct,
  //   quantity: item.quantity,
  // }));

  // console.log("Produkty idki:", convertProdcutToProductId);
  // const idPerson = authData.idPerson;
  // console.log("idPerson:", idPerson);
  // const OrderProducts = async (product) => {
  //   const url = `http://localhost:8080/addOrder?idPerson=${idPerson}&idProduct=${product.idProduct}&quantity=${product.quantity}`;

  //   try {
  //     const response = await axios.post(url);
  //     console.log("Odpowiedź serwera:", response.data);
  //   } catch (error) {
  //     console.error("Błąd podczas wysyłania produktu:", error);
  //   }
  // };
  // // convertProdcutToProductId.forEach(handleOrderProducts);

  // const handleOrders = () => {
  //   convertProdcutToProductId.forEach(OrderProducts);
  // };
  return (
    <div className="order-item-list">
      {busket.map((product) => (
        <div key={product.idProduct}>
          {console.log("Produkt w koszyku:", product)}
          {console.log("product.productId W nVBAR", product.productId)}{" "}
          {/* Użyj unikalnego identyfikatora */}
          <p>
            <b>Nazwa produktu:</b>
            <br />
            {product.productName}
          </p>
          <div>
            <b>Ilość: </b>
            <br />
            <div>
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
            <div>Cena: {product.productPrice * product.quantity}</div>
          </div>
          <button onClick={() => removeFromBusket(product.idProduct)}>X</button>
          {/* inne informacje o produkcie */}
        </div>
      ))}
      <div className="total-price">
        <p>Łączna cena: {totalPrice.toFixed(2)} zł</p>
      </div>

      {/* <button onClick={handleOrders}>Zapłać</button> */}
      <button>
        <Link to="/stripe"> Zapłać</Link>
      </button>
    </div>
  );
};

export default OrderItem;
