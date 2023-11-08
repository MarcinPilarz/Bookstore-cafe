import React from "react";
import "./ProductsTile.css";
const ProductsTile = ({ title, image }) => {
  return (
    <div className="product-tile">
      <div className="image-container">
        <img className="products" src={image} alt={title} />
      </div>
      <h3>{title}</h3>
    </div>
  );
};

export default ProductsTile;
