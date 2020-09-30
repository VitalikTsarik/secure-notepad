import React from "react";
import FormCheck from "react-bootstrap/FormCheck";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";

const PackagesTable = ({packages, select, onDetail}) => {
  return (
    <Table responsive>
      <thead>
      <tr>
        {select && <th/>}
        <th>Name</th>
        <th>Initial Location</th>
        <th>Target Location</th>
        <th>Cost</th>
        {onDetail && <th/>}
      </tr>
      </thead>
      <tbody>
      {Boolean(packages.length) && packages.map((item) => (
        <tr key={item.id}>
          {select && (
            <td>
              <FormCheck
                defaultChecked={select.selectedPackages.find((value => value.id === item.id))}
                onChange={() => select.onSelect(item.id)}
              />
            </td>
          )}
          <td>{item.name}</td>
          <td>{item.initialLocation.name || item.initialLocation}</td>
          <td>{item.targetLocation.name || item.targetLocation}</td>
          <td>${item.cost}</td>
          {onDetail && (
            <td>
              <ButtonGroup>
                <Button
                  variant="primary"
                  onClick={() => onDetail(item.id)}
                >
                  Detail
                </Button>
              </ButtonGroup>
            </td>
          )}
        </tr>
      ))}
      </tbody>
    </Table>
  );
};

export default PackagesTable;
