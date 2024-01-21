import axios from "axios";
import React from "react";
import { useAuth } from "../Login/LoginInfoContext";
import { useCart } from "../ProductSection/BusketProducts";
import "./RoleToggle.css";
const RoleToggle = () => {
  const { authData, setAuthData } = useAuth();
  const {
    busket,
    setBusket,
    clearBusket,
    updateProductQuantity,
    removeFromBusket,
  } = useCart();
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
          headers: {
            Authorization: `Bearer ${authData?.token}`,
          },
          roleType: newRole,
        }
      );

      const updatedAuthData = { ...authData, roleType: newRole };
      setAuthData(updatedAuthData);

      localStorage.setItem("authData", JSON.stringify(updatedAuthData));

      clearBusket();
      window.location.reload();
    } catch (error) {
      console.error("Error changing role", error);
    }
  };

  return (
    <div>
      <p>Aktualna rola: {authData.roleType}</p>
      {canToggleRole() && (
        <button className="role-toggle-button" onClick={toggleRole}>
          Zmień rolę na {getNextRole()}
        </button>
      )}
    </div>
  );
};

export default RoleToggle;
