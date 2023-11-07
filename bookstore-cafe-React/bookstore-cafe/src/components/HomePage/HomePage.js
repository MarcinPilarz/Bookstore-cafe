import React, { useState } from "react";
import "../HomePage/HomePage.css";
import ProductsTile from "./ProductsTile";
import ProductModal from "./ProductModal";
const HomePage = () => {
  const products = [
    { title: "Koszulki", image: "koszulki.jpg" },
    { title: "Buty", image: "buty.jpg" },
    { title: "Spodnie", image: "spodnie.jpg" },
    // Dodaj więcej produktów
  ];

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);

  const openModal = (product) => {
    setSelectedProduct(product);
    setIsModalOpen(true);
  };

  // Funkcja zamykająca modal
  const closeModal = () => {
    setSelectedProduct(null);
    setIsModalOpen(false);
  };
  return (
    <div className="homepage">
      <img src="your-image.jpg" alt="Nasza firma" />
      <section>
        <h1>O Nas</h1>
        <p>
          Jesteśmy firmą specjalizującą się w dostarczaniu wysokiej jakości
          produktów i usług.
        </p>
      </section>
      <section>
        <h1>Wydarzenia</h1>
        <p>Bądź na bieżąco z naszymi najnowszymi wydarzeniami i promocjami.</p>
      </section>
      <section>
        <h1>Produkty</h1>
        <div className="product-tiles">
          {products.map((product, index) => (
            <div
              key={index}
              className="product-tile"
              onClick={() => openModal(product)}
            >
              <img src={product.image} alt={product.title} />
              <h3>{product.title}</h3>
            </div>
          ))}
        </div>
      </section>
      {isModalOpen && selectedProduct && (
        <ProductModal productData={selectedProduct} closeModal={closeModal} />
      )}
      <section>
        <h1>Rezerwacje</h1>
        <p>Zarezerwuj u nas stolik lub usługę online i ciesz się wygodą.</p>
      </section>
      <section>
        <h1>Komentarze</h1>
        <p>
          Przeczytaj opinie naszych klientów i podziel się swoim doświadczeniem.
        </p>
      </section>
    </div>
  );
};

export default HomePage;
