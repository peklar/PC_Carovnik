import React from 'react';
import Header from './Header';
import Navbar from './Navbar';
import Content from './Content';
import Footer from './Footer';
import '../assets/Home.css';

const Home = () => {
  return (
    <div>
      <Header />
      <Navbar />
      <Content />
      <Footer />
    </div>
  );
};

export default Home;
