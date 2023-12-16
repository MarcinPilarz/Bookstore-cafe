import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, Link } from "react-router-dom";
import SearchBarProducts from "./SearchBarProducts";
import Navbar from "../Navbar/Navbar";
import Footer from "../Footer/Footer";
import "./ProductSection.css";
import CoffeeIntensity from "./CoffeeIntensity";
import ProductDetails from "./ProductDetails";
import { useCart } from "./BusketProducts";

// props= searchResault
const ProductSection = () => {
  //const { productType } = useParams();
  const [products, setProducts] = useState([]);
  const [filterProducts, setFilterProducts] = useState([]);
  const [searchProducts, setSearchProducts] = useState("");
  const [filteredProducts, setFilteredProducts] = useState([]);

  const [searchQuery, setSearchQuery] = useState("");

  const [showDetails, setShowDetails] = useState(false);
  const [productSelectedId, setProdcutSelectedId] = useState(null);
  const [detailsMap, setDetailsMap] = useState({});

  const [addedToCartList, setAddedToCartList] = useState([]);
  const { productType } = useParams();
  const { addToBusket, productsList } = useCart();
  //const { busket } = useCart();

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

  const handleSearch = (searchQuery, filteredProducts) => {
    setSearchQuery(searchQuery);
    setFilteredProducts(filteredProducts);
  };

  const handleTabClick = () => {
    // Wyczyść stan Search Bara po kliknięciu w zakładkę
    setSearchQuery("");
    document.getElementById("searchInput").value = "";
  };

  const clearSearch = () => {
    setSearchProducts("");
    setFilterProducts(products);
  };

  const productDetails = () => {
    // setProdcutSelectedId(productId);
    // console.log("Productid:", productId);
    setShowDetails(!showDetails);
    console.log("Pokazane szczegoly", !showDetails);
  };

  const toggleDetails = (productId) => {
    setDetailsMap((prevDetailsMap) => ({
      ...prevDetailsMap,
      [productId]: !prevDetailsMap[productId],
    }));
  };

  const handleAddToBusket = (product) => {
    console.log("Console log w handleAddToBusket", product);
    addToBusket(product);

    // Dodaj cały obiekt produktu do addedToCartList
    console.log("Dodawanie do addedToCartList", product);
    console.log("Aktualny stan addedToCartList:", addedToCartList);
    setAddedToCartList((prevList) => {
      console.log("PrevList w setAddedToCartList:", prevList);
      return [...prevList, product];
    });
  };

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
                <>
                  <button
                    className="more-info-button"
                    // onClick={productDetails}
                    // allProducts={products}
                    onClick={() => toggleDetails(product.product.idProduct)}
                  >
                    Szczegóły
                  </button>
                  {detailsMap[product.product.idProduct] && (
                    <ProductDetails
                      onClose={() =>
                        setDetailsMap((prevDetailsMap) => ({
                          ...prevDetailsMap,
                          [product.product.idProduct]: false,
                        }))
                      }
                      productId={product.product.idProduct}
                      allProducts={products}
                    />
                  )}
                </>
                <>
                  {" "}
                  <button className="add-busket-button">Do koszyka</button>
                </>
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
                <>
                  <button
                    className="more-info-button"
                    // onClick={productDetails}
                    // allProducts={products}
                    onClick={() => toggleDetails(product.product.idProduct)}
                  >
                    Szczegóły
                  </button>
                  {detailsMap[product.product.idProduct] && (
                    <ProductDetails
                      onClose={() =>
                        setDetailsMap((prevDetailsMap) => ({
                          ...prevDetailsMap,
                          [product.product.idProduct]: false,
                        }))
                      }
                      productId={product.product.idProduct}
                      allProducts={products}
                    />
                  )}
                </>
                <>
                  <button className="add-busket-button">Do koszyka</button>
                </>
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
                <>
                  <button
                    className="more-info-button"
                    // onClick={productDetails}
                    // allProducts={products}
                    onClick={() => toggleDetails(product.product.idProduct)}
                  >
                    Szczegóły
                  </button>
                  {detailsMap[product.product.idProduct] && (
                    <ProductDetails
                      onClose={() =>
                        setDetailsMap((prevDetailsMap) => ({
                          ...prevDetailsMap,
                          [product.product.idProduct]: false,
                        }))
                      }
                      productId={product.product.idProduct}
                      allProducts={products}
                    />
                  )}
                </>
                <>
                  <button className="add-busket-button">Do koszyka</button>
                </>
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
            <div
              id="title-product-all-list"
              className="tile-products-list"
              key={product.product.idProduct}
            >
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
                <>
                  <button
                    className="more-info-button"
                    // onClick={productDetails}
                    // allProducts={products}
                    onClick={() => toggleDetails(product.product.idProduct)}
                  >
                    Szczegóły
                  </button>
                  {detailsMap[product.product.idProduct] && (
                    <ProductDetails
                      onClose={() =>
                        setDetailsMap((prevDetailsMap) => ({
                          ...prevDetailsMap,
                          [product.product.idProduct]: false,
                        }))
                      }
                      productId={product.product.idProduct}
                      allProducts={products}
                    />
                  )}
                </>
                <>
                  <button
                    className="add-busket-button"
                    onClick={() => {
                      console.log(
                        "Produkt w buuttonie do przeslania",
                        product.product
                      ); // Wyświetla informacje o produkcie w konsoli
                      handleAddToBusket(product.product);
                    }}
                  >
                    Do koszyka
                  </button>
                </>
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
            <Link to="/products-page/ALLPRODUCTS" onClick={handleTabClick}>
              Wszystkie produkty
            </Link>
          </li>
          <li className={isBooksActive}>
            <Link to="/products-page/BOOK" onClick={handleTabClick}>
              Książki
            </Link>
          </li>
          <li className={isCoffeeActive}>
            <Link to="/products-page/COFFEE" onClick={handleTabClick}>
              Kawy
            </Link>
          </li>
          <li className={isFoodActive}>
            <Link to="/products-page/FOOD" onClick={handleTabClick}>
              Jedzenie
            </Link>
          </li>
        </ul>
        <SearchBarProducts
          allProducts={products}
          onSearch={handleSearch}
          productType={productType}
          clearSearch={clearSearch}
        />

        {searchQuery.trim() === "" ? (
          // Jeśli nic nie wpisano, wyświetl productInfo
          <div>{productInfo}</div>
        ) : (
          // W przeciwnym razie, wyświetl przefiltrowane produkty
          <div className="tile-products-border">
            {filteredProducts.map((product) => (
              // <div key={product.product.idProduct}>
              <div
                id="title-product-all-list"
                className="tile-products-list"
                key={product.product.idProduct}
              >
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
        )}
        {/* </div> */}
        {/* )} */}
      </div>
      <Footer />
    </>
  );
};

export default ProductSection;
