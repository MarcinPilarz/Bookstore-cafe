import React from "react";
import "./ProductsTile.css";
const ProductsTile = ({ title, image }) => {
  return (
    <div className="product-tile">
      <img src={image} alt={title} />
      <h3>{title}</h3>
    </div>
  );
};

export default ProductsTile;
