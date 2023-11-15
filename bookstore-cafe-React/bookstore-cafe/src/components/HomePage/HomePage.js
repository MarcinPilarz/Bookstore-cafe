import React, { useState, useEffect } from "react";
import "../HomePage/HomePage.css";
import ProductsTile from "./ProductsTile";
import ProductModal from "./ProductModal";
import { Link, NavLink } from "react-router-dom";
import EventSlider from "../EventSlider/EventSlider";
import CommentsSlider from "../Comments/CommentsSlider";
import axios from "axios";
import Navbar from "../Navbar/Navbar";
import Footer from "../Footer/Footer";
import MainPhoto from "./MainPhoto";
const HomePage = () => {
  const products = [
    {
      title: "Książki",
      image:
        "https://storage.googleapis.com/springbootphoto/springbootphoto/KsiazkaKafelekProdukt.png",
      productType: "BOOK",
    },
    {
      title: "Kawy",
      image:
        "https://storage.googleapis.com/springbootphoto/springbootphoto/KawaKafelekProdukt.png",
      productType: "COFFEE",
    },
    {
      title: "Jedzenie",
      image:
        "https://storage.googleapis.com/springbootphoto/springbootphoto/JedzenieKafelekProdukt.png",
      productType: "FOOD",
    },
    // Dodaj więcej produktów
  ];

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [events, setEvents] = useState([]);
  const openModal = (product) => {
    setSelectedProduct(product);
    setIsModalOpen(true);
  };

  // Funkcja zamykająca modal
  const closeModal = () => {
    setSelectedProduct(null);
    setIsModalOpen(false);
  };

  // useEffect(() => {
  //   axios
  //     .get("http://localhost:8080/events")
  //     .then((response) => {
  //       const eventsData = response.data;
  //       console.log("Pobrane dane z API:", eventsData);
  //       setEvents(eventsData);
  //     })
  //     .catch((error) => {
  //       console.error("Błąd pobierania danych wydarzeń", error);
  //     });
  // }, []);

  return (
    <>
      <Navbar />
      <MainPhoto />
      <div className="homepage">
        <section>
          <h1>O Nas</h1>
          <div className="about-us">
            <p>
              Jesteśmy pasjonatami ksiązek i kawy, dlatego postanowiliśmy się z
              wami podzielić naszym zamiłowaniem. Czytaj z kawą to nie tylko
              przestrzeń do poszukiwania swoich nowych lektur, ale również
              przytulne miejsce, gdzie można spędzić czas przy dobrej lekturze i
              pysznej kawie. Nasza oferta to różne gatunki książek, które
              podbijały serca czytelników. W kawiarni serwujemy aromatyczną kawę
              oraz przekąski, które umilają czas przy literaturze. Czytaj z kawą
              to miejsce, gdzie każdy miłośnik literatury i kawy znajdzie coś
              dla siebie.
            </p>
          </div>
        </section>
        <section id="products">
          <h1>Produkty</h1>
          <div className="product-tiles">
            {products.map((product, index) => (
              <NavLink
                key={index}
                to={`/products/${product.productType}`}
                className="product-tile"
                activeClassName="active-product"
                onClick={() => openModal(product)}
              >
                <img src={product.image} alt={product.title} />
                <h3>{product.title}</h3>
              </NavLink>
            ))}
          </div>
        </section>
        {isModalOpen && selectedProduct && (
          <ProductModal productData={selectedProduct} closeModal={closeModal} />
        )}
        <section id="events">
          <h1>Wydarzenia</h1>
          <p>
            Bądź na bieżąco z naszymi najnowszymi wydarzeniami i promocjami.
          </p>
        </section>

        <EventSlider />
        <section id="reservation">
          <h1>Rezerwacje</h1>
          <p>
            Chwila relaksu? Spotkanie ze znajomymi? Zarezerwuj u nas stolik i
            przestań martwić się o brak miejsca.
          </p>
          <a href="/reservation">
            <button>Zarezerwuj stolik</button>
          </a>
        </section>
        <section>
          <h1>Komentarze</h1>
          <p>
            Przeczytaj opinie naszych klientów i podziel się swoim
            doświadczeniem.
          </p>
        </section>
        <CommentsSlider />
      </div>

      <Footer />
    </>
  );
};

export default HomePage;
