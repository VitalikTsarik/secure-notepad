import axios from "axios";
import { ModeOfOperation, utils } from "aes-js";
import { Base64 } from "js-base64";
import { JSEncrypt } from "./lib/jsencrypt";

const API_URL = "http://localhost:8080/api/";
const KEY_SIZE = 1024;

class SessionService {
  async generateKeys() {
    const crypt = new JSEncrypt({default_key_size: KEY_SIZE});
    crypt.getKey();
    const privateKey = crypt.getPrivateKey();
    const publicKey = crypt.getPublicKey();
    localStorage.setItem("privateKey", JSON.stringify(privateKey));
    localStorage.setItem("publicKey", JSON.stringify(publicKey));
    return {publicKey, privateKey};
  }

  async getPrivateKey() {
    return JSON.parse(localStorage.getItem("privateKey"));
  }

  async getPublicKey() {
    return JSON.parse(localStorage.getItem("publicKey"));
  }

  async decrypt(message) {
    const crypt = new JSEncrypt();
    crypt.setPrivateKey(await this.getPrivateKey());
    return crypt.decrypt(message);
  }

  async getSessionKey() {
    let sessionKey = JSON.parse(localStorage.getItem("sessionKey"));
    if (!Boolean(sessionKey)) {
      sessionKey = await axios
        .post(API_URL + "rsa", {
          openRSAkey: await this.getPublicKey(),
        })
        .then(response => {
          if (response.data.sessionKey) {
            const encryptedSessionKey = response.data;
            localStorage.setItem("encryptedSessionKey", JSON.stringify(encryptedSessionKey));
            return this.decrypt(encryptedSessionKey);
          }
        }).then((key) => {
          sessionKey = key;
          localStorage.setItem("sessionKey", JSON.stringify(sessionKey));
        });
    }
    const encoder = new TextEncoder();
    return encoder.encode(sessionKey);
  }

  async encrypt(message) {
    const sessionKey = await this.getSessionKey();
    const messageBytes = utils.utf8.toBytes(message);
    const iv = [];
    const aesOfb = new ModeOfOperation.ofb(sessionKey, iv);
    const encryptedBytes = aesOfb.encrypt(messageBytes);
    return Base64.encode(encryptedBytes);
  }
}

export default new SessionService();
