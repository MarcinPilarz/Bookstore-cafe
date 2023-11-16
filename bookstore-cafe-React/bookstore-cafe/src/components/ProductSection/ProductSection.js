import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, Link } from "react-router-dom";
import Navbar from "../Navbar/Navbar";
import Footer from "../Footer/Footer";
import "./ProductSection.css";
import CoffeeIntensity from "./CoffeeIntensity";
const ProductSection = () => {
  //const { productType } = useParams();
  const [products, setProducts] = useState([]);
  const { productType } = useParams();
  //   useEffect(() => {
  //     // Pobierz produkty dla danego productType z bazy danych
  //     const fetchData = async () => {
  //       try {
  //         const response = await axios.get(
  //           `http://example.com/api/products/${productType}`
  //         );
  //         setProducts(response.data);
  //       } catch (error) {
  //         console.error("Błąd podczas pobierania danych:", error);
  //       }
  //     };

  //     fetchData();
  //   }, [productType]);
  const isBooksActive = productType === "BOOK" ? "active-product" : "";
  const isCoffeeActive = productType === "COFFEE" ? "active-product" : "";
  const isFoodActive = productType === "FOOD" ? "active-product" : "";
  const isAllProductsActive =
    productType === "ALLPRODUCTS" ? "active-product" : "";
  useEffect(() => {
    axios
      .get(`http://localhost:8080/images?productType=${productType}`)
      .then((response) => {
        const productsData = response.data;
        console.log("Pobrane dane produktow z API:", productsData);
        setProducts(productsData);
      })
      .catch((error) => {
        console.error("Błąd pobierania danych wydarzeń", error);
      });
  }, [productType]);

  let productInfo;
  switch (productType) {
    case "COFFEE":
      productInfo = (
        <div className="tile-products-border">
          {products.map((product) => (
            <div className="tile-products-list" key={product.product.idProduct}>
              <img
                className="photo-list-products"
                src={product.imageUrl}
                alt={product.title}
              />
              <h3 className="produc-name-text">
                {product.product.productName}
              </h3>
              <h3 className="product-price-text">
                Cena: {product.product.productPrice} zł
              </h3>
              {/* <h3>{product.product.coffeeIntensity}</h3> */}
              <h3 className="coffee-intensity">
                <span className="intensity-label">Intensywność:</span>
                <CoffeeIntensity
                  className="coffee-intensity-circle"
                  intensity={product.product.coffeeIntensity}
                />
              </h3>
              <div className="button-busket-info-container">
                <button className="more-info-button">Szczegóły</button>
                <button className="add-busket-button">Do koszyka</button>
              </div>
              {/* Dodaj inne dane produktu, jeśli są dostępne */}
            </div>
          ))}
        </div>
      );
      break;

    case "BOOK":
      productInfo = (
        <div className="tile-products-border">
          {products.map((product) => (
            <div className="tile-products-list" key={product.product.idProduct}>
              <img
                className="photo-list-products"
                src={product.imageUrl}
                alt={product.title}
              />
              <h3 className="produc-name-text">
                {product.product.productName}
              </h3>
              <h3 className="product-price-text">
                Cena: {product.product.productPrice} zł
              </h3>
              {/* <h3>{product.product.coffeeIntensity}</h3> */}
              <h3 id="food-info">
                <span className="calories-label">Autor:</span>
                {product.product.author}
                <span className="weight-label">Rodzaj:</span>
                {product.product.genere}
              </h3>
              <div className="button-busket-info-container">
                <button className="more-info-button">Szczegóły</button>
                <button className="add-busket-button">Do koszyka</button>
              </div>
              {/* Dodaj inne dane produktu, jeśli są dostępne */}
            </div>
          ))}
        </div>
      );
      break;
    case "FOOD":
      productInfo = (
        <div className="tile-products-border">
          {products.map((product) => (
            <div className="tile-products-list" key={product.product.idProduct}>
              <img
                className="photo-list-products"
                src={product.imageUrl}
                alt={product.title}
              />
              <h3 className="produc-name-text">
                {product.product.productName}
              </h3>
              <h3 className="product-price-text">
                Cena: {product.product.productPrice} zł
              </h3>
              {/* <h3>{product.product.coffeeIntensity}</h3> */}
              <h3 id="food-info">
                <span className="calories-label">Kalorie:</span>
                {product.product.amountOfCalories}
                <span className="weight-label">Waga potrawy:</span>
                {product.product.foodWeight} g
              </h3>
              <div className="button-busket-info-container">
                <button className="more-info-button">Szczegóły</button>
                <button className="add-busket-button">Do koszyka</button>
              </div>
              {/* Dodaj inne dane produktu, jeśli są dostępne */}
            </div>
          ))}
        </div>
      );
      break;
    case "ALLPRODUCTS":
      productInfo = (
        <div className="tile-products-border">
          {products.map((product) => (
            <div className="tile-products-list" key={product.product.idProduct}>
              <img
                className="photo-list-products"
                src={product.imageUrl}
                alt={product.title}
              />
              <h3 className="produc-name-text">
                {product.product.productName}
              </h3>
              <h3 className="product-price-text">
                Cena: {product.product.productPrice} zł
              </h3>
              {/* Dodaj warunek sprawdzający typ produktu i wyświetl odpowiednie informacje */}
              {product.product.productType === "COFFEE" && (
                <h3 className="coffee-intensity">
                  <span className="intensity-label">Intensywność:</span>
                  <CoffeeIntensity
                    className="coffee-intensity-circle"
                    intensity={product.product.coffeeIntensity}
                  />
                </h3>
              )}
              {product.product.productType === "BOOK" && (
                <h3 id="food-info">
                  <span className="calories-label">Autor:</span>
                  {product.product.author}
                  <span className="weight-label">Rodzaj:</span>
                  {product.product.genere}
                </h3>
              )}
              {product.product.productType === "FOOD" && (
                <h3 id="food-info">
                  <span className="calories-label">Kalorie:</span>
                  {product.product.amountOfCalories}
                  <span className="weight-label">Waga potrawy:</span>
                  {product.product.foodWeight} g
                </h3>
              )}
              {/* Dodaj inne dane produktu, jeśli są dostępne */}
              <div className="button-busket-info-container">
                <button className="more-info-button">Szczegóły</button>
                <button className="add-busket-button">Do koszyka</button>
              </div>
            </div>
          ))}
        </div>
      );
      break;
    default:
      productInfo = (
        <div>
          <p>Nie ma takiego typu produktu</p>
        </div>
      );
  }

  return (
    <>
      <Navbar />
      <h1 className="productType-name">{productType}</h1>
      <div className="li-product-card">
        <ul>
          <li className={isAllProductsActive}>
            <Link to="/products-page/ALLPRODUCTS">Wszystkie produkty</Link>
          </li>
          <li className={isBooksActive}>
            <Link to="/products-page/BOOK">Książki</Link>
          </li>
          <li className={isCoffeeActive}>
            <Link to="/products-page/COFFEE">Kawy</Link>
          </li>
          <li className={isFoodActive}>
            <Link to="/products-page/FOOD">Jedzenie</Link>
          </li>
        </ul>
      </div>
      <div>{productInfo}</div>
      {/* <div>
        <h1>Strona z produktami: {productType}</h1>
        <ul>
          {categories.map((category) => (
            <li key={category}>
              <Link
                to={`/products-page/${category}`}
                className={category === productType ? "active" : ""}
              >
                {category.charAt(0).toUpperCase() + category.slice(1)}
              </Link>
            </li>
          ))}
        </ul>
        <ul>
          {products.map((product) => (
            <li key={product.id}>{product.title}</li>
          ))}
        </ul>
      </div> */}
      <Footer />
    </>
  );
};

export default ProductSection;
