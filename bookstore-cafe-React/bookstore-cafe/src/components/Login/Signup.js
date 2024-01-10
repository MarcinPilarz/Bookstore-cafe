import React, { useState } from "react";
import { useHistory } from "react-router-dom";
import axios from "axios";
import "./Singup.css";
const Signup = () => {
  const [isLoginActive, setIsLoginActive] = useState(true);
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [telephonNumber, setTelephonNumber] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [repeatPassword, setRepeatPassword] = useState("");
  const [roleType, setRoleType] = useState("");

  const [registrationData, setRegistrationData] = useState({
    firstName: "",
    lastName: "",
    telephonNumber: "",
    email: "",
    password: "",
    roleType: "Klient",
  });
  const [repeatedPassword, setRepeatedPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const handleLoginClick = () => {
    // Przekierowanie do "/zaloguj"
    window.location.href = "/signin";
  };

  const handlePeopleRegister = async () => {
    try {
      console.log("Rejestracja:", {
        firstName,
        lastName,
        telephonNumber,
        email,
        password,
        roleType,
      });
      const response = await axios.post("http://localhost:8080/newPerson", {
        firstName,
        lastName,
        telephonNumber,
        email,
        password,
        roleType,
      });
      console.log("Odpowiedź po rejestracji:", response.data);
    } catch (error) {
      console.error("Błąd rejestracji:", error.message);
    }
  };

  const handleRegisterSubmit = async () => {
    if (registrationData.password !== repeatedPassword) {
      alert("Hasła nie są takie same!");
      return;
    }
    try {
      const response = await axios.post(
        "http://localhost:8080/newPerson",
        registrationData,
        {
          timeout: 10000,

          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      console.log("Odpowiedź po rejestracji:", response.data);

      // Wyczyść pola formularza po udanej rejestracji
      setRegistrationData({
        firstName: "",
        lastName: "",
        telephonNumber: "",
        email: "",
        password: "",
        roleType: "",
      });
      setRepeatedPassword("");
      console.log("Zarejestrowano pomyślnie");
    } catch (error) {
      console.error("Błąd rejestracji:", error.message);
      console.error("Szczegóły błędu:", error.stack);

      if (axios.isAxiosError(error)) {
        console.error("Szczegóły błędu:", error.toJSON());
      } else if (error.code === "ECONNABORTED") {
        console.error("Żądanie zostało przerwane z powodu timeout'u.");
      } else if (error.response) {
        console.error("Status błędu:", error.response.status);
        console.error("Treść błędu:", error.response.data);
      } else if (error.request) {
        console.error("Nie otrzymano odpowiedzi na żądanie", error.request);
      } else {
        console.error("Błąd ustawiania żądania:", error.message);
      }
    }
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setRegistrationData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  return (
    <section className="singup-section">
      <div className="singup-container">
        <header className="header-login">
          <h1>
            <span> CZYTAJ Z KAWĄ </span>
            <span>ZAREJESTRUJ SIĘ</span>
            <span> I DOŁĄCZ DO NAS</span>
            <span>:)</span>
          </h1>
        </header>
        <div className="toggle-buttons">
          <button
            className={isLoginActive ? "active" : ""}
            onClick={() => setIsLoginActive(true)}
          >
            Rejestracja
          </button>
          <button
            className={!isLoginActive ? "active" : ""}
            onClick={() => {
              setIsLoginActive(false);
              handleLoginClick();
            }}
          >
            Zaloguj
          </button>
        </div>

        <div className="div-form-container">
          {/* <div className="logo-login">
          <img
            src="https://storage.googleapis.com/springbootphoto/springbootphoto/Czytaj%20z%20Kaw%C4%85%20logo.png"
            alt="Company Logo"
          />
        </div> */}
          <form className="form-container ">
            <div>
              <label>
                <input
                  className="input-login"
                  type="text"
                  name="firstName"
                  placeholder="Imie"
                  value={registrationData.firstName}
                  onChange={handleInputChange}
                />
              </label>

              <label>
                <input
                  className="input-login"
                  type="text"
                  name="lastName"
                  placeholder="Nazwisko"
                  value={registrationData.lastName}
                  onChange={handleInputChange}
                />
              </label>

              {/* <label>
                <input
                  className="input-login"
                  type="text"
                  name="roleType"
                  placeholder="Rola"
                  value={registrationData.roleType}
                  onChange={handleInputChange}
                />
              </label> */}

              <label>
                <br />

                <input
                  className="input-login"
                  type="tel"
                  pattern="[0-9]{9}"
                  maxLength="9"
                  placeholder="Numer telefonu"
                  onKeyPress={(e) => {
                    const onlyDigits = /^[0-9\b]+$/;
                    if (!onlyDigits.test(e.key)) {
                      e.preventDefault();
                    }
                  }}
                  name="telephonNumber"
                  value={registrationData.telephonNumber}
                  onChange={handleInputChange}
                />
              </label>

              <label>
                <br />

                <input
                  className="input-login"
                  type="email"
                  name="email"
                  pattern="[^@\s]+@[^@\s]+\.[^@\s]+"
                  placeholder="E-mail"
                  title="Podaj poprawny adres email."
                  value={registrationData.email}
                  onChange={handleInputChange}
                />
              </label>

              <label>
                <input
                  className="input-login"
                  type={showPassword ? "text" : "password"}
                  name="password"
                  placeholder="Hasło"
                  value={registrationData.password}
                  onChange={handleInputChange}
                />
              </label>

              <label>
                <input
                  className="input-login"
                  type={showPassword ? "text" : "password"}
                  placeholder="Powtórz hasło"
                  value={repeatedPassword}
                  onChange={(e) => setRepeatedPassword(e.target.value)}
                />
              </label>
              <button
                type="button"
                className="show-hide-password-button"
                onClick={() => setShowPassword(!showPassword)}
              >
                {showPassword ? "Ukryj Hasło" : "Pokaż Hasło"}
              </button>
              <button
                type="button"
                className="singup-button"
                onClick={handleRegisterSubmit}
              >
                Zarejestruj się
              </button>
            </div>
          </form>
        </div>
      </div>
    </section>
  );
};

export default Signup;
