import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import '../assets/Navbar.css';
import { uporabnikIme } from '../services/api';

const MyNavbar = () => {
  const [isAdmin, setIsAdmin] = useState(false);

  const [isImeSet, setIsImeSet] = useState(false);
  useEffect(() => {
    const ime = localStorage.getItem('ime');
    setIsImeSet(!!ime);
    admincheck();
  }, []);

  async function admincheck() {
    if (isImeSet) {
      const uporabnik = await uporabnikIme(localStorage.getItem('ime'));
      setIsAdmin(uporabnik[0].isAdmin);
    }
  }

  const handleLogout = () => {
    admincheck();
    localStorage.removeItem('ime');
    setIsImeSet(false);
  };
  const linkStyle = {
    textDecoration: 'none',
    color: 'inherit',
    cursor: 'pointer',
  };
  admincheck();
  return (
    <Navbar bg="light" expand="lg">
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="m-auto">
          {' '}
          <Nav.Item>
            <Link to="/" className="nav-link">
              Domov
            </Link>
          </Nav.Item>
          <Nav.Item>
            <Link to="/choosetype" className="nav-link">
              Komponente
            </Link>
          </Nav.Item>
          <Nav.Item>
            <Link to="/configuration" className="nav-link">
              Konfigurator
            </Link>
          </Nav.Item>
          {isAdmin && (
            <Nav.Item>
              <Link to="/maintainer" className="nav-link">
                Vzdrževalec
              </Link>
            </Nav.Item>
          )}
          {!isImeSet && (
            <>
              <Nav.Item>
                <Link to="/login" className="nav-link">
                  Prijava
                </Link>
              </Nav.Item>
              <Nav.Item>
                <Link to="/register" className="nav-link">
                  Registracija
                </Link>
              </Nav.Item>
            </>
          )}
          {isImeSet && (
            <Nav.Item onClick={handleLogout} className="nav-link custom-link">
              Odjava
            </Nav.Item>
          )}
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

export default MyNavbar;
