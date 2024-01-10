import React, { useState, useEffect, useContext, createContext } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./Signin.css";
//import { useHistory } from "react-router-dom";
import { useAuth } from "./LoginInfoContext";
import HomePage from "../HomePage/HomePage";
// const AuthContext = React.createContext();
const Signin = () => {
  const { authData, setAuthData } = useAuth();
  const [isLoginActive, setIsLoginActive] = useState(true);
  const navigate = useNavigate();
  // const [email, setEmail] = useState("");
  // const [password, setPassword] = useState("");
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
  //const history = useHistory();
  const handleRegisterClick = () => {
    // Przekierowanie do "/rejestruj"
    window.location.href = "/signup";
  };
  const [showResetPasswordForm, setShowResetPasswordForm] = useState(false);
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
      // 60 * 60 * 1000; // 15 minut w milisekundach
      //const expirationTime = new Date().getTime() + 10 * 60; // 15 minut w milisekundach

      // Ustawienie danych uwierzytelniających w kontekście i localStorage
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
      setAuthData(newAuthData); // Aktualizacja stanu w kontekście

      // Ustawienie domyślnego nagłówka autoryzacji dla wszystkich żądań axios
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
    // Przekazywanie tokenów do innego komponentu
    if (token && refreshToken) {
      console.log("Przekazuję tokeny do innego komponentu");
      console.log(token);

      console.log(refreshToken);
      // Tutaj przekazuj tokeny do innych komponentów
    }
  }, [token, refreshToken]);
  // useEffect(() => {
  //   // Tutaj możesz umieścić logikę sprawdzającą, czy użytkownik jest zalogowany
  //   // np. jeśli masz token w localStorage lub innym miejscu
  //   const storedToken = localStorage.getItem("token");
  //   const storedRefreshToken = localStorage.getItem("refreshToken");

  //   if (storedToken && storedRefreshToken) {
  //     setToken(storedToken);
  //     setRefreshToken(storedRefreshToken);
  //     console.log("Użytkownik jest już zalogowany");
  //     // Przekieruj do HomePage lub innej ścieżki po zalogowaniu
  //     history.push("/");
  //   }
  // }, []); // Pusta tablica oznacza, że useEffect zostanie uruchomiony tylko raz po zamontowaniu komponentu

  // const useAuth = () => {
  //   const context = useContext(AuthContext);
  //   if (!context) {
  //     throw new Error("useAuth must be used within an AuthProvider");
  //   }
  //   return context;
  // };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setLoginData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };
  // const handleRefreshToken = async () => {
  //   try {
  //     const response = await axios.post(`${API_URL}/refresh`, {
  //       refreshToken,
  //     });
  //     setToken(response.data.token);
  //     console.log("Token odświeżony pomyślnie");
  //   } catch (error) {
  //     console.error("Błąd odświeżania tokenu:", error.message);
  //   }
  // };

  // const handleProtectedResource = async () => {
  //   try {
  //     const response = await axios.get(`${API_URL}/sayAdmin`, {
  //       headers: {
  //         Authorization: `Bearer ${token}`,
  //       },
  //     });
  //     console.log(response.data);
  //   } catch (error) {
  //     console.error("Błąd dostępu do chronionego zasobu:", error.message);
  //   }
  // };

  const handleForgotPasswordClick = (e) => {
    e.preventDefault();
    setShowResetPasswordForm(true);
  };

  const handleResetPassword = async () => {
    if (!loginData.email) {
      console.error("Adres email jest niezdefiniowany");
      return;
    }

    try {
      await axios.post("http://localhost:8080/reset-password", {
        email: loginData.email,
      });
      console.log(
        "Instrukcje dotyczące resetowania hasła zostały wysłane na email."
      );
      setShowResetPasswordForm(false);
    } catch (error) {
      console.error("Błąd podczas resetowania hasła", error);
    }
  };
  return (
    // <AuthContext.Provider
    //   value={{
    //     token,
    //     refreshToken,
    //     firstName,
    //     lastName,
    //     phoneNumber,
    //     handleLogin,
    //   }}
    // >
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
          {/* <div className="logo-login">
            <img
              src="https://storage.googleapis.com/springbootphoto/springbootphoto/Czytaj%20z%20Kaw%C4%85%20logo.png"
              alt="Company Logo"
            />
          </div> */}
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
                  type="password"
                  name="password"
                  placeholder="Hasło"
                  value={loginData.password}
                  onChange={handleInputChange}
                  required
                />
              </label>

              <button
                type="button"
                onClick={handleLogin}
                className="singup-button"
              >
                Zaloguj się
              </button>
              {showResetPasswordForm && (
                <div className="reset-password-form">
                  <input
                    className="input-login"
                    type="email"
                    name="email"
                    placeholder="E-mail"
                    value={loginData.email}
                    onChange={handleInputChange}
                    required
                  />
                  <button
                    onClick={handleResetPassword}
                    className="reset-password-button"
                  >
                    Wyślij link resetujący hasło
                  </button>
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
    // </AuthContext.Provider>
  );
};

export default Signin;
