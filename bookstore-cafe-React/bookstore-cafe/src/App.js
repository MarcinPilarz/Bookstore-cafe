import React from "react";
import { BrowserRouter, Route, Router, Routes } from "react-router-dom";
import "./App.css";
import HomePage from "./components/HomePage/HomePage";
//mport {BusketSideBarProvider} from './components/SideBars/BusketSideBar';
import Signin from "./components/Login/Signin";
import Signup from "./components/Login/Signup";
import ProductSection from "./components/ProductSection/ProductSection";
import { CartProvider } from "./components/ProductSection/BusketProducts";
import { AuthProvider } from "./components/Login/LoginInfoContext";
import OrderItem from "./components/Orders/OrderItem";
import StripePayment from "./components/Orders/StripePayment";
import UserPanel from "./components/Panels/UserPanel";
import EmployeePanel from "./components/Panels/EmployeePanel";
import OwnerPanel from "./components/Panels/OwnerPanel";
function App() {
  return (
    // <BusketSideBarProvider>
    <AuthProvider>
      <BrowserRouter>
      

        <Routes>
          <Route path="/signup" element={<Signup />} />
          <Route path="/signin" element={<Signin />} />
          {/* <CartProvider> */}
          <Route path="/" element={<HomePage />} />
          <Route path="/summaryOrder" element={<OrderItem />} />
          <Route path="/stripe" element={<StripePayment />} />
          <Route path="/user-panel" element={<UserPanel/>} />
          <Route path="/employee-panel" element=< EmployeePanel/> />
          <Route path="/owner-panel" element=<OwnerPanel/>/>
          <Route
            path="/products-page/:productType"
            element={<ProductSection />}
            />
          {/* </CartProvider> */}
          {/* <Route path="/" element={<AuthProvider><HomePage /></AuthProvider>} /> */}
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
