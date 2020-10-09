import axios from "axios";
import { ModeOfOperation, utils } from "aes-js";
import { Base64 } from "js-base64";
import { JSEncrypt } from "./lib/jsencrypt";

const API_URL = "http://localhost:8080/api/";
const KEY_SIZE = 1024;
const IV = [21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36];

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

  getPrivateKey() {
    return JSON.parse(localStorage.getItem("privateKey"));
  }

  getPublicKey() {
    return JSON.parse(localStorage.getItem("publicKey"));
  }

  getCurrentSessionKey() {
    return JSON.parse(localStorage.getItem("sessionKey"));
  }

  decryptRsa(message) {
    const crypt = new JSEncrypt();
    crypt.setPrivateKey(this.getPrivateKey());
    return crypt.decrypt(message);
  }

  async getSessionKey(update = false) {
    let sessionKey = JSON.parse(localStorage.getItem("sessionKey"));
    if (!Boolean(sessionKey) || update) {
      sessionKey = await axios
        .post(API_URL + "rsa", {
          openRSAkey: this.getPublicKey(),
        })
        .then(response => {
          const encryptedSessionKey = response.data.encryptedSessionKey;
          if (encryptedSessionKey) {
            localStorage.setItem("encryptedSessionKey", JSON.stringify(encryptedSessionKey));
            return this.decryptRsa(encryptedSessionKey);
          }
        }).then((key) => {
          sessionKey = key;
          localStorage.setItem("sessionKey", JSON.stringify(sessionKey));
        });
    }
    return sessionKey;
  }

  async decrypt(message) {
    const sessionKey = await this.getSessionKey();
    const sessionKeyBytes = Base64.toUint8Array(sessionKey);
    const messageBytes = Base64.toUint8Array(message);
    const aesOfb = new ModeOfOperation.ofb(sessionKeyBytes, IV);
    const decrypted = aesOfb.decrypt(messageBytes);
    return Base64.decode(Base64.fromUint8Array(decrypted));
  }

  async encrypt(message) {
    const sessionKey = await this.getSessionKey();
    const sessionKeyBytes = Base64.toUint8Array(sessionKey);
    const messageBytes = Base64.toUint8Array(Base64.encode(message));
    const aesOfb = new ModeOfOperation.ofb(sessionKeyBytes, IV);
    const encryptedBytes = aesOfb.encrypt(messageBytes);
    return Base64.fromUint8Array(encryptedBytes);
  }
}

export default new SessionService();
