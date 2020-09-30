import React from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { Formik } from "formik";

import TransporterService from "../../../../services/transporter.service";

const TripModal = (props) => {
  const handleSubmit = async (values) => {
    await TransporterService.createTrip({car: values.car});
    props.onHide();
  };

  return (
    <Modal
      {...props}
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title>Create trip</Modal.Title>
      </Modal.Header>
      <Formik
        initialValues={{
          car: "",
        }}
        onSubmit={handleSubmit}
        validateOnChange={false}
      >
        {({handleSubmit, handleChange, values}) => (
          <Form onSubmit={handleSubmit}>
            <Modal.Body>
              <Form.Group>
                <Form.Label>Car</Form.Label>
                <Form.Control
                  name="car"
                  onChange={handleChange}
                  value={values.car}
                  type="text"
                />
              </Form.Group>
            </Modal.Body>
            <Modal.Footer>
              <Button type="submit">Submit</Button>
            </Modal.Footer>
          </Form>
        )}
      </Formik>
    </Modal>
  );
};

export default TripModal;