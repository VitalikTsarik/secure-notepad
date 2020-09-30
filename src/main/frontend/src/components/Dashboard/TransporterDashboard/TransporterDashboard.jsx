import React, { useEffect, useState } from "react";
import Button from "react-bootstrap/Button";

import TransporterService from "../../../services/transporter.service";
import TripsTable from "../../common/TripsTable/TripsTable";
import PackagesModal from "./PackagesModal/PackagesModal";
import TripModal from "./TripModal/TripModal";
import RouteModal from "./RouteModal/RouteModal";

const TransporterDashboard = () => {
  const [trips, setTrips] = useState([]);

  const [modalTrip, setModalTrip] = useState(false);
  const [currentTrip, setCurrentTrip] = useState(null);
  const [modalPackages, setModalPackages] = useState(false);
  const [modalRoute, setModalRoute] = useState(false);

  useEffect(() => {
    (async () => {
      setTrips(await TransporterService.getTrips());
    })();
  }, []);
  const handleNext = async (order, id) => {
    await TransporterService.updateCurrentCity(order, id);
    setTrips(await TransporterService.getTrips());
  };
  const handlePackages = (id) => {
    setCurrentTrip(trips.find((trip) => trip.id === id));
    setModalPackages(true);
  };
  const handleRoute = (id) => {
    setCurrentTrip(trips.find((trip) => trip.id === id));
    setModalRoute(true);
  };
  const handleStart = async (id) => {
    await TransporterService.startTrip(id);
    setTrips(await TransporterService.getTrips());
  };
  const handleFinish = async (id) => {
    await TransporterService.finishTrip(id);
    setTrips(await TransporterService.getTrips());
  };
  const handleAdd = () => {
    setModalTrip(true);
  };
  const handleModalTripHide = async () => {
    setTrips(await TransporterService.getTrips());
    setModalTrip(false);
  };
  const handleModalPackagesHide = async () => {
    setTrips(await TransporterService.getTrips());
    setModalPackages(false);
  };
  const handleModalRouteHide = async () => {
    setTrips(await TransporterService.getTrips());
    setModalRoute(false);
  };
  return (
    <>
      <h2>Your trips</h2>
      <div className="content">
        <TripsTable
          trips={trips}
          actions={{
            onNext: handleNext,
            onPackages: handlePackages,
            onRoute: handleRoute,
            onStart: handleStart,
            onFinish: handleFinish,
          }}
        />
        {(trips.length === 0) && <span>Your don't have any trips. Try creating one </span>}
        <div className="mb-4">
          <Button onClick={handleAdd}>Add</Button>
        </div>
      </div>

      <TripModal
        show={modalTrip}
        onHide={handleModalTripHide}
      />
      <PackagesModal
        show={modalPackages}
        trip={currentTrip}
        onHide={handleModalPackagesHide}
      />
      <RouteModal
        show={modalRoute}
        trip={currentTrip}
        onHide={handleModalRouteHide}
      />
    </>
  );
};

export default TransporterDashboard;