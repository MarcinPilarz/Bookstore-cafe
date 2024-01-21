import React, { createContext, useContext, useEffect, useState } from "react";

import axios from "axios";

const LoginInfoContext = createContext();

const AuthProvider = ({ children }) => {
  const [authData, setAuthData] = useState(() => {
    const storedAuthData = localStorage.getItem("authData");
    if (storedAuthData) {
      const parsedAuthData = JSON.parse(storedAuthData);
      if (
        parsedAuthData.expirationTime &&
        parsedAuthData.expirationTime > new Date().getTime()
      ) {
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
      email: null,
      roleType: null,
    };
  });

  useEffect(() => {
    if (authData?.token) {
      axios.defaults.headers.common[
        "Authorization"
      ] = `Bearer ${authData?.token}`;
      console.log("dsadasdasdadad", authData?.token);
    }
  }, [authData?.token]);

  const logout = () => {
    if (authData && authData?.idPerson) {
      const busketKey = `busket_${authData?.idPerson}`;
      localStorage.removeItem(busketKey);
    }

    setAuthData(null);
    localStorage.removeItem("authData");
  };
  return (
    <LoginInfoContext.Provider value={{ authData, setAuthData, logout }}>
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

export { AuthProvider, LoginInfoContext, useAuth };
