import React from 'react';
import { Container } from 'react-bootstrap';

const Footer = () => {
  return (
    <footer className="bg-dark text-white py-3">
      <Container>
        <p>&copy; {new Date().getFullYear()} PC-Carovnik</p>
      </Container>
    </footer>
  );
};

export default Footer;
