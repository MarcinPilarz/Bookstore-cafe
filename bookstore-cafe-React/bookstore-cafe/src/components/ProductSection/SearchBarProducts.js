import React, { useState, useEffect } from "react";

const SearchBarProducts = ({ onSearch }) => {
  const [searchProducts, setSearchProducts] = useState("");

  //useEffect;
  const handleSubmit = (e) => {
    e.preventDefault();

    onSearch(searchProducts);
  };
  const handleSearchChange = (e) => {
    // if (!e.target.value) return setSearchProducts(); //post
    // const resultArray = posts.filter((post) =>
    // post.title.includes(e.target.value) || post.body.includes(e.target.value));

    //setSearchProducts(resultsArray);
    const newSearchProduct = e.target.value;
    setSearchProducts(newSearchProduct);
    onSearch(newSearchProduct);
  };
  return (
    <form className="search-bar">
      <input
        className="search-input"
        type="text"
        placeholder="Wyszukaj produkt"
        value={searchProducts}
        onChange={handleSearchChange}
        // onChange={handleSearchChange}
      />
      {/* <button type className="search-button"></button> */}
    </form>
  );
};

export default SearchBarProducts;
