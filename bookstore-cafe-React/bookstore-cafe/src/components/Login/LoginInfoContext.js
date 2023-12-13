import React, { createContext, useContext, useState, useEffect } from "react";

import axios from "axios";
const LoginInfoContext = createContext();
const AuthProvider = ({ children }) => {
  //   const [authData, setAuthData] = useState({
  //     token: null,
  //     refreshToken: null,
  //     firstName: null,
  //     lastName: null,
  //     phoneNumber: null,
  //   });
  //   console.log("LoginInfoContext:", authData);

  const [authData, setAuthData] = useState(() => {
    // Pobranie danych z localStorage przy pierwszym renderowaniu
    const storedAuthData = localStorage.getItem("authData");
    if (storedAuthData) {
      const parsedAuthData = JSON.parse(storedAuthData);
      if (
        parsedAuthData.expirationTime &&
        parsedAuthData.expirationTime > new Date().getTime()
      ) {
        // Dane są ważne
        return parsedAuthData;
      }
    }
    return {
      token: null,
      refreshToken: null,
      firstName: null,
      lastName: null,
      phoneNumber: null,
      idPerson: null,
      expirationTime: null,
    };
  });

  useEffect(() => {
    // Aktualizacja nagłówka axios po odświeżeniu strony
    if (authData.token != null) {
      axios.defaults.headers.common[
        "Authorization"
      ] = `Bearer ${authData.token}`;
      console.log("dsadasdasdadad", authData.token);
    }
  }, [authData.token]);

  //   useEffect(() => {
  //     // Aktualizacja nagłówka axios po odświeżeniu strony
  //     axios.defaults.headers.common["Authorization"] = `Bearer ${authData.token}`;
  //   }, [authData.token]);
  return (
    <LoginInfoContext.Provider value={{ authData, setAuthData }}>
      {children}
    </LoginInfoContext.Provider>
  );
};

const useAuth = () => {
  const context = useContext(LoginInfoContext);

  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};

export { AuthProvider, useAuth, LoginInfoContext };
