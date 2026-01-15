import React, { useState, useEffect } from 'react';
import { useSearchParams } from "react-router-dom";

import Header from './Header';
import Navbar from './Navbar';
import Footer from './Footer';
import ComponentList from './ComponentList';

import '../assets/Components.css';



const Components = () => {

      

  const [searchParams, setSearchParams] = useSearchParams();

  const [filter, setFilter] = useState({type: searchParams.get("type") || "CPU", minPrice : 0, maxPrice : 10000, minVrednost1 : 0, maxVrednost1 : 10000, minVrednost2 : 0, maxVrednost2 : 10000, minVrednost3: 0, maxVrednost3 : 10000});

  const handleChange = event => {
    const { id, value } = event.target;

    setFilter(prevFilter => ({
      ...prevFilter,
      [id]: value
    }));

  };

  
  const [labelVrednost1, setLabelVrednost1] = useState('Vrednost 1:');
  const [labelVrednost2, setLabelVrednost2] = useState('Vrednost 2:');
  const [labelVrednost3, setLabelVrednost3] = useState('Vrednost 3:');

  useEffect(() => {
    if (filter.type === 'GPU') {
      setLabelVrednost1('VRam:');
      setLabelVrednost2('GPU Clock:');
      setLabelVrednost3('GPU Top Temperature:');
    }

    else if(filter.type === 'CPU') {
        setLabelVrednost1('Num. of Cores:');
        setLabelVrednost2('CPU speed(GHz):');
        setLabelVrednost3('Cache memory(MB):');
      }

    else if(filter.type === 'PSU') {
        setLabelVrednost1('Wattage:');
        setLabelVrednost2('Efficiency:');
        setLabelVrednost3('Amperage:');
    }

    else if(filter.type === 'SSD') {
        setLabelVrednost1('Capacity(GB):');
        setLabelVrednost2('Write speed(MB/s):');
        setLabelVrednost3('Read speed(MB/s):');
    }

    else if(filter.type === 'HDD') {
        setLabelVrednost1('Capacity(GB):');
        setLabelVrednost2('Transfer rate(RPM):');
        setLabelVrednost3('Noise level(db):');
    }

    else if(filter.type === 'RAM') {
        setLabelVrednost1('Capacity(GB):');
        setLabelVrednost2('RAM Speed(Mhz):');
        setLabelVrednost3('Voltage(volts):');
    }

    else if(filter.type === 'MOTHERBOARD') {
        setLabelVrednost1('RAM slots:');
        setLabelVrednost2('Max system memory(GB):');
        setLabelVrednost3('USB ports:');
    }

    else if(filter.type === 'CASE') {
      setLabelVrednost1('Višina(cm):');
      setLabelVrednost2('Širina(cm):');
      setLabelVrednost3('Teža(kg):');
  }
    
    else {
      setLabelVrednost1('Vrednost1:');
      setLabelVrednost2('Vrednost2:');
      setLabelVrednost3('Vrednost3:');
  }}, [filter]);

  return (
    <div>
      <Header />
      <Navbar />

      <div>
        <br></br>
        <form className="form">
        <div className="col">
          <label>Tip:</label> 
            <select className='form-select' id="type" value={filter.type} onChange={handleChange}>
              <option>CPU</option>
              <option>GPU</option>
              <option>RAM</option>
              <option>MOTHERBOARD</option>
              <option>HDD</option>
              <option>SSD</option>
              <option>PSU</option>
              <option>CASE</option>
            </select>
            </div>
          <div className='row'>
            <div className="col">
            <label>Min Cena:</label>
            <input className="form-control" type="text" value={filter.minPrice} onChange={handleChange} id="minPrice" />
            <label>Min {labelVrednost1}</label>
            <input className="form-control" type="text" value={filter.minVrednost1} onChange={handleChange} id="minVrednost1" />
            <label>Min {labelVrednost2}</label>
            <input className="form-control" type="text" value={filter.minVrednost2} onChange={handleChange} id="minVrednost2" />
            <label>Min {labelVrednost3}</label>
            <input className="form-control" type="text" value={filter.minVrednost3} onChange={handleChange} id="minVrednost3" />
            </div>
            <div className='col'>
            <label>Max Cena:</label>
            <input className="form-control" type="text" value={filter.maxPrice} onChange={handleChange} id="maxPrice" />
            <label>Max {labelVrednost1}</label>
            <input className="form-control" type="text" value={filter.maxVrednost1} onChange={handleChange} id="maxVrednost1" />
            <label>Max {labelVrednost2}</label>
            <input className="form-control" type="text" value={filter.maxVrednost2} onChange={handleChange} id="maxVrednost2" />
            <label>Max {labelVrednost3}</label>
            <input className="form-control" type="text" value={filter.maxVrednost3} onChange={handleChange} id="maxVrednost3" />
            </div>
          </div><br></br>
        </form>
        <br></br>

        <div>

          <div className="componentInfo">
          <div className="componentChange">
          </div>
            <p>Naziv:</p>
            <p>Cena:</p>
            <p>{labelVrednost1}</p>
            <p>{labelVrednost2}</p>
            <p>{labelVrednost3}</p>
          </div>

        </div>

        <ComponentList {...filter}/>

      </div>
        
      <Footer />
  
    </div>

  );

};

export default Components;
