import React, { useState, useEffect, useContext, createContext } from "react";
import axios from "axios";
import "./Signin.css";
//import { useHistory } from "react-router-dom";
const AuthContext = React.createContext();
const Signin = ({ children }) => {
  const [isLoginActive, setIsLoginActive] = useState(true);

  // const [email, setEmail] = useState("");
  // const [password, setPassword] = useState("");
  const [token, setToken] = useState(null);
  const [refreshToken, setRefreshToken] = useState(null);
  const [LoginData, setLoginData] = useState({
    email: "",
    password: "",
  });
  //const history = useHistory();
  const handleRegisterClick = () => {
    // Przekierowanie do "/rejestruj"
    window.location.href = "/signup";
  };

  const handleLogin = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8080/signin",
        LoginData,
        {
          timeout: 10000,

          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      setToken(response.data.token);
      setRefreshToken(response.data.refreshToken);
      console.log("Zalogowano pomyślnie");
      //console.log(response.token);

      console.log("token", token);
      console.log("rtoken", refreshToken);
      setLoginData({
        email: "",
        password: "",
      });
    } catch (error) {
      console.error("Błąd logowania:", error.message);
    }
  };

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

  const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
      throw new Error("useAuth must be used within an AuthProvider");
    }
    return context;
  };

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
                  value={LoginData.email}
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
                  value={LoginData.password}
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
            </div>
          </form>
        </div>
      </div>
    </section>
  );
};

export { Signin, AuthContext };
