import axios from "axios";

import withSessionKey from "./auth-header";

const API_URL = "http://localhost:8080/api/auth/";

class AuthService {
  login(login, password) {
    return axios
      .post(API_URL + "signin", {
        login,
        password,
      }, {params: withSessionKey({})})
      .then(response => {
        if (response) {
          localStorage.setItem("user", JSON.stringify({login, password}));
        }
        return response.data;
      });
  }

  logout() {
    localStorage.removeItem("user");
  }

  register(login, password) {
    return axios.post(API_URL + "signup", {
      login,
      password,
    }, {params: withSessionKey({})});
  }

  getCurrentUser() {
    return JSON.parse(localStorage.getItem("user"));
  }
}

export default new AuthService();
