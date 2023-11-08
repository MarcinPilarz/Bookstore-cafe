import React from "react";
import { Link, BrowserRouter as Router, Route, Routes } from "react-router-dom";

import HomePage from "./HomePage/HomePage";
import ReservationPage from "./ReservationPage/ReservationPage";
const AppRouters = () => {
  return (
    <Router>
      <div>
        <nav>
          <Link to="/">Strona główna</Link>
          <Link to="/reservation">Rezerwacje</Link>
        </nav>
        <Routes>
          <Route path="/reservation" element={<ReservationPage />}></Route>
          <Route path="/" element={<HomePage />}></Route>
        </Routes>
      </div>
    </Router>
  );
};

export default AppRouters;
