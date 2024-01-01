import React from "react";
import "./ProductDetails.css";
import CoffeeIntensity from "./CoffeeIntensity";
const ProductDetails = ({ productId, allProducts, onClose, productType }) => {
  const selectedProduct = allProducts.find(
    (product) => product.idProduct === productId
  );

  const detailedInformation = () => {
    // if (productType === "COFFE") {
    //   <p>test</p>;
    // }
  };

  return (
    <div className="product-details-container" onClick={onClose}>
      <div className="product-details-content">
        {selectedProduct ? (
          <>
            <h2>{selectedProduct.productName}</h2>
            <p>Opis: {selectedProduct.productDescription}</p>
            <p>Cena: {selectedProduct.productPrice} zł</p>
            {/* Dodaj inne informacje o produkcie, które chcesz wyświetlić */}
            {/* <div>cos{detailedInformation}</div> */}

            {selectedProduct.productType === "COFFEE" && (
              <div className="coffee-intensity-container">
                <h3 className="coffee-intensity">
                  <span className="intensity-label">Intensywność:</span>
                  <CoffeeIntensity
                    className="coffee-intensity-circle"
                    intensity={selectedProduct.coffeeIntensity}
                  />
                </h3>
                {/* Dodaj inne informacje o kawie, jeśli są dostępne */}
              </div>
            )}

            {selectedProduct.productType === "BOOK" && (
              <>
                <h3 id="book-info">
                  <span className="author-label-details">Autor:</span>
                  {selectedProduct.author}
                  <span className="genre-label">Gatunek:</span>
                  {selectedProduct.genere}
                  {<br />}
                  <span className="publishingHouse-label">Wydawnictwo:</span>
                  {selectedProduct.publishingHouse}
                  <span className="language-label">Język:</span>
                  {selectedProduct.language}
                  {<br />}
                  <span className="publication-date-label">Rok wydania:</span>
                  {selectedProduct.publicationDate}
                  <span className="book-cover-label">Okładka:</span>
                  {selectedProduct.bookCover}
                  <span className="number-page-label">Liczba stron:</span>
                  {selectedProduct.numberPage}
                </h3>
                {/* Dodaj inne informacje o książce, jeśli są dostępne */}
              </>
            )}

            {/* Warunek dla jedzenia */}
            {selectedProduct.productType === "FOOD" && (
              <>
                <h3 id="food-info">
                  <span className="calories-label">Kalorie:</span>
                  {selectedProduct.amountOfCalories}
                  <span className="weight-label">Waga potrawy:</span>
                  {selectedProduct.foodWeight} g
                </h3>
                {/* Dodaj inne informacje o jedzeniu, jeśli są dostępne */}
              </>
            )}
          </>
        ) : (
          <p>Product not Found</p>
        )}
      </div>
    </div>
  );
};

export default ProductDetails;
