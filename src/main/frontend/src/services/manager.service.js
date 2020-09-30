import axios from "axios";

import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/manager/";

class ManagerService {
  async getPackages() {
    return (await axios.get(API_URL + "packages", {headers: authHeader()})).data;
  }

  async getTrips() {
    return (await axios.get(API_URL + "trips", {headers: authHeader()})).data;
  }
}

export default new ManagerService();
