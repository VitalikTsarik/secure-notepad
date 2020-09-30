import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/cargo-owner/";

class CargoOwnerService {
  async getPackages() {
    return (await axios.get(API_URL + "packages", {headers: authHeader()})).data;
  }

  async createPackage(item) {
    return axios.post(API_URL + "package", {...item}, {headers: authHeader()});
  }

  async getPackage(id) {
    return (await axios.get(API_URL + `package/${id}`, {headers: authHeader()})).data;
  }

  async editPackage({id, ...item}) {
    return axios.put(API_URL + `package/${id}`, {...item}, {headers: authHeader()});
  }

  async removePackage(id) {
    return axios.delete(API_URL + `package/${id}`, {headers: authHeader()});
  }

  async getTrips() {
    return (await axios.get(API_URL + "trips", {headers: authHeader()})).data;
  }
}

export default new CargoOwnerService();
