import axios from "axios";

import withSessionKey from "./auth-header";
import SessionService from "./session.service";

const API_URL = "http://localhost:8080/api/auth/";

class AuthService {
  async login(login, password) {
    const encryptedLogin = await SessionService.encrypt(login);
    const encryptedPassword = await SessionService.encrypt(password);
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
    localStorage.removeItem("publicKey")
    localStorage.removeItem("privateKey")
    localStorage.removeItem("encryptedSessionKey")
    localStorage.removeItem("sessionKey")
  }

  async register(login, password) {
    const encryptedLogin = await SessionService.encrypt(login);
    const encryptedPassword = await SessionService.encrypt(password);
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
