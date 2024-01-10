import React from "react";
import "./PaginationProducts.css";
const PaginationProducts = ({
  itemsPerPage,
  totalItems,
  paginate,
  currentPage,
}) => {
  const pageNumbers = [];

  for (let i = 1; i <= Math.ceil(totalItems / itemsPerPage); i++) {
    pageNumbers.push(i);
  }

  return (
    <nav className="nav-background">
      <ul className="pagination-products">
        {pageNumbers.map((number) => (
          <li key={number} className="page-item">
            <a
              onClick={() => paginate(number)}
              className={`page-link ${
                currentPage === number ? "active-page" : ""
              }`}
              href="#!"
            >
              {number}
            </a>
          </li>
        ))}
      </ul>
    </nav>
  );
};
export default PaginationProducts;
