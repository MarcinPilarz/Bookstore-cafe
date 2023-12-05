import React, { useState, createContext } from "react";

export const BusketSideBar = createContext();

const BusketSideBarProvider = ({ children }) => {
  const [isOpen, setIsOpen] = useState(false);

  const handleClose = () => {
    setIsOpen(false);
  };

  return (
    <BusketSideBar.Provider value={{ isOpen, setIsOpen, handleClose }}>
      {children}
    </BusketSideBar.Provider>
  );
};

export default BusketSideBarProvider;
