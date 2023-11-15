import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, Link } from "react-router-dom";
import Navbar from "../Navbar/Navbar";
import Footer from "../Footer/Footer";

const ProductSection = () => {
  const { productType } = useParams();
  const [products, setProducts] = useState([]);

  useEffect(() => {
    // Pobierz produkty dla danego productType z bazy danych
    const fetchData = async () => {
      try {
        const response = await axios.get(
          `http://example.com/api/products/${productType}`
        );
        setProducts(response.data);
      } catch (error) {
        console.error("Błąd podczas pobierania danych:", error);
      }
    };

    fetchData();
  }, [productType]);

  const categories = ["kawa", "ksiazki", "jedzenie"];

  return (
    <>
      <Navbar />
      <div>
        <h1>Strona z produktami: {productType}</h1>
        <ul>
          {categories.map((category) => (
            <li key={category}>
              <Link
                to={`/products-page/${category}`}
                className={category === productType ? "active" : ""}
              >
                {category.charAt(0).toUpperCase() + category.slice(1)}
              </Link>
            </li>
          ))}
        </ul>
        <ul>
          {products.map((product) => (
            <li key={product.id}>{product.title}</li>
          ))}
        </ul>
      </div>
      <Footer />
    </>
  );
};

export default ProductSection;
