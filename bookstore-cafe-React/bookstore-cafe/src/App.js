import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import HomePage from "./components/HomePage/HomePage";
//mport {BusketSideBarProvider} from './components/SideBars/BusketSideBar';
import { Signin } from "./components/Login/Signin";
import Signup from "./components/Login/Signup";
import ProductSection from "./components/ProductSection/ProductSection";
function App() {
  return (
    // <BusketSideBarProvider>
    <BrowserRouter>
      <Routes>
        <Route path="/newPerson" element={<Signup />} />
        <Route path="/signin" element={<Signin />} />
        <Route path="/" element={<HomePage />} />
        <Route
          path="/products-page/:productType"
          element={<ProductSection />}
        />
      </Routes>
    </BrowserRouter>
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
