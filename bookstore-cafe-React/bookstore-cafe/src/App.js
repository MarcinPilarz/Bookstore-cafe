import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import HomePage from "./components/HomePage/HomePage";
//mport {BusketSideBarProvider} from './components/SideBars/BusketSideBar';
import Signin from "./components/Login/Signin";
import Signup from "./components/Login/Signup";
import ProductSection from "./components/ProductSection/ProductSection";
import { CartProvider } from "./components/ProductSection/BusketProducts";
import { AuthProvider } from "./components/Login/LoginInfoContext";
import OrderItem from "./components/Orders/OrderItem";
function App() {
  return (
    // <BusketSideBarProvider>
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/newPerson" element={<Signup />} />
          <Route path="/signin" element={<Signin />} />
          {/* <CartProvider> */}
          <Route path="/" element={<HomePage />} />
          <Route path="/summaryOrder" element={<OrderItem />} />
          <Route
            path="/products-page/:productType"
            element={<ProductSection />}
          />
          {/* </CartProvider> */}
        </Routes>
      </BrowserRouter>
    </AuthProvider>
    // </BusketSideBarProvider>

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
