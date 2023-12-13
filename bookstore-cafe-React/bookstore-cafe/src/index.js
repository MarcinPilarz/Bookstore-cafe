import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import BusketSideBarProvider from "./components/SideBars/BusketSideBar";
import { CartProvider } from "./components/ProductSection/BusketProducts";
import { Signin } from "./components/Login/Signin";
import { AuthProvider } from "./components/Login/LoginInfoContext";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <AuthProvider>
    <CartProvider>
      <BusketSideBarProvider>
        <React.StrictMode>
          <App />
        </React.StrictMode>
      </BusketSideBarProvider>
    </CartProvider>
  </AuthProvider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
