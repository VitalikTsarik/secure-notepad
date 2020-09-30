import React from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { Formik } from "formik";

import CargoOwnerService from "../../../services/cargo-owner.service";

const CargoModal = ({edit = false, disabled = false, item = {}, ...props}) => {
  const handleSubmit = async (values) => {
    if (edit) {
      await CargoOwnerService.editPackage({id: item.id, ...values});
    } else {
      await CargoOwnerService.createPackage(values);
    }
    props.onHide();
  };

  return (
    <Modal
      {...props}
      size="lg"
      centered
    >
      <Modal.Header closeButton>
        {item.name && (
          <Modal.Title>
            Package "{item.name}"
          </Modal.Title>
        )}
      </Modal.Header>
      <Formik
        initialValues={{
          name: item.name || "",
          cost: item.cost || 0,
          height: item.height || 0,
          length: item.length || 0,
          width: item.width || 0,
          weight: item.weight || 0,
          initialLocation: item.initialLocation && item.initialLocation.name || item.initialLocation || "",
          targetLocation: item.targetLocation && item.targetLocation.name || item.targetLocation || "",
        }}
        onSubmit={handleSubmit}
        validateOnChange={false}
      >
        {({handleSubmit, handleChange, values}) => (
          <Form onSubmit={handleSubmit}>
            <Modal.Body>
              <Form.Group>
                <Form.Label>Name</Form.Label>
                <Form.Control
                  name="name"
                  onChange={handleChange}
                  value={values.name}
                  readOnly={disabled}
                  type="text"
                />
              </Form.Group>
              <Form.Group>
                <Form.Label>Cost, $</Form.Label>
                <Form.Control
                  name="cost"
                  onChange={handleChange}
                  value={values.cost}
                  readOnly={disabled}
                  type="number"
                />
              </Form.Group>
              <Form.Group>
                <Form.Label>Height, cm</Form.Label>
                <Form.Control
                  name="height"
                  onChange={handleChange}
                  value={values.height}
                  readOnly={disabled}
                  type="number"
                />
              </Form.Group>
              <Form.Group>
                <Form.Label>Length, cm</Form.Label>
                <Form.Control
                  name="length"
                  onChange={handleChange}
                  value={values.length}
                  readOnly={disabled}
                  type="number"
                />
              </Form.Group>
              <Form.Group>
                <Form.Label>Width, cm</Form.Label>
                <Form.Control
                  name="width"
                  onChange={handleChange}
                  value={values.width}
                  readOnly={disabled}
                  type="number"
                />
              </Form.Group>
              <Form.Group>
                <Form.Label>Weight, kg</Form.Label>
                <Form.Control
                  name="weight"
                  onChange={handleChange}
                  value={values.weight}
                  readOnly={disabled}
                  type="number"
                />
              </Form.Group>
              <Form.Group>
                <Form.Label>Initial Location</Form.Label>
                <Form.Control
                  name="initialLocation"
                  onChange={handleChange}
                  value={values.initialLocation}
                  readOnly={disabled}
                  type="text"
                />
              </Form.Group>
              <Form.Group>
                <Form.Label>Target Location</Form.Label>
                <Form.Control
                  name="targetLocation"
                  onChange={handleChange}
                  value={values.targetLocation}
                  readOnly={disabled}
                  type="text"
                />
              </Form.Group>
            </Modal.Body>
            {!disabled && (
              <Modal.Footer>
                <Button type="submit">Submit</Button>
              </Modal.Footer>
            )}
          </Form>
        )}
      </Formik>
    </Modal>
  );
};

export default CargoModal;