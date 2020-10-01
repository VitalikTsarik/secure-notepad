import axios from "axios";
import { decrypt, generateKey } from "js-crypto-rsa";
import { ModeOfOperation, utils } from "aes-js";

const API_URL = "http://localhost:8080/api/";
const KEY_SIZE = 256;

class SessionService {
  async generateKeys() {
    const keys = await generateKey(KEY_SIZE);
    const publicKey = keys.publicKey;
    const privateKey = keys.privateKey;
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
    const decoder = new TextDecoder("utf-8");
    const privateKey = await this.getPrivateKey();
    return decoder.decode(await decrypt(message, privateKey));
  }

  async getSessionKey() {
    let sessionKey = JSON.parse(localStorage.getItem("sessionKey"));
    if (!Boolean(sessionKey)) {
      sessionKey = await axios
        .get(API_URL + "sessionKey", {
          data: {
            publicKey: await this.getPublicKey(),
          }
        })
        .then(response => {
          if (response.data.sessionKey) {
            const encodedSessionKey = response.data;
            return this.decrypt(encodedSessionKey);
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
    return utils.hex.fromBytes(encryptedBytes);
  }
}

export default new SessionService();
