import React, { useState, useEffect } from 'react';
import Header from './Header';
import Navbar from './Navbar';
import Content from './Content';
import Footer from './Footer';
import { komponenta } from '../services/api'; 
import '../assets/Maintainer.css';

const Maintainer = () => {
  const [selectedType, setSelectedType] = useState('');
  const [cena, setCena] = useState('');
  const [naziv, setNaziv] = useState('');
  const [opis, setOpis] = useState('');
  const [url, setUrl] = useState('');
  const [vrednost1, setVrednost1] = useState('');
  const [vrednost2, setVrednost2] = useState('');
  const [vrednost3, setVrednost3] = useState('');
  const [datumIzdaje, setDatumIzdaje] = useState('');
  const [labelVrednost1, setLabelVrednost1] = useState('Vrednost 1:');
  const [labelVrednost2, setLabelVrednost2] = useState('Vrednost 2:');
  const [labelVrednost3, setLabelVrednost3] = useState('Vrednost 3:');

  useEffect(() => {
    if (selectedType === 'GPU') {
      setLabelVrednost1('VRam:');
      setLabelVrednost2('GPU Clock:');
      setLabelVrednost3('GPU Top Temperature:');
    }

    else if(selectedType === 'CPU') {
        setLabelVrednost1('Num. of Cores:');
        setLabelVrednost2('CPU speed(GHz):');
        setLabelVrednost3('Cache memory(MB):');
      }

    else if(selectedType === 'PSU') {
        setLabelVrednost1('Wattage:');
        setLabelVrednost2('Efficiency:');
        setLabelVrednost3('Amperage:');
    }

    else if(selectedType === 'SSD') {
        setLabelVrednost1('Capacity(GB):');
        setLabelVrednost2('Write speed(MB/s):');
        setLabelVrednost3('Read speed(MB/s):');
    }

    else if(selectedType === 'HDD') {
        setLabelVrednost1('Capacity(GB):');
        setLabelVrednost2('Transfer rate(RPM):');
        setLabelVrednost3('Noise level(db):');
    }

    else if(selectedType === 'RAM') {
        setLabelVrednost1('Capacity(GB):');
        setLabelVrednost2('RAM Speed(Mhz):');
        setLabelVrednost3('Voltage(volts):');
    }

    else if(selectedType === 'MOTHERBOARD') {
        setLabelVrednost1('RAM slots:');
        setLabelVrednost2('Max system memory(GB):');
        setLabelVrednost3('USB ports:');
    }

    else if(selectedType === 'CASE') {
      setLabelVrednost1('Višina(cm):');
      setLabelVrednost2('Širina(cm):');
      setLabelVrednost3('Teža(kg):');
  }
    
    else {
      setLabelVrednost1('Vrednost1:');
      setLabelVrednost2('Vrednost2:');
      setLabelVrednost3('Vrednost3:');
    }
  }, [selectedType]);

  const maintainerStyle = {
    minHeight: '180vh',
    minWidth: '60%',
    marginTop: '5%',
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const intVrednost1 = parseInt(vrednost1, 10) || 0;
    const intVrednost2 = parseInt(vrednost2, 10) || 0;
    const intVrednost3 = parseInt(vrednost3, 10) || 0;

    const formData = {
      tipKomponente: selectedType,
      cena: parseInt(cena, 10) || 0,
      naziv,
      opis,
      url,
      vrednost1: intVrednost1,
      vrednost2: intVrednost2,
      vrednost3: intVrednost3,
      datumIzdaje, 
    };

    try {
      await komponenta(formData);
      console.log('Data inserted successfully');
      setSelectedType('');
      setCena('');
      setNaziv('');
      setOpis('');
      setUrl('');
      setVrednost1('');
      setVrednost2('');
      setVrednost3('');
      setDatumIzdaje('');
    } catch (error) {
      console.error('Error inserting data:', error);
    }
  };


  return (
    <div>
      <Header />
      <Navbar />
      <div style={maintainerStyle} className="form-container">
        <h2>Dodajanje komponent</h2>
        <form onSubmit={handleSubmit}>
          <label htmlFor="typeDropdown">Tip komponente:</label><br />
          <select
            id="typeDropdown"
            name="typeDropdown"
            value={selectedType}
            onChange={(e) => setSelectedType(e.target.value)}
          >
            <option value="">Izberi tip...</option>
            <option value="CPU">CPU</option>
            <option value="GPU">GPU</option>
            <option value="RAM">RAM</option>
            <option value="SSD">SSD</option>
            <option value="HDD">HDD</option>
            <option value="PSU">PSU</option>
            <option value="MOTHERBOARD">MOTHERBOARD</option>
            <option value="CASE">CASE</option>
          </select>
          <br />

          <label htmlFor="datumIzdaje">Datum izdaje:</label><br />
          <input
            type="date"
            id="datumIzdaje"
            name="datumIzdaje"
            value={datumIzdaje}
            onChange={(e) => setDatumIzdaje(e.target.value)}
          />
          <br />


          <label htmlFor="cena">Cena:</label><br />
          <input
            type="text"
            id="cena"
            name="cena"
            value={cena}
            onChange={(e) => setCena(e.target.value)}
          />
          <br />

          <label htmlFor="naziv">Naziv:</label><br />
          <input
            type="text"
            id="naziv"
            name="naziv"
            value={naziv}
            onChange={(e) => setNaziv(e.target.value)}
          />
          <br />

          <label htmlFor="opis">Opis:</label><br />
          <input
            type="text"
            id="opis"
            name="opis"
            value={opis}
            onChange={(e) => setOpis(e.target.value)}
          />
          <br />

          <label htmlFor="url">URL:</label><br />
          <input
            type="text"
            id="url"
            name="url"
            value={url}
            onChange={(e) => setUrl(e.target.value)}
          />
          <br />

          <label htmlFor="vrednost1">{labelVrednost1}</label><br />
          <input
            type="text"
            id="vrednost1"
            name="vrednost1"
            value={vrednost1}
            onChange={(e) => setVrednost1(e.target.value)}
          />
          <br />

          <label htmlFor="vrednost2">{labelVrednost2}</label><br />
          <input
            type="text"
            id="vrednost2"
            name="vrednost2"
            value={vrednost2}
            onChange={(e) => setVrednost2(e.target.value)}
          />
          <br />

          <label htmlFor="vrednost3">{labelVrednost3}</label><br />
          <input
            type="text"
            id="vrednost3"
            name="vrednost3"
            value={vrednost3}
            onChange={(e) => setVrednost3(e.target.value)}
          />
          <br />

          <button type="submit">Dodaj komponento</button>
        </form>
      </div>
      <Footer />
    </div>
  );
};

export default Maintainer;
