import React, {useState} from "react";
import {Link} from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBasketShopping, faBars } from '@fortawesome/free-solid-svg-icons';
import "./Navbar.css";


const Navbar =() => {

  const [showMenuBars, setShowMenuBars]=useState(false);
  
  const toggleMenu =() => {
  setShowMenuBars(!showMenuBars);
  console.log("Menu zostało przełączone. Aktualny stan menuOpen:", !showMenuBars);

  };
  return (

 <header>
    
    <nav className={`bar-icon ${showMenuBars ? 'open' : ''}`}>
    <div onClick={toggleMenu} className="hamburger"><FontAwesomeIcon icon={faBars} /></div>
        <ul className="navbar">
         <li> <a href="/#">Strona główna</a></li>
       <li> <a href="/products">Produkty</a> </li>
       <li><a href="/#">Rezerwacja</a></li> 
       <li> <a href="/#">Wydarzenia</a> </li>
      
       <li className="sign-in"><a href="/login">Zaloguj</a></li>
       <li className="sign-up"><a href="/login">Zarejestruj</a></li>
        </ul>
        <div className="busket-icon">  <FontAwesomeIcon icon={faBasketShopping} /></div>
    </nav>
  

   
   
   

   </header>
  );
}

export default Navbar;
