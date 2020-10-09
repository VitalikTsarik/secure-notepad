import React, { useCallback, useEffect, useState } from "react";
import Jumbotron from "react-bootstrap/Jumbotron";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

import SessionService from "../../services/session.service";
import AuthService from "../../services/auth.service";

const Keys = () => {
  const [privateKey, setPrivateKey] = useState("");
  const [publicKey, setPublicKey] = useState("");

  useEffect(() => {
    (async () => {
      const publicKey = SessionService.getPublicKey();
      const privateKey = SessionService.getPrivateKey();
      setPublicKey(publicKey);
      setPrivateKey(privateKey);
    })();
  }, []);

  const handleClick = useCallback(async () => {
    AuthService.logout()
    const {publicKey, privateKey} = await SessionService.generateKeys();
    setPublicKey(publicKey);
    setPrivateKey(privateKey);
    await SessionService.getSessionKey(true);
    window.location.reload();
  }, []);

  return (
    <Container>
      <Jumbotron>
        <Form.Group controlId="exampleForm.ControlTextarea1">
          <Form.Label>Private Key</Form.Label>
          <Form.Control as="textarea" rows="15" readOnly value={privateKey}/>
        </Form.Group>
        <Form.Group controlId="exampleForm.ControlTextarea1">
          <Form.Label>Public Key</Form.Label>
          <Form.Control as="textarea" rows="6" readOnly value={publicKey}/>
        </Form.Group>
        <Button onClick={handleClick}>Generate</Button>
      </Jumbotron>
    </Container>
  );
};

export default Keys;
