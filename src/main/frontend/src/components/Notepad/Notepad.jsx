import React, { useCallback, useEffect, useState } from "react";
import Jumbotron from "react-bootstrap/Jumbotron";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

import TextService from "../../services/text.service";

const DEFAULT_TEXT_SELECT = "Select text";

const Notepad = () => {
  const [text, setText] = useState("");
  const [textId, setTextId] = useState(DEFAULT_TEXT_SELECT);
  const [texts, setTexts] = useState([DEFAULT_TEXT_SELECT]);

  useEffect(() => {
    (async () => {
      const texts = await TextService.getTexts();
      setTexts([DEFAULT_TEXT_SELECT, ...texts]);
    })();
  }, []);

  const handleTextIdChange = useCallback(async (e) => {
    const textId = e.target.value;
    setTextId(textId);
    if (textId === DEFAULT_TEXT_SELECT) {
      setText("");
    } else {
      const text = await TextService.getTextById(textId);
      setText(text);
    }
  }, []);
  const handleTextChange = useCallback((e) => {
    setText(e.target.value);
  }, []);
  const handleSave = useCallback(() => {
    TextService.editText(textId, text);
  }, [textId, text]);
  const handleDelete = useCallback(async () => {
    await TextService.removeText(textId);
    setText("");
  }, [textId]);

  const textNotSelected = textId === DEFAULT_TEXT_SELECT;

  return (
    <Container>
      <Jumbotron>
        <Form.Group controlId="exampleForm.ControlSelect1">
          <Form.Label>Select file</Form.Label>
          <Form.Control as="select" onChange={handleTextIdChange}>
            {texts.map((file) => (<option key={file}>{file}</option>))}
          </Form.Control>
        </Form.Group>
        <Form.Group controlId="exampleForm.ControlTextarea1">
          <Form.Control
            as="textarea"
            rows="10"
            value={text}
            onChange={handleTextChange}
            disabled={textNotSelected}
          />
        </Form.Group>
        <div>
          <Button
            onClick={handleSave}
            disabled={textNotSelected}
          >
            Save
          </Button>
          <Button
            onClick={handleDelete}
            variant="danger"
            disabled={textNotSelected}
          >
            Delete
          </Button>
        </div>
      </Jumbotron>
    </Container>
  );
};

export default Notepad;
