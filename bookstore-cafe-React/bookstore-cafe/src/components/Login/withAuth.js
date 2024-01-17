import React from "react";
import { useAuth } from "./LoginInfoContext"; // Zaimportuj swój hook
import { Navigate } from "react-router-dom"; // Zaimportuj Redirect z react-router

const withAuth = (WrappedComponent, allowedRoles) => {
  return (props) => {
    const { authData } = useAuth();

    if (!authData.token || !allowedRoles.includes(authData.roleType)) {
      return <Navigate to="/" replace />;
    }

    return <WrappedComponent {...props} />;
  };
};

export default withAuth;
