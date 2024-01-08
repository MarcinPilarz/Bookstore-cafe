import React, { useState } from "react";
import { useAuth } from "../Login/LoginInfoContext";
import axios from "axios";
import "./RoleToggle.css";
const RoleToggle = () => {
  const { authData, setAuthData } = useAuth();

  const getNextRole = () => {
    switch (authData.roleType) {
      case "Wlasciciel":
        return "Klient";
      case "Pracownik":
        return authData.email.includes("wlasciciel") ? "Wlasciciel" : "Klient";
      case "Klient":
        return authData.email.includes("wlasciciel")
          ? "Wlasciciel"
          : "Pracownik";
      default:
        return "Wlasciciel";
    }
  };

  const canToggleRole = () => {
    return (
      authData.email.includes("pracownik") ||
      authData.email.includes("wlasciciel")
    );
  };

  const toggleRole = async () => {
    if (!canToggleRole()) return;

    const newRole = getNextRole();
    try {
      const response = await axios.put(
        `http://localhost:8080/updateRoleType?id=${authData.idPerson}`,
        {
          roleType: newRole,
        }
      );

      const updatedAuthData = { ...authData, roleType: newRole };
      setAuthData(updatedAuthData);
      //   setAuthData({ ...authData, roleType: newRole });

      // Opcjonalnie: odświeżenie strony
      //   window.location.reload();

      localStorage.setItem("authData", JSON.stringify(updatedAuthData));

      // Opcjonalnie: odświeżenie strony
      window.location.reload();
    } catch (error) {
      console.error("Error changing role", error);
    }
  };

  return (
    <div>
      <p>Aktualna rola: {authData.roleType}</p>
      {canToggleRole() && (
        <button className="role-toggle-button" onClick={toggleRole}>Zmień rolę na {getNextRole()}</button>
      )}
    </div>
  );
};

export default RoleToggle;
