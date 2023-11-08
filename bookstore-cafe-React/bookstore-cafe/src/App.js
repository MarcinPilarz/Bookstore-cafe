import React from "react";
import logo from "./logo.svg";
import "./App.css";
import Navbar from "./components/Navbar/Navbar";
import HomePage from "./components/HomePage/HomePage";
//mport {BusketSideBarProvider} from './components/SideBars/BusketSideBar';
import AppRouters from "./components/AppRouters";
import MainPhoto from "./components/HomePage/MainPhoto";
import Footer from "./components/Footer/Footer";
function App() {
  return (
    <React.Fragment>
      {/* <BusketSideBarProvider> */}
      <Navbar />
      <MainPhoto />
      <HomePage />
      {/* <AppRouters /> */}
      {/* </BusketSideBarProvider> */}
      <Footer />
    </React.Fragment>
  );
}

export default App;
