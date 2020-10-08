export default function withSessionKey(data) {
  const encryptedSessionKey = JSON.parse(localStorage.getItem("encryptedSessionKey"));
  if (encryptedSessionKey) {
    return {...data, encryptedSessionKey};
  } else {
    return data;
  }
}
