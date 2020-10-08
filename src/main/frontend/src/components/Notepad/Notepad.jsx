import React, { useCallback, useEffect, useState } from "react";
import Jumbotron from "react-bootstrap/Jumbotron";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

import TextService from "../../services/text.service";

const Notepad = () => {
  const [text, setText] = useState('');
  const [textId, setTextId] = useState(1);
  const [files, setFiles] = useState(['Select text']);

  useEffect(() => {
    (async () => {
      const files = await TextService.getTexts();
      setFiles(files);
      setFiles(["sdsdd", "ssdsdds", "sddsdsdds"]);
    })();
  }, []);

  useEffect(() => {
    (async () => {
      const text = await TextService.getTextById(textId);
      setText(text);
    })();
  }, []);

  const handleFileChange = useCallback((e) => {
    setTextId(e.target.value);
  }, []);
  const handleTextChange = useCallback((e) => {
    setText(e.target.value);
  }, []);
  const handleSave = useCallback(() => {
    TextService.editText(textId, text);
  }, [text]);
  const handleDelete = useCallback(async () => {
    await TextService.removeText(textId);
    setText('')
  }, []);

  return (
    <Container>
      <Jumbotron>
        <Form.Group controlId="exampleForm.ControlSelect1">
          <Form.Label>Select file</Form.Label>
          <Form.Control as="select" onChange={handleFileChange}>
            {files.map((file) => (<option key={file}>{file}</option>))}
          </Form.Control>
        </Form.Group>
        <Form.Group controlId="exampleForm.ControlTextarea1">
          <Form.Control
            as="textarea"
            rows="10"
            value={text}
            onChange={handleTextChange}
            disabled={!Boolean(textId)}
          />
        </Form.Group>
        <div>
          <Button onClick={handleSave}>Save</Button>
          <Button onClick={handleDelete} variant="danger">Delete</Button>
        </div>
      </Jumbotron>
    </Container>
  );
};

export default Notepad;
