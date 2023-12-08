import React, { useState, useEffect } from "react";
import ProductSection from "./ProductSection";
const SearchBarProducts = ({ allProducts, onSearch, productType }) => {
  const [searchQuery, setSearchQuery] = useState("");

  const handleSearchChange = (e) => {
    const newSearchQuery = e.target.value;
    setSearchQuery(newSearchQuery);

    // Filtrowanie produktów na podstawie wprowadzonego zapytania
    const filteredProducts = allProducts.filter((product) =>
      product.product.productName
        .toLowerCase()
        .includes(newSearchQuery.toLowerCase())
    );

    // Przekazanie przefiltrowanych produktów do komponentu nadrzędnego
    onSearch(newSearchQuery, filteredProducts);
  };

  const clearSearch = () => {
    setSearchQuery(""); // Ustaw puste pole wyszukiwania
    onSearch("", []); // Przekaż puste zapytanie i pustą listę do komponentu nadrzędnego
  };
  useEffect(() => {
    // Wyczyść pole wyszukiwania po zmianie zakładki
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
      <button type="button" onClick={clearSearch}>
        Wyczyść
      </button>
    </form>
  );
};

export default SearchBarProducts;
