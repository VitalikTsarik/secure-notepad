import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

class AuthService {
  login(login, password) {
    return axios
      .post(API_URL + "signin", {
        login,
        password,
      })
      .then(response => {
        if (response.data.token) {
          localStorage.setItem("user", JSON.stringify(response.data));
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
    });
  }

  getCurrentUser() {
    return JSON.parse(localStorage.getItem("user"));
  }
}

export default new AuthService();
