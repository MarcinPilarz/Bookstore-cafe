import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFacebook, faInstagram } from "@fortawesome/free-brands-svg-icons";
import "./Footer.css";
const Footer = () => {
  return (
    <section>
      <footer className="top">
        <img src="logo.svg" alt="Logo" />
        <div className="links">
          <div className="links-column">
            <h2>Kontakt</h2>
            <a>Telefon: 111111111</a>
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
