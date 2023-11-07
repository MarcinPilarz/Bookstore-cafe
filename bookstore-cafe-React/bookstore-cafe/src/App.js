import React from "react";
import logo from "./logo.svg";
import "./App.css";
import Navbar from "./components/Navbar/Navbar";
import HomePage from "./components/HomePage/HomePage";
//mport {BusketSideBarProvider} from './components/SideBars/BusketSideBar';

function App() {
  return (
    <React.Fragment>
      {/* <BusketSideBarProvider> */}
      <Navbar />
      <HomePage />
      {/* </BusketSideBarProvider> */}
    </React.Fragment>
  );
}

export default App;
