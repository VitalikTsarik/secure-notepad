import React, { Component } from "react";
import Jumbotron from "react-bootstrap/Jumbotron";
import Container from "react-bootstrap/Container";

import AuthService from "../../services/auth.service";
import CargoOwnerDashboard from "./CargoOwnerDashboard/CargoOwnerDashboard";
import TransporterDashboard from "./TransporterDashboard/TransporterDashboard";
import ManagerDashboard from "./ManagerDashboard/ManagerDashboard";
import { Roles } from "./constants";

export default class Dashboard extends Component {
  state = {
    userRole: ""
  };

  componentDidMount() {
    const user = AuthService.getCurrentUser();
    if (user && user.role) {
      this.setState({userRole: user.role});
    } else {
      throw Error("Error accessing user");
    }
  }

  render() {
    let dashboard;
    switch (this.state.userRole) {
      case Roles.CARGO_OWNER:
        dashboard = <CargoOwnerDashboard/>;
        break;
      case Roles.TRANSPORTER:
        dashboard = <TransporterDashboard/>;
        break;
      case Roles.MANAGER:
        dashboard = <ManagerDashboard/>;
        break;
    }
    return (
      <Container>
        <Jumbotron>
          {dashboard}
        </Jumbotron>
      </Container>
    );
  }
}
