import React from "react";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";

const PackagesTable = ({packages, actions}) => {
  return (
    <Table striped bordered hover responsive>
      <thead>
      <tr>
        <th>Name</th>
        <th>Initial Location</th>
        <th>Target Location</th>
        <th>Cost</th>
        {actions && <th>Actions</th>}
      </tr>
      </thead>
      <tbody>
      {Boolean(packages.length) && packages.map((item) => (
        <tr key={item.id}>
          <td>{item.name}</td>
          <td>{item.initialLocation}</td>
          <td>{item.targetLocation}</td>
          <td>${item.cost}</td>
          {actions && (
            <td>
              {!item.tripId && (
                <ButtonGroup>
                  <Button
                    variant="primary"
                    onClick={() => actions.onEdit(item.id)}
                  >
                    Edit
                  </Button>
                  <Button
                    variant="danger"
                    onClick={() => actions.onRemove(item.id)}
                  >
                    Remove
                  </Button>
                </ButtonGroup>
              )}
            </td>
          )}
        </tr>
      ))}
      </tbody>
    </Table>
  );
};

export default PackagesTable;
