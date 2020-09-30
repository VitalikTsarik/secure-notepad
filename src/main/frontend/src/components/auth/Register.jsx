import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import Select from "react-validation/build/select";
import CheckButton from "react-validation/build/button";

import AuthService from "../../services/auth.service";
import { required, username, password } from "./validation";

export default class Register extends Component {
  constructor(props) {
    super(props);
    this.state = {
      login: "",
      firstName: "",
      middleName: "",
      lastName: "",
      role: "",
      password: "",
      successful: false,
      message: ""
    };

    this.handleRegister = this.handleRegister.bind(this);
    this.handleChangeInput = this.handleChangeInput.bind(this);
  }

  handleChangeInput(field, e) {
    this.setState({[field]: e.target.value});
  }

  handleRegister(e) {
    e.preventDefault();

    this.setState({
      message: "",
      successful: false
    });

    this.form.validateAll();

    if (this.checkBtn.context._errors.length === 0) {
      AuthService.register(
        this.state.login,
        this.state.firstName,
        this.state.middleName,
        this.state.lastName,
        this.state.role,
        this.state.password,
      ).then(
        response => {
          this.setState({
            message: response.data.message,
            successful: true
          });
        },
        error => {
          const resMessage =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          this.setState({
            successful: false,
            message: resMessage
          });
        }
      );
    }
  }

  render() {
    return (
      <div className="col-md-12">
        <div className="card card-container">
          <img
            src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
            alt="profile-img"
            className="profile-img-card"
          />

          <Form
            onSubmit={this.handleRegister}
            ref={c => {
              this.form = c;
            }}
          >
            {!this.state.successful && (
              <>
                <div className="form-group">
                  <label htmlFor="login">Login</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="login"
                    value={this.state.login}
                    onChange={(e) => this.handleChangeInput("login", e)}
                    validations={[required, username]}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="firstName">First Name</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="firstName"
                    value={this.state.firstName}
                    onChange={(e) => this.handleChangeInput("firstName", e)}
                    validations={[required]}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="middleName">Middle Name</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="middleName"
                    value={this.state.middleName}
                    onChange={(e) => this.handleChangeInput("middleName", e)}
                    validations={[required]}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="lastName">Last Name</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="lastName"
                    value={this.state.lastName}
                    onChange={(e) => this.handleChangeInput("lastName", e)}
                    validations={[required]}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="role">Role</label>
                  <Select
                    className="form-control"
                    name="role"
                    value={this.state.role}
                    onChange={(e) => this.handleChangeInput("role", e)}
                    validations={[required]}
                  >
                    <option value="">Choose your role</option>
                    <option value="0">Cargo owner</option>
                    <option value="1">Transporter</option>
                    <option value="2">Manager</option>
                  </Select>
                </div>
                <div className="form-group">
                  <label htmlFor="password">Password</label>
                  <Input
                    type="password"
                    className="form-control"
                    name="password"
                    value={this.state.password}
                    onChange={(e) => this.handleChangeInput("password", e)}
                    validations={[required, password]}
                  />
                </div>
                <div className="form-group">
                  <button className="btn btn-primary btn-block">Sign Up</button>
                </div>
              </>
            )}
            {this.state.message && (
              <div className="form-group">
                <div
                  className={
                    this.state.successful
                      ? "alert alert-success"
                      : "alert alert-danger"
                  }
                  role="alert"
                >
                  {this.state.message}
                </div>
              </div>
            )}
            <CheckButton
              style={{display: "none"}}
              ref={c => {
                this.checkBtn = c;
              }}
            />
          </Form>
        </div>
      </div>
    );
  }
}
