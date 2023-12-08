import React from "react";
import "./ProductDetails.css";
import CoffeeIntensity from "./CoffeeIntensity";
const ProductDetails = ({ productId, allProducts, onClose, productType }) => {
  const selectedProduct = allProducts.find(
    (product) => product.product.idProduct === productId
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
            <h2>{selectedProduct.product.productName}</h2>
            <p>Price: {selectedProduct.product.productPrice} zł</p>
            <p>Opis: {selectedProduct.product.productDescription}</p>
            {/* Dodaj inne informacje o produkcie, które chcesz wyświetlić */}
            {/* <div>cos{detailedInformation}</div> */}

            {selectedProduct.product.productType === "COFFEE" && (
              <>
                <h3 className="coffee-intensity">
                  <span className="intensity-label">Intensywność:</span>
                  <CoffeeIntensity
                    className="coffee-intensity-circle"
                    intensity={selectedProduct.product.coffeeIntensity}
                  />
                </h3>
                {/* Dodaj inne informacje o kawie, jeśli są dostępne */}
              </>
            )}

            {selectedProduct.product.productType === "BOOK" && (
              <>
                <h3 id="book-info">
                  <span className="author-label">Autor:</span>
                  {selectedProduct.product.author}
                  <span className="genre-label">Gatunek:</span>
                  {selectedProduct.product.genere}
                </h3>
                {/* Dodaj inne informacje o książce, jeśli są dostępne */}
              </>
            )}

            {/* Warunek dla jedzenia */}
            {selectedProduct.product.productType === "FOOD" && (
              <>
                <h3 id="food-info">
                  <span className="calories-label">Kalorie:</span>
                  {selectedProduct.product.amountOfCalories}
                  <span className="weight-label">Waga potrawy:</span>
                  {selectedProduct.product.foodWeight} g
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
