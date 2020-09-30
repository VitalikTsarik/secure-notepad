import React, { useEffect, useState } from "react";
import Modal from "react-bootstrap/Modal";

import TransporterService from "../../../../services/transporter.service";
import CargoModal from "../../../common/CargoModal/CargoModal";
import PackagesTable from "../../../common/PackagesTable/PackagesTable";

const PackagesModal = ({trip, ...props}) => {
  const [packages, setPackages] = useState([]);
  const [selectedPackages, setSelectedPackages] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const [modalItem, setModalItem] = useState({});

  useEffect(() => {
    (async () => {
      const freePackages = await TransporterService.getFreePackages();
      if (trip) {
        setPackages([...trip.packageList, ...freePackages]);
      } else {
        setPackages(freePackages);
      }
    })();
  }, [trip]);
  useEffect(() => {
    if (trip) {
      setSelectedPackages(trip.packageList);
    }
  }, [trip]);

  const handleDetail = (id) => {
    const item = packages.find(item => item.id === id);
    setModalItem(item);
    setModalShow(true);
  };
  const handleSelect = async (id) => {
    const selected = selectedPackages.some(item => item.id === id);
    const item = packages.find(item => item.id === id);
    if (selected) {
      await TransporterService.removePackage(trip.id, id);
      setSelectedPackages((prev) => prev.filter((item) => item.id !== id));
    } else {
      await TransporterService.addPackage(trip.id, id);
      setSelectedPackages((prev) => [...prev, item]);
    }
  };
  return (
    <Modal
      {...props}
      size="lg"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title>Available packages</Modal.Title>
      </Modal.Header>
      <Modal.Body className="content">
        <PackagesTable
          packages={packages}
          select={{
            selectedPackages: selectedPackages,
            onSelect: handleSelect,
          }}
          onDetail={handleDetail}
        />
        {(packages.length === 0) && <span>There are no packages available</span>}
      </Modal.Body>
      <CargoModal
        show={modalShow}
        item={modalItem}
        disabled
        onHide={() => setModalShow(false)}
      />
    </Modal>
  );
};

export default PackagesModal;