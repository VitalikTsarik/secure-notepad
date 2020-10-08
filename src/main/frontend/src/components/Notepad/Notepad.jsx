import React, { useCallback, useEffect, useMemo, useState } from "react";
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

  const updateTexts = useCallback(async () => {
    const newTexts = await TextService.getTexts();
    setTexts([DEFAULT_TEXT_SELECT, ...newTexts]);
  }, []);

  useEffect(() => {
    updateTexts();
  }, [updateTexts]);

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
    await updateTexts();
    setText("");
    setTextId(DEFAULT_TEXT_SELECT);
  }, [textId]);
  const handleNew = useCallback(async () => {
    await TextService.createText();
    const newTexts = await TextService.getTexts();
    setTexts([DEFAULT_TEXT_SELECT, ...newTexts]);
    const newTextId = newTexts[newTexts.length - 1];
    setText("");
    setTextId(newTextId);
  }, [textId]);

  const textNotSelected = textId === DEFAULT_TEXT_SELECT;

  const options = useMemo(() => texts.map((file) => (<option key={file}>{file}</option>)), [JSON.stringify(texts), textId]);
  return (
    <Container>
      <Jumbotron>
        <Form.Group controlId="exampleForm.ControlSelect1">
          <Form.Label>Select file</Form.Label>
          <Form.Control as="select" value={textId} onChange={handleTextIdChange}>
            {options}
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
          <Button
            onClick={handleNew}
            variant="success"
          >
            New
          </Button>
        </div>
      </Jumbotron>
    </Container>
  );
};

export default Notepad;
