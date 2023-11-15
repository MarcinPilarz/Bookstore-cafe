import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, Link } from "react-router-dom";
import Navbar from "../Navbar/Navbar";
import Footer from "../Footer/Footer";

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
  const isBooksActive = productType === "BOOK" ? "active" : "";
  const isCoffeeActive = productType === "COFFEE" ? "active" : "";
  const isFoodActive = productType === "FOOD" ? "active" : "";
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
        <div className="product-list">
          {products.map((product) => (
            <div key={product.product.idProduct}>
              <h3>{product.product.productName}</h3>
              <h3>{product.product.productPrice}</h3>
              {/* <img src={product.image} alt={product.title} /> */}
              {/* Dodaj inne dane produktu, jeśli są dostępne */}
            </div>
          ))}
        </div>
      );
      break;

    case "BOOK":
      productInfo = (
        <div className="product-list">
          {products.map((product) => (
            <div key={product.product.idProduct}>
              <h3>{product.product.productName}</h3>
              <h3>{product.product.productPrice}</h3>
              {/* <img src={product.image} alt={product.title} /> */}
              {/* Dodaj inne dane produktu, jeśli są dostępne */}
            </div>
          ))}
        </div>
      );
      break;
    case "FOOD":
      productInfo = (
        <div className="product-list">
          {products.map((product) => (
            <div key={product.product.idProduct}>
              <h3>{product.product.productName}</h3>
              <h3>{product.product.productPrice}</h3>
              {/* <img src={product.image} alt={product.title} /> */}
              {/* Dodaj inne dane produktu, jeśli są dostępne */}
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
      <h1>{productType}</h1>
      <div>
        <ul>
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
