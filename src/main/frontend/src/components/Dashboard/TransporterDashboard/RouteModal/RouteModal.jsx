import React, { useEffect, useState } from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import EditableDndList from "editable-dnd-list";

import TransporterService from "../../../../services/transporter.service";

const RouteModal = ({trip, ...props}) => {
  const [routeList, setRouteList] = useState([]);
  useEffect(() => {
    if (trip) {
      setRouteList(trip.routeList.map((item, index) => ({id: `${item}${index}`, text: item})));
    }
  }, [trip]);

  const handleSubmit = async () => {
    const route = routeList
      .filter((item) => Boolean(item.text))
      .map((item) => item.text);
    await TransporterService.assignRoute(route, trip.id);
    props.onHide();
  };
  return (
    <Modal
      {...props}
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title>Route</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <span>Setup your route by listing locations one by one, starting with the first. Use drag and drop to order them</span>
        <EditableDndList items={routeList} onChange={(value) => setRouteList(value)}/>
      </Modal.Body>
      <Modal.Footer>
        <Button
          type="submit"
          onClick={handleSubmit}
          disabled={routeList.length < 2}
        >
          Submit
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default RouteModal;