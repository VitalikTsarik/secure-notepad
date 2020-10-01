import React, { useCallback, useEffect, useState } from "react";
import Jumbotron from "react-bootstrap/Jumbotron";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

import SessionService from "../../services/session.service";

const Keys = () => {
  const [privateKey, setPrivateKey] = useState("");
  const [publicKey, setPublicKey] = useState("");

  useEffect(() => {
    (async () => {
      const publicKey = await SessionService.getPublicKey();
      const privateKey = await SessionService.getPrivateKey();
      setPublicKey(JSON.stringify(publicKey));
      setPrivateKey(JSON.stringify(privateKey));
    })();
  }, []);

  const handleClick = useCallback(async () => {
    const {publicKey, privateKey} = await SessionService.generateKeys();
    setPublicKey(JSON.stringify(publicKey));
    setPrivateKey(JSON.stringify(privateKey));
    SessionService.getSessionKey();
  }, []);

  return (
    <Container>
      <Jumbotron>
        <Form.Group controlId="exampleForm.ControlTextarea1">
          <Form.Label>Private Key</Form.Label>
          <Form.Control as="textarea" rows="5" readOnly value={privateKey}/>
        </Form.Group>
        <Form.Group controlId="exampleForm.ControlTextarea1">
          <Form.Label>Public Key</Form.Label>
          <Form.Control as="textarea" rows="3" readOnly value={publicKey}/>
        </Form.Group>
        <Button onClick={handleClick}>Generate</Button>
      </Jumbotron>
    </Container>
  );
};

export default Keys;
