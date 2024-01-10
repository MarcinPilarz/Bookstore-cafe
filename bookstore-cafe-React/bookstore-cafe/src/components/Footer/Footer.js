import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFacebook, faInstagram } from "@fortawesome/free-brands-svg-icons";
import "./Footer.css";
const Footer = () => {
  return (
    <section className="footer-section">
      <footer className="top">
        <img
          className="logo-footer"
          src="https://storage.googleapis.com/springbootphoto/springbootphoto/Czytaj%20z%20Kaw%C4%85%20logo.png"
          alt="Logo"
        />
        <div className="links">
          <div className="links-column">
            <h2>Kontakt</h2>
            <a>Telefon: +48 123 456 789</a>
            <p>
              E-mail:
              <a> czytajzkawa@example.pl</a>
            </p>
          </div>
          <div className="links-column socials-column">
            <h2>Media społecznościowe</h2>
            <p>Polub nas:</p>
            <div className="socials">
              <a
                href="https://www.facebook.com/"
                target="_blank"
                rel="noopener noreferrer"
              >
                <FontAwesomeIcon icon={faFacebook} />
              </a>
              <a
                href="https://www.instagram.com/"
                target="_blank"
                rel="noopener noreferrer"
              >
                <FontAwesomeIcon icon={faInstagram} />
              </a>
            </div>
          </div>
        </div>
      </footer>
      <footer className="bottom">
        <p className="copyright">2024 CzytajzKawa</p>
        <div className="legal">
          <a>License</a>
        </div>
      </footer>
    </section>
  );
};

export default Footer;
