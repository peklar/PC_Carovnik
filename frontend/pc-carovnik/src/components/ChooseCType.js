import React from 'react';
import Header from './Header';
import Navbar from './Navbar';
import Footer from './Footer';
import backgroundImage from '../assets/images/cpuSlika.jpg';
import backgroundImage1 from '../assets/images/gpuSlika.jpg';
import backgroundImage2 from '../assets/images/ramSlika.jpg';
import backgroundImage3 from '../assets/images/motherboardSlika.jpg';
import backgroundImage4 from '../assets/images/hddSlika.jpg';
import backgroundImage5 from '../assets/images/ssdSlika.jpg';
import backgroundImage6 from '../assets/images/psuSlika.jpg';
import backgroundImage7 from '../assets/images/accessorySlika.jpg';

import ComponentLink from './ComponentLink';

import '../assets/ChooseCType.css';

const ChooseCType = () => {
  return (
    <div>
      <Header />
      <Navbar />

      <div className="parent">
        <ComponentLink
          href="./components?type=CPU&minPrice=1&maxPrice=1000&minVrednost1=1&maxVrednost1=1000&minVrednost2=1&maxVrednost2=2000&minVrednost3=1&maxVrednost3=1000"
          backgroundImage={backgroundImage}
          text="CPU"
        />

        <ComponentLink
          href="./components?type=GPU&minPrice=1&maxPrice=1000&minVrednost1=1&maxVrednost1=1000&minVrednost2=1&maxVrednost2=2000&minVrednost3=1&maxVrednost3=1000"
          backgroundImage={backgroundImage1}
          text="GPU"
        />
        <ComponentLink
          href="./components?type=RAM&minPrice=1&maxPrice=1000&minVrednost1=1&maxVrednost1=1000&minVrednost2=1&maxVrednost2=2000&minVrednost3=1&maxVrednost3=1000"
          backgroundImage={backgroundImage2}
          text="RAM"
        />
        <ComponentLink
          href="./components?type=MOTHERBOARD&minPrice=1&maxPrice=1000&minVrednost1=1&maxVrednost1=1000&minVrednost2=1&maxVrednost2=2000&minVrednost3=1&maxVrednost3=1000"
          backgroundImage={backgroundImage3}
          text="MOTHERBOARD"
        />
      </div>

      <div className="parent">
        <ComponentLink
          href="./components?type=HDD&minPrice=1&maxPrice=1000&minVrednost1=1&maxVrednost1=1000&minVrednost2=1&maxVrednost2=2000&minVrednost3=1&maxVrednost3=1000"
          backgroundImage={backgroundImage4}
          text="HDD"
        />
        <ComponentLink
          href="./components?type=SSD&minPrice=1&maxPrice=1000&minVrednost1=1&maxVrednost1=1000&minVrednost2=1&maxVrednost2=2000&minVrednost3=1&maxVrednost3=1000"
          backgroundImage={backgroundImage5}
          text="SSD"
        />

        <ComponentLink
          href="./components?type=PSU&minPrice=1&maxPrice=1000&minVrednost1=1&maxVrednost1=1000&minVrednost2=1&maxVrednost2=2000&minVrednost3=1&maxVrednost3=1000"
          backgroundImage={backgroundImage6}
          text="PSU"
        />
        <ComponentLink
          href="./components?type=CASE&minPrice=1&maxPrice=1000&minVrednost1=1&maxVrednost1=1000&minVrednost2=1&maxVrednost2=2000&minVrednost3=1&maxVrednost3=1000"
          backgroundImage={backgroundImage7}
          text="PC CASE"
        />
      </div>

      <Footer />
    </div>
  );
};

export default ChooseCType;
