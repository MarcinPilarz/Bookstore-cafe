
import React from 'react';
import logo from './logo.svg';
import './App.css';
import Navbar from './components/Navbar/Navbar';
//mport {BusketSideBarProvider} from './components/SideBars/BusketSideBar';

function App() {
  return (
    <React.Fragment>
      {/* <BusketSideBarProvider> */}
    <Navbar/>
    {/* </BusketSideBarProvider> */}
  </React.Fragment>
  );
}

export default App;
