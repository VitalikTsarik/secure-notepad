import React, { useCallback, useEffect, useState } from "react";
import { Link } from "react-router-dom";

import AuthService from "../../services/auth.service";

const Header = () => {
  const [user, setUser] = useState(null);
  useEffect(() => {
    setUser(AuthService.getCurrentUser());
  }, []);

  const logOut = useCallback(() => {
    AuthService.logout();
  }, []);

  return (
    <nav className="navbar navbar-expand-sm  navbar-dark bg-dark">
      <div className="container-md">
        <div className="navbar-brand">
          DAP
        </div>
        <div className="navbar-nav mr-auto">
          {user && (
            <li className="nav-item">
              <Link to={"/dashboard"} className="nav-link">
                Dashboard
              </Link>
            </li>
          )}
        </div>
        {user ? (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <div className="navbar-text">
                {user.login}
              </div>
            </li>
            <li className="nav-item">
              <a href="/login" className="nav-link" onClick={logOut}>
                LogOut
              </a>
            </li>
          </div>
        ) : (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/login"} className="nav-link">
                Login
              </Link>
            </li>
            <li className="nav-item">
              <Link to={"/register"} className="nav-link">
                Sign Up
              </Link>
            </li>
          </div>
        )}
      </div>
    </nav>
  );
};

export default Header;