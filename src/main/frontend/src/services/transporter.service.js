import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/transporter/";

class TransporterService {
  async getFreePackages() {
    return (await axios.get(API_URL + "packages/free", {headers: authHeader()})).data;
  }

  async createTrip(trip) {
    return axios.post(API_URL + "trip", {...trip}, {headers: authHeader()});
  }

  async getTrip(id) {
    return (await axios.get(API_URL + `trip/${id}`, {headers: authHeader()})).data;
  }

  async addPackage(tripId, cargoId) {
    return axios.put(API_URL + `trip/package?packageId=${cargoId}&tripId=${tripId}`, null, {headers: authHeader()});
  }

  async removePackage(tripId, cargoId) {
    return axios.delete(API_URL + `trip/package?packageId=${cargoId}&tripId=${tripId}`, {headers: authHeader()});
  }

  async getTrips() {
    return (await axios.get(API_URL + "trips", {headers: authHeader()})).data;
  }

  async updateCurrentCity(order, tripId) {
    return axios.put(API_URL + `trip/current-city?order=${order}&tripId=${tripId}`, null, {headers: authHeader()});
  }

  async assignRoute(route, tripId) {
    return axios.put(API_URL + "trip/route", {route: route, tripId: tripId}, {headers: authHeader()});
  }

  async startTrip(id) {
    return axios.post(API_URL + `trip/started?tripId=${id}`, null, {headers: authHeader()});
  }

  async finishTrip(id) {
    return axios.post(API_URL + `trip/finished?tripId=${id}`, null, {headers: authHeader()});
  }
}

export default new TransporterService();
