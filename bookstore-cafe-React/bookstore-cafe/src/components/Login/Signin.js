import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Signin.css";

import { useAuth } from "./LoginInfoContext";

const Signin = () => {
  const { authData, setAuthData } = useAuth();
  const [isLoginActive, setIsLoginActive] = useState(true);
  const navigate = useNavigate();

  const [token, setToken] = useState(null);
  const [refreshToken, setRefreshToken] = useState(null);
  const [role, setRole] = useState(null);
  const [firstName, setFirstName] = useState(null);
  const [lastName, setLastName] = useState(null);
  const [phoneNumber, setPhoneNumber] = useState(null);
  const [loginData, setLoginData] = useState({
    email: "",
    password: "",
  });
  const [showPassword, setShowPassword] = useState(false);

  const handleRegisterClick = () => {
    window.location.href = "/signup";
  };
  const [showResetPasswordForm, setShowResetPasswordForm] = useState(false);
  const [newPassword, setNewPassword] = useState("");
  const handleLogin = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8080/signin",
        loginData,
        {
          timeout: 10000,
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      const expirationTime = new Date().getTime() + 604800000;

      const newAuthData = {
        token: response.data.token,
        refreshToken: response.data.refreshToken,
        firstName: response.data.firstName,
        lastName: response.data.lastName,
        phoneNumber: response.data.phoneNumber,
        idPerson: response.data.idPerson,
        roleType: response.data.roleType,
        email: response.data.email,
        expirationTime: expirationTime,
      };

      localStorage.setItem("authData", JSON.stringify(newAuthData));
      setAuthData(newAuthData);

      axios.defaults.headers.common[
        "Authorization"
      ] = `Bearer ${response.data.token}`;

      navigate("/");
    } catch (error) {
      console.error("Błąd logowania:", error.message);
    }
  };
  console.log("po handleLogin", authData);

  useEffect(() => {
    if (token && refreshToken) {
      console.log("Przekazuję tokeny do innego komponentu");
      console.log(token);

      console.log(refreshToken);
    }
  }, [token, refreshToken]);

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setLoginData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleForgotPasswordClick = (e) => {
    e.preventDefault();
    setShowResetPasswordForm(true);
  };

  const handleResetPassword = async () => {
    if (!loginData.email || !newPassword) {
      console.error("Adres email lub nowe hasło jest niezdefiniowane");
      return;
    }

    try {
      await axios.put(
        `http://localhost:8080/reset-password?email=${loginData.email}&newPassword=${newPassword}`,
        {
          email: loginData.email,
          newPassword: newPassword,
        }
      );
      console.log("Hasło zostało zresetowane.");
      setShowResetPasswordForm(false);
    } catch (error) {
      console.error("Błąd podczas resetowania hasła", error);
    }
  };

  const handleCloseModal = () => {
    setShowResetPasswordForm(false);
  };
  return (
    <section className="singup-section">
      <div className="singup-container">
        <header className="header-login">
          <h1 className="h1-signin">
            <span> CZYTAJ Z KAWĄ </span>
            <span>ZALOGUJ SIĘ</span>
            <span> I PRZEGLĄDAJ OFERTĘ</span>
            <span>:)</span>
          </h1>
        </header>
        <div className="toggle-buttons-login">
          <button
            className={!isLoginActive ? "active" : ""}
            onClick={() => {
              setIsLoginActive(false);
              handleRegisterClick();
            }}
          >
            Rejestracja
          </button>
          <button
            className={isLoginActive ? "active" : ""}
            onClick={() => setIsLoginActive(true)}
          >
            Zaloguj
          </button>
        </div>

        <div>
          <form className="form-container-login ">
            <div>
              <label>
                <br />

                <input
                  className="input-login"
                  type="email"
                  name="email"
                  pattern="[^@\s]+@[^@\s]+\.[^@\s]+"
                  placeholder="E-mail"
                  title="Podaj poprawny adres email."
                  value={loginData.email}
                  onChange={handleInputChange}
                  required
                />
              </label>

              <label>
                <input
                  className="input-login"
                  type={showPassword ? "text" : "password"}
                  name="password"
                  placeholder="Hasło"
                  value={loginData.password}
                  onChange={handleInputChange}
                  required
                />
              </label>
              <button
                type="button"
                className="show-hide-password-button-signin"
                onClick={() => setShowPassword(!showPassword)}
              >
                {showPassword ? "Ukryj Hasło" : "Pokaż Hasło"}
              </button>
              <button
                type="button"
                onClick={handleLogin}
                className="singup-button"
              >
                Zaloguj się
              </button>
              {showResetPasswordForm && (
                <div className="overlay" onClick={handleCloseModal}>
                  <div
                    className="reset-password-form"
                    onClick={(e) => e.stopPropagation()}
                  >
                    <input
                      className="input-login input-reset-password"
                      type="email"
                      name="email"
                      placeholder="E-mail"
                      value={loginData.email}
                      onChange={handleInputChange}
                      required
                    />
                    <input
                      className="input-login input-reset-password"
                      type="password"
                      name="newPassword"
                      placeholder="Nowe hasło"
                      value={newPassword}
                      onChange={(e) => setNewPassword(e.target.value)}
                      required
                    />
                    <button
                      onClick={handleResetPassword}
                      className="reset-password-button"
                    >
                      Zmień hasło
                    </button>
                  </div>
                </div>
              )}

              <div className="forgot-password">
                <a href="#" onClick={handleForgotPasswordClick}>
                  Zapomniałem hasła
                </a>
              </div>
            </div>
          </form>
        </div>
      </div>
    </section>
  );
};

export default Signin;
