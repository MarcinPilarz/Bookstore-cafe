import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, Link, useNavigate } from "react-router-dom";
import SearchBarProducts from "./SearchBarProducts";
import Navbar from "../Navbar/Navbar";
import Footer from "../Footer/Footer";
import "./ProductSection.css";
import CoffeeIntensity from "./CoffeeIntensity";
import ProductDetails from "./ProductDetails";
import { useCart } from "./BusketProducts";
import { useAuth } from "../Login/LoginInfoContext";
import PaginationProducts from "./PaginationProducts";
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
  const navigate = useNavigate();

  const [currentPage, setCurrentPage] = useState(1);
  const [productsPerPage] = useState(12);

  const indexOfLastProduct = currentPage * productsPerPage;
  const indexOfFirstProduct = indexOfLastProduct - productsPerPage;
  const currentProducts = products.slice(
    indexOfFirstProduct,
    indexOfLastProduct
  );

  const paginate = (pageNumber) => setCurrentPage(pageNumber);
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

  const { authData } = useAuth();
  const idPerson = authData?.idPerson;
  const isBooksActive = productType === "BOOK" ? "active-product" : "";
  const isCoffeeActive = productType === "COFFEE" ? "active-product" : "";
  const isFoodActive = productType === "FOOD" ? "active-product" : "";
  const isAllProductsActive =
    productType === "ALLPRODUCTS" ? "active-product" : "";
  useEffect(() => {
    axios
      //.get(`http://localhost:8080/images?productType=${productType}`)
      .get(`http://localhost:8080/products?productType=${productType}`)
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

  useEffect(() => {
    // Resetuj numer strony na 1, za każdym razem gdy productType się zmienia
    setCurrentPage(1);
  }, [productType]); // Dodaj productType jako zależność efektu

  const toggleDetails = (productId) => {
    setDetailsMap((prevDetailsMap) => ({
      ...prevDetailsMap,
      [productId]: !prevDetailsMap[productId],
    }));
  };

  const handleAddToBusket = (product) => {
    addToBusket(product);

    setAddedToCartList((prevList) => {
      return [...prevList, product];
    });
  };
  const handleSigninClick = () => {
    navigate("/signin");
  };
  let productInfo;
  switch (productType) {
    case "COFFEE":
      productInfo = (
        <div className="tile-products-border">
          {currentProducts.map((product) => (
            <div className="tile-products-list" key={product.idProduct}>
              <img
                className="photo-list-products"
                src={product.imageName}
                alt={product.title}
              />
              <h3 className="produc-name-text">{product.productName}</h3>
              <h3 className="product-price-text">
                Cena: {product.productPrice} zł
              </h3>
              {/* <h3>{product.coffeeIntensity}</h3> */}
              <h3 className="coffee-intensity">
                <span className="intensity-label">Intensywność:</span>
                <CoffeeIntensity
                  className="coffee-intensity-circle"
                  intensity={product.coffeeIntensity}
                />
              </h3>
              <div className="button-busket-info-container">
                <>
                  <button
                    className="more-info-button"
                    // onClick={productDetails}
                    // allProducts={products}
                    onClick={() => toggleDetails(product.idProduct)}
                  >
                    Szczegóły
                  </button>
                  {detailsMap[product.idProduct] && (
                    <ProductDetails
                      onClose={() =>
                        setDetailsMap((prevDetailsMap) => ({
                          ...prevDetailsMap,
                          [product.idProduct]: false,
                        }))
                      }
                      productId={product.idProduct}
                      allProducts={products}
                    />
                  )}
                </>
                <>
                  {authData?.roleType === "Klient" ? (
                    <button
                      className="add-busket-button"
                      onClick={() => {
                        console.log(
                          "Produkt w buuttonie do przeslania",
                          product
                        ); // Wyświetla informacje o produkcie w konsoli
                        handleAddToBusket(product);
                      }}
                    >
                      Do koszyka
                    </button>
                  ) : (
                    <button
                      className="else-add-busket-button"
                      onClick={handleSigninClick}
                    >
                      Zaloguj się jako klient
                    </button>
                  )}
                </>
              </div>
              {/* Dodaj inne dane produktu, jeśli są dostępne */}
            </div>
          ))}
          <div className="pagination-container">
            <PaginationProducts
              itemsPerPage={productsPerPage}
              totalItems={products.length}
              paginate={paginate}
              currentPage={currentPage}
            />
          </div>
        </div>
      );
      break;

    case "BOOK":
      productInfo = (
        <div className="tile-products-border">
          {currentProducts.map((product) => (
            <div className="tile-products-list" key={product.idProduct}>
              <img
                className="photo-list-products"
                src={product.imageName}
                alt={product.title}
              />
              <h3 className="produc-name-text">{product.productName}</h3>
              <h3 className="product-price-text">
                Cena: {product.productPrice} zł
              </h3>
              {/* <h3>{product.coffeeIntensity}</h3> */}
              <h3 id="food-info">
                <span className="calories-label">Autor:</span>
                {product.author}
                <span className="weight-label">Rodzaj:</span>
                {product.genere}
              </h3>
              <div className="button-busket-info-container">
                <>
                  <button
                    className="more-info-button"
                    // onClick={productDetails}
                    // allProducts={products}
                    onClick={() => toggleDetails(product.idProduct)}
                  >
                    Szczegóły
                  </button>
                  {detailsMap[product.idProduct] && (
                    <ProductDetails
                      onClose={() =>
                        setDetailsMap((prevDetailsMap) => ({
                          ...prevDetailsMap,
                          [product.idProduct]: false,
                        }))
                      }
                      productId={product.idProduct}
                      allProducts={products}
                    />
                  )}
                </>
                <>
                  {authData?.roleType === "Klient" ? (
                    <button
                      className="add-busket-button"
                      onClick={() => {
                        console.log(
                          "Produkt w buuttonie do przeslania",
                          product
                        ); // Wyświetla informacje o produkcie w konsoli
                        handleAddToBusket(product);
                      }}
                    >
                      Do koszyka
                    </button>
                  ) : (
                    <button
                      className="else-add-busket-button"
                      onClick={handleSigninClick}
                    >
                      Zaloguj się jako klient
                    </button>
                  )}
                </>
              </div>
            </div>
          ))}
          <div className="pagination-container">
            <PaginationProducts
              itemsPerPage={productsPerPage}
              totalItems={products.length}
              paginate={paginate}
            />
          </div>
        </div>
      );
      break;
    case "FOOD":
      productInfo = (
        <div className="tile-products-border">
          {currentProducts.map((product) => (
            <div className="tile-products-list" key={product.idProduct}>
              <img
                className="photo-list-products"
                src={product.imageName}
                alt={product.title}
              />
              <h3 className="produc-name-text">{product.productName}</h3>
              <h3 className="product-price-text">
                Cena: {product.productPrice} zł
              </h3>
              {/* <h3>{product.coffeeIntensity}</h3> */}
              <h3 id="food-info">
                <span className="calories-label">Kalorie:</span>
                {product.amountOfCalories}
                <span className="weight-label">Waga potrawy:</span>
                {product.foodWeight} g
              </h3>
              <div className="button-busket-info-container">
                <>
                  <button
                    className="more-info-button"
                    // onClick={productDetails}
                    // allProducts={products}
                    onClick={() => toggleDetails(product.idProduct)}
                  >
                    Szczegóły
                  </button>
                  {detailsMap[product.idProduct] && (
                    <ProductDetails
                      onClose={() =>
                        setDetailsMap((prevDetailsMap) => ({
                          ...prevDetailsMap,
                          [product.idProduct]: false,
                        }))
                      }
                      productId={product.idProduct}
                      allProducts={products}
                    />
                  )}
                </>
                <>
                  {authData?.roleType === "Klient" ? (
                    <button
                      className="add-busket-button"
                      onClick={() => {
                        console.log(
                          "Produkt w buuttonie do przeslania",
                          product
                        ); // Wyświetla informacje o produkcie w konsoli
                        handleAddToBusket(product);
                      }}
                    >
                      Do koszyka
                    </button>
                  ) : (
                    <button
                      className="else-add-busket-button"
                      onClick={handleSigninClick}
                    >
                      Zaloguj się jako klient
                    </button>
                  )}
                </>
              </div>
            </div>
          ))}
          <div className="pagination-container">
            <PaginationProducts
              itemsPerPage={productsPerPage}
              totalItems={products.length}
              paginate={paginate}
            />
          </div>
        </div>
      );
      break;
    case "ALLPRODUCTS":
      productInfo = (
        <div className="tile-products-border">
          {currentProducts.map((product) => (
            <div
              id="title-product-all-list"
              className="tile-products-list"
              key={product.idProduct}
            >
              <img
                className="photo-list-products"
                src={product.imageName}
                alt={product.title}
              />
              <h3 className="produc-name-text">{product.productName}</h3>
              <h3 className="product-price-text">
                Cena: {product.productPrice} zł
              </h3>

              {product.productType === "COFFEE" && (
                <h3 className="coffee-intensity">
                  <span className="intensity-label">Intensywność:</span>
                  <CoffeeIntensity
                    className="coffee-intensity-circle"
                    intensity={product.coffeeIntensity}
                  />
                </h3>
              )}
              {product.productType === "BOOK" && (
                <h3 id="food-info">
                  <span className="calories-label">Autor:</span>
                  {product.author}
                  <span className="weight-label">Rodzaj:</span>
                  {product.genere}
                </h3>
              )}
              {product.productType === "FOOD" && (
                <h3 id="food-info">
                  <span className="calories-label">Kalorie:</span>
                  {product.amountOfCalories}
                  <span className="weight-label">Waga potrawy:</span>
                  {product.foodWeight} g
                </h3>
              )}
              {/* Dodaj inne dane produktu, jeśli są dostępne */}
              <div className="button-busket-info-container">
                <>
                  <button
                    className="more-info-button"
                    // onClick={productDetails}
                    // allProducts={products}
                    onClick={() => toggleDetails(product.idProduct)}
                  >
                    Szczegóły
                  </button>
                  {detailsMap[product.idProduct] && (
                    <ProductDetails
                      onClose={() =>
                        setDetailsMap((prevDetailsMap) => ({
                          ...prevDetailsMap,
                          [product.idProduct]: false,
                        }))
                      }
                      productId={product.idProduct}
                      allProducts={products}
                    />
                  )}
                </>
                <>
                  {authData?.roleType === "Klient" ? (
                    <button
                      className="add-busket-button"
                      onClick={() => {
                        console.log(
                          "Produkt w buuttonie do przeslania",
                          product
                        ); // Wyświetla informacje o produkcie w konsoli
                        handleAddToBusket(product);
                      }}
                    >
                      Do koszyka
                    </button>
                  ) : (
                    <button
                      className="else-add-busket-button"
                      onClick={handleSigninClick}
                    >
                      Zaloguj się jako klient
                    </button>
                  )}
                </>
              </div>
            </div>
          ))}
          <div className="pagination-container">
            <PaginationProducts
              itemsPerPage={productsPerPage}
              totalItems={products.length}
              paginate={paginate}
            />
          </div>
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
      {/* <h1 className="productType-name">{productType}</h1> */}
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
              // <div key={product.idProduct}>
              <div
                id="title-product-all-list"
                className="tile-products-list"
                key={product.idProduct}
              >
                <img
                  className="photo-list-products"
                  src={product.imageName}
                  alt={product.title}
                />
                <h3 className="produc-name-text">{product.productName}</h3>
                <h3 className="product-price-text">
                  Cena: {product.productPrice} zł
                </h3>
                {/* Dodaj warunek sprawdzający typ produktu i wyświetl odpowiednie informacje */}
                {product.productType === "COFFEE" && (
                  <h3 className="coffee-intensity">
                    <span className="intensity-label">Intensywność:</span>
                    <CoffeeIntensity
                      className="coffee-intensity-circle"
                      intensity={product.coffeeIntensity}
                    />
                  </h3>
                )}
                {product.productType === "BOOK" && (
                  <h3 id="food-info">
                    <span className="calories-label">Autor:</span>
                    {product.author}
                    <span className="weight-label">Rodzaj:</span>
                    {product.genere}
                  </h3>
                )}
                {product.productType === "FOOD" && (
                  <h3 id="food-info">
                    <span className="calories-label">Kalorie:</span>
                    {product.amountOfCalories}
                    <span className="weight-label">Waga potrawy:</span>
                    {product.foodWeight} g
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
