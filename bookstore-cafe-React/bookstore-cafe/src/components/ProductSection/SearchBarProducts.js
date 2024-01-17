import React, { useEffect, useState } from "react";
import "./SearchBarProducts.css";
const SearchBarProducts = ({ allProducts, onSearch, productType }) => {
  const [searchQuery, setSearchQuery] = useState("");

  const handleSearchChange = (e) => {
    const newSearchQuery = e.target.value;
    setSearchQuery(newSearchQuery);

    const filteredProducts = allProducts.filter((product) =>
      product.productName.toLowerCase().includes(newSearchQuery.toLowerCase())
    );

    onSearch(newSearchQuery, filteredProducts);
  };

  const clearSearch = () => {
    setSearchQuery("");
    onSearch("", []);
  };
  useEffect(() => {
    clearSearch();
  }, [productType]);
  return (
    <form className="search-bar">
      <input
        id="searchInput"
        className="search-input"
        type="text"
        placeholder="Wyszukaj produkt"
        value={searchQuery}
        onChange={handleSearchChange}
      />
      <button
        type="button"
        className="clear-button-search-bar"
        onClick={clearSearch}
      >
        Wyczyść
      </button>
    </form>
  );
};

export default SearchBarProducts;
