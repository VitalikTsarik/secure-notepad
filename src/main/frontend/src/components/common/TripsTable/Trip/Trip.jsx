import React from "react";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import Button from "react-bootstrap/Button";

const States = Object.freeze({
  CREATING: 'CREATING',
  STARTED: 'STARTED',
  FINISHED: 'FINISHED',
});

const Trip = ({trip, actions}) => {
  const packages = trip.packageList.map((item, index) => (
    <span key={item.id}>{item.name}{(index !== trip.packageList.length - 1) && ", "}</span>
  ));
  const routeList = trip.routeList;
  const route = routeList.map((item, index) => (
    <span key={index}>{item}{(index !== routeList.length - 1) && " ⇨ "}</span>
  ));
  const first = trip.currentLocation === 0;
  const last = trip.currentLocation === trip.routeList.length - 1;

  let currentLocation;
  let buttons;
  switch (trip.state) {
    case States.CREATING:
      currentLocation = "Not started";
      buttons = actions && (
        <ButtonGroup>
          <Button
            variant="primary"
            onClick={actions.onPackages}
          >
            Packages
          </Button>
          <Button
            variant="primary"
            onClick={actions.onRoute}
          >
            Route
          </Button>
          <Button
            variant="success"
            onClick={actions.onStart}
            disabled={trip.packageList.length === 0 || routeList.length === 0}
          >
            Start
          </Button>
        </ButtonGroup>
      )
      break;
    case States.STARTED:
      currentLocation = trip.routeList[trip.currentLocation];
      buttons = actions && (
        <ButtonGroup>
          <Button
            variant="primary"
            onClick={() => actions.onNext(trip.currentLocation - 1)}
            disabled={first}
          >
            Previous
          </Button>
          <Button
            variant="primary"
            onClick={() => actions.onNext(trip.currentLocation + 1)}
            disabled={last}
          >
            Next
          </Button>
          <Button
            variant="danger"
            onClick={actions.onFinish}
            disabled={!last}
          >
            Finish
          </Button>
        </ButtonGroup>
      )
      break
    case States.FINISHED:
      currentLocation = "Finished";
      break;
  }

  return (
    <tr>
      <td>{trip.car}</td>
      <td>{packages}</td>
      <td>{route}</td>
      <td>{currentLocation}</td>
      {actions && (
        <td>{buttons}</td>
      )}
    </tr>
  );
};

export default Trip;