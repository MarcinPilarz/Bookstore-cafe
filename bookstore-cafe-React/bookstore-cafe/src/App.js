import React from "react";
import logo from "./logo.svg";
import "./App.css";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import HomePage from "./components/HomePage/HomePage";
//mport {BusketSideBarProvider} from './components/SideBars/BusketSideBar';
import AppRouters from "./components/AppRouters";
import MainPhoto from "./components/HomePage/MainPhoto";
import Footer from "./components/Footer/Footer";
import ProductSection from "./components/ProductSection/ProductSection";
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/products-page" element={<ProductSection />} />
      </Routes>
    </BrowserRouter>
    // <React.Fragment>
    //{/* <BusketSideBarProvider> */}
    //<Navbar />
    // <MainPhoto />
    //<HomePage />
    // {/* <AppRouters /> */}
    //  {/* </BusketSideBarProvider> */}
    //  <Footer />
    //</React.Fragment>
  );
}

export default App;
