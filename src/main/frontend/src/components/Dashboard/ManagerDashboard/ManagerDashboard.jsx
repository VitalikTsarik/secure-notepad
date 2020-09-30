import React, { useEffect, useState } from "react";

import ManagerService from "../../../services/manager.service";
import PackagesTable from "../../common/PackagesTable/PackagesTable";
import TripsTable from "../../common/TripsTable/TripsTable";
import CargoModal from "../../common/CargoModal/CargoModal";

const ManagerDashboard = () => {
  const [packages, setPackages] = useState([]);
  const [trips, setTrips] = useState([]);

  useEffect(() => {
    (async () => {
      setPackages(await ManagerService.getPackages());
      setTrips(await ManagerService.getTrips());
    })();
  }, []);

  const [modalShow, setModalShow] = useState(false);
  const [modalItem, setModalItem] = useState({});
  const handleDetail = (id) => {
    const item = packages.find(item => item.id === id);
    setModalItem(item);
    setModalShow(true);
  };
  return (
    <>
      <h2>All packages</h2>
      <PackagesTable
        packages={packages}
        onDetail={handleDetail}
      />
      <h2>All trips</h2>
      <TripsTable trips={trips}/>

      <CargoModal
        show={modalShow}
        item={modalItem}
        disabled
        onHide={() => setModalShow(false)}
      />
    </>
  );
};

export default ManagerDashboard;