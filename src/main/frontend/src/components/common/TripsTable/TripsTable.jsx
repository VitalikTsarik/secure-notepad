import React from "react";
import Table from "react-bootstrap/Table";

import Trip from "./Trip/Trip";

const TripsTable = ({trips, actions}) => {
  return (
    <Table striped bordered hover responsive>
      <thead>
      <tr>
        <th>Car</th>
        <th>Packages</th>
        <th>Route</th>
        <th>Current location</th>
        {actions && <th>Actions</th>}
      </tr>
      </thead>
      <tbody>
      {trips.map((trip) => (
        <Trip
          key={trip.id}
          trip={trip}
          actions={actions && {
            onNext: (order) => actions.onNext(order, trip.id),
            onPackages: () => actions.onPackages(trip.id),
            onRoute: () => actions.onRoute(trip.id),
            onStart: () => actions.onStart(trip.id),
            onFinish: () => actions.onFinish(trip.id),
          }}
        />
      ))}
      </tbody>
    </Table>
  );
};

export default TripsTable;