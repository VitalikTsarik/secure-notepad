import React, { useEffect, useState } from "react";
import Table from "react-bootstrap/Table";
import Button from "react-bootstrap/Button";
import ButtonGroup from "react-bootstrap/ButtonGroup";

import "./CargoOwnerDashboard.css";

import CargoOwnerService from "../../../services/cargo-owner.service";
import CargoModal from "../../common/CargoModal/CargoModal";
import TripsTable from "../../common/TripsTable/TripsTable";
import PackagesTable from "./PackagesTable/PackagesTable";

const CargoOwnerDashboard = () => {
  const [packages, setPackages] = useState([]);
  const [trips, setTrips] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const [modalEdit, setModalEdit] = useState(false);
  const [modalItem, setModalItem] = useState({});

  useEffect(() => {
    (async () => {
      setPackages(await CargoOwnerService.getPackages());
      setTrips(await CargoOwnerService.getTrips());
    })();
  }, []);

  const handleEdit = async (id) => {
    const item = await CargoOwnerService.getPackage(id);
    setModalItem(item);
    setModalEdit(true);
    setModalShow(true);
  };
  const handleRemove = async (id) => {
    await CargoOwnerService.removePackage(id);
    setPackages(prev => prev.filter(item => item.id !== id));
  };
  const handleAdd = () => {
    setModalItem({});
    setModalEdit(false);
    setModalShow(true);
  };
  const handleHide = async () => {
    setPackages(await CargoOwnerService.getPackages());
    setModalShow(false);
  };
  return (
    <>
      <h2>Your packages</h2>
      <div className="content">
        <PackagesTable
          packages={packages}
          actions={{
            onEdit: handleEdit,
            onRemove: handleRemove,
          }}
        />
        {(packages.length === 0) && <span>Your don't have any packages. Try creating one </span>}
        <div className="mb-4">
          <Button onClick={handleAdd}>Add</Button>
        </div>
      </div>
      <h2>Trips with your packages</h2>
      <TripsTable trips={trips}/>
      <CargoModal
        show={modalShow}
        edit={modalEdit}
        item={modalItem}
        onHide={handleHide}
      />
    </>
  );
};

export default CargoOwnerDashboard;