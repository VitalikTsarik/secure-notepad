import React, { useCallback, useEffect, useState } from "react";
import { Link } from "react-router-dom";

import AuthService from "../../services/auth.service";
import SessionService from "../../services/session.service";

const Header = () => {
  const [user, setUser] = useState(null);
  useEffect(() => {
    setUser(AuthService.getCurrentUser());
  }, []);

  const logOut = useCallback(() => {
    AuthService.logout();
  }, []);

  const [sessionKey, setSessionKey] = useState();
  useEffect(() => {
    setSessionKey(SessionService.getCurrentSessionKey());
  }, []);

  return (
    <nav className="navbar navbar-expand-sm  navbar-dark bg-dark">
      <div className="container-md">
        <div className="navbar-brand">
          Secure Notepad
        </div>
        <div className="navbar-nav mr-auto">
          {(Boolean(sessionKey) && Boolean(user)) && (
            <li className="nav-item">
              <Link to={"/notepad"} className="nav-link">
                Notepad
              </Link>
            </li>
          )}
          <li className="nav-item">
            <Link to={"/keys"} className="nav-link">
              Keys
            </Link>
          </li>
        </div>
        {user ? (
          (<div className="navbar-nav ml-auto">
              <li className="nav-item">
                <div className="navbar-text">
                  {user.login}
                </div>
              </li>
              <li className="nav-item">
                <a href="/keys" className="nav-link" onClick={logOut}>
                  LogOut
                </a>
              </li>
            </div>
          )
        ) : (
          Boolean(sessionKey) && (<div className="navbar-nav ml-auto">
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
          )
        )}
      </div>
    </nav>
  );
};

export default Header;