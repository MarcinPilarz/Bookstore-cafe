import React from "react";
import { useAuth } from "./LoginInfoContext";
import { Navigate } from "react-router-dom";

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
