import React from "react";
import "./CoffeeIntensity.css";
const CoffeeIntensity = ({ intensity }) => {
  const maxIntensity = 10;
  const circles = [];

  for (let i = 0; i < maxIntensity; i++) {
    circles.push(
      <div
        key={i}
        className={`intensity-circle ${i < intensity ? "filled" : ""}`}
      />
    );
  }

  return <div className="intensity-display">{circles}</div>;
};

export default CoffeeIntensity;
