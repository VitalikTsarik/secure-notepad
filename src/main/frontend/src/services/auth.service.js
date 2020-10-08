import axios from "axios";

import withSessionKey from "./auth-header";
import SessionService from "./session.service";

const API_URL = "http://localhost:8080/api/auth/";

class AuthService {
  login(login, password) {
    const encryptedLogin = SessionService.encrypt(login);
    const encryptedPassword = SessionService.encrypt(password);
    return axios
      .post(API_URL + "signin", {
        login: encryptedLogin,
        password: encryptedPassword,
      }, {params: withSessionKey({})})
      .then(response => {
        if (response) {
          localStorage.setItem("user", JSON.stringify({login, password}));
        }
      });
  }

  logout() {
    localStorage.removeItem("user");
  }

  register(login, password) {
    const encryptedLogin = SessionService.encrypt(login);
    const encryptedPassword = SessionService.encrypt(password);
    return axios
      .post(API_URL + "signup", {
        login: encryptedLogin,
        password: encryptedPassword,
      }, {params: withSessionKey({})})
      .then(response => {
        if (response) {
          localStorage.setItem("user", JSON.stringify({login, password}));
        }
      });
  }

  getCurrentUser() {
    return JSON.parse(localStorage.getItem("user"));
  }
}

export default new AuthService();
