import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import HomePage from "./components/HomePage/HomePage";

import { AuthProvider } from "./components/Login/LoginInfoContext";
import Signin from "./components/Login/Signin";
import Signup from "./components/Login/Signup";
import OrderItem from "./components/Orders/OrderItem";
import StripePayment from "./components/Orders/StripePayment";
import EmployeePanel from "./components/Panels/EmployeePanel";
import OwnerPanel from "./components/Panels/OwnerPanel";
import UserPanel from "./components/Panels/UserPanel";
import ProductSection from "./components/ProductSection/ProductSection";
function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/signup" element={<Signup />} />
          <Route path="/signin" element={<Signin />} />

          <Route path="/" element={<HomePage />} />
          <Route path="/summaryOrder" element={<OrderItem />} />
          <Route path="/stripe" element={<StripePayment />} />
          <Route path="/user-panel" element={<UserPanel />} />
          <Route path="/employee-panel" element={<EmployeePanel />} />
          <Route path="/owner-panel" element={<OwnerPanel />} />
          <Route
            path="/products-page/:productType"
            element={<ProductSection />}
          />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
