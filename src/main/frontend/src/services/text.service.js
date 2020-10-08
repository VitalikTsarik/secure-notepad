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
      .get(API_URL + "text", {
        params: withSessionKey({
          textId: textId,
        })
      })
      .then(response => {
        const text = response.data;
        return SessionService.decrypt(text);
      });
  }

  async editText(textId, text) {
    const encryptedText = await SessionService.encrypt(text);
    return axios
      .put(API_URL + "text", {
        id: textId,
        encryptedText: encryptedText,
      }, {params: withSessionKey({})});
  }

  async removeText(textId) {
    return axios
      .delete(API_URL + "text", {
        params: withSessionKey({
          textId: textId,
        })
      });
  }

  async getTexts() {
    return axios
      .get(API_URL + "texts", {params: withSessionKey({})})
      .then(response => {
        return response.data.texts;
      });
  }
}

export default new TextService();
