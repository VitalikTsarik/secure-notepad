import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "../../services/auth.service";
import Header from "../Header/Header";
import Login from "../auth/Login";
import Register from "../auth/Register";
import KeysGeneration from "../KeysGeneration/KeysGeneration";
import Notepad from "../Notepad/Notepad";

const App = () => {
  const user = AuthService.getCurrentUser();
  return (
    <Router>
      <Header/>
      <div className="container mt-3">
        <Switch>
          <Route exact path="/" component={user ? Notepad : KeysGeneration}/>
          <Route exact path="/keys-generation" component={KeysGeneration}/>
          <Route exact path="/notepad" component={Notepad}/>
          <Route exact path="/login" component={Login}/>
          <Route exact path="/register" component={Register}/>
        </Switch>
      </div>
    </Router>
  );
};

export default App;
