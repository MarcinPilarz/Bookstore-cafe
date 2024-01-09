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
    <div className="product-details-modal-container" onClick={onClose}>
      <div className="product-details-modal-content">
        {selectedProduct ? (
          <>
            <h2 className="product-details-title">
              {selectedProduct.productName}
            </h2>
            <p className="product-description">
              Opis: {selectedProduct.productDescription}
            </p>
            <p className="product-price">
              Cena: {selectedProduct.productPrice} zł
            </p>

            {selectedProduct.productType === "COFFEE" && (
              <div className="coffee-details-container">
                <h3 className="coffee-intensity-title">
                  <span className="intensity-title-label">Intensywność:</span>
                  {/* <CoffeeIntensity
                    className="coffee-intensity-display"
                    intensity={selectedProduct.coffeeIntensity}
                  /> */}
                  {selectedProduct.coffeeIntensity}/10
                </h3>
                <p>
                  Intensywność kawy w skali od 1 do 10 opisuje stopień jej
                  smaku, aromatu, gęstości i mocy, gdzie 1 oznacza bardzo lekką,
                  delikatną kawę, a 10 bardzo intensywną, mocną i z pełnym,
                  głębokim smakiem.
                </p>
              </div>
            )}

            {selectedProduct.productType === "BOOK" && (
              <div className="book-details-info">
                <span className="book-author-label">Autor:</span>
                {selectedProduct.author}
                <span className="book-genre-label">Gatunek:</span>
                {selectedProduct.genere}
                <span className="book-publishing-house-label">
                  Wydawnictwo:
                </span>
                {selectedProduct.publishingHouse}
                <span className="book-language-label">Język:</span>
                {selectedProduct.language}
                <span className="book-publication-date-label">
                  Rok wydania:
                </span>
                {selectedProduct.publicationDate}
                <span className="book-cover-label">Okładka:</span>
                {selectedProduct.bookCover}
                <span className="book-page-count-label">Liczba stron:</span>
                {selectedProduct.numberPage}
              </div>
            )}

            {selectedProduct.productType === "FOOD" && (
              <div className="food-details-info">
                <span className="food-calories-label">Kalorie:</span>
                {selectedProduct.amountOfCalories}
                <span className="food-weight-label">Waga potrawy:</span>
                {selectedProduct.foodWeight} g
              </div>
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
