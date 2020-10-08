import axios from "axios";

import withSessionKey from "./auth-header";
import SessionService from "./session.service";

const API_URL = "http://localhost:8080/api/";

class TextService {
  async createText(text) {
    const encryptedText = await SessionService.encrypt(text);
    return axios
      .post(API_URL + "text", {
        encryptedText: encryptedText
      }, {params: withSessionKey({})})
      .then(response => {
        return response.data;
      });
  }

  async getTextById(textId) {
    return axios
      .post(API_URL + "text", {
        textId: textId,
      }, {params: withSessionKey({})})
      .then(response => {
        return SessionService.decrypt(response.data);
      });
  }

  async editText(textId) {
    return axios
      .put(API_URL + "text", {
        textId: textId
      }, {params: withSessionKey({})});
  }

  async removeText(textId) {
    return axios
      .delete(API_URL + "text", {
        params: withSessionKey({
          textId: textId
        })
      });
  }

  async getTexts(textId) {
    return axios
      .get(API_URL + "texts", {params: withSessionKey({})})
      .then(response => {
        const encryptedTexts = response.data;
        return encryptedTexts.map((text) => SessionService.decrypt(text));
      });
  }
}

export default new TextService();
