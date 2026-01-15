import React, { useState, useEffect } from 'react';
import Header from './Header';
import Navbar from './Navbar';
import Footer from './Footer';
import { konfiguracija, konfiguracijakomponenta, tipKomponente, uporabnikIme } from '../services/api'; 
import { uporabnik } from '../services/api';
import '../assets/Home.css';
import '../assets/Configuration.css';
import backgroundSlika from '../assets/images/wizard.png';

const Configuration = () => {

  const [selectedGPU, setSelectedGPU] = useState(null);
  const [selectedCPU, setSelectedCPU] = useState(null);
  const [selectedPSU, setSelectedPSU] = useState(null);
  const [selectedMOTHERBOARD, setSelectedMOTHERBOARD] = useState(null);
  const [selectedSSD, setSelectedSSD] = useState(null);
  const [selectedHDD, setSelectedHDD] = useState(null);
  const [selectedRAM, setSelectedRAM] = useState(null);
  const [selectedCASE, setSelectedCASE] = useState(null);

  const [gpuComponents, setGPUComponents] = useState([]);
  const [cpuComponents, setCPUComponents] = useState([]);
  const [psuComponents, setPSUComponents] = useState([]);
  const [motherboardComponents, setMOTHERBOARDComponents] = useState([]);
  const [ssdComponents, setSSDComponents] = useState([]);
  const [hddComponents, setHDDComponents] = useState([]);
  const [ramComponents, setRAMComponents] = useState([]);
  const [caseComponents, setCASEComponents] = useState([]);

  const [totalPrice, setTotalPrice] = useState(0);

  const [configurationName, setconfigurationName] = useState("Vnesi naziv konfiguracije");

  const calculateTotalPrice = () => {
    const components = [selectedGPU, selectedCPU, selectedPSU, selectedMOTHERBOARD, selectedSSD, selectedHDD, selectedRAM, selectedCASE];

    const total = components.reduce((acc, component) => {
      return acc + (component?.cena || 0);
    }, 0);

    setTotalPrice(total);
  };

  async function createConfiguration() {
    if(!localStorage.getItem("ime")){
      alert("če želite shraniti konfiguracijo, se registrirajte :)")
    }
    else if(configurationName == ""){
      alert("podajte ime konfiguraciji :(")
    }
    else{
    const uporabnik = await uporabnikIme(localStorage.getItem("ime"));
    console.log(uporabnik);
    const response = await konfiguracija(uporabnik[0].id, configurationName);
    const konId = response.data.id;

    if(selectedCPU && konId)
    konfiguracijakomponenta(konId, selectedCPU.id);
    if(selectedGPU && konId)
    konfiguracijakomponenta(konId, selectedGPU.id);
    if(selectedPSU && konId)
    konfiguracijakomponenta(konId, selectedPSU.id);
    if(selectedMOTHERBOARD && konId)
    konfiguracijakomponenta(konId, selectedMOTHERBOARD.id);
    if(selectedSSD && konId)
    konfiguracijakomponenta(konId, selectedSSD.id);
    if(selectedHDD && konId)
    konfiguracijakomponenta(konId, selectedHDD.id);
    if(selectedRAM && konId)
    konfiguracijakomponenta(konId, selectedRAM.id);
    if(selectedCASE && konId)
    konfiguracijakomponenta(konId, selectedCASE.id);
    }

  }

  const maintainerStyle = {
    minHeight: '110vh',
    minWidth: '60%',
    marginTop: '5%',
  };

  useEffect(() => {
    tipKomponente('GPU')
      .then(response => {
        const data = response;
        if (Array.isArray(data)) {
          setGPUComponents(data);
        } else {
          console.error('Invalid data format received for GPU components:', data);
        }
      })
      .catch(error => console.error('Error fetching GPU components:', error));

      tipKomponente('PSU')
      .then(response => {
        const data = response;
        if (Array.isArray(data)) {
          setPSUComponents(data);
        } else {
          console.error('Invalid data format received for PSU components:', data);
        }
      })
      .catch(error => console.error('Error fetching PSU components:', error));

      tipKomponente('MOTHERBOARD')
      .then(response => {
        const data = response;
        if (Array.isArray(data)) {
          setMOTHERBOARDComponents(data);
        } else {
          console.error('Invalid data format received for MOTHERBOARD components:', data);
        }
      })
      .catch(error => console.error('Error fetching MOTHERBOARD components:', error));

      tipKomponente('SSD')
      .then(response => {
        const data = response;
        if (Array.isArray(data)) {
          setSSDComponents(data);
        } else {
          console.error('Invalid data format received for SSD components:', data);
        }
      })
      .catch(error => console.error('Error fetching SSD components:', error));

      tipKomponente('HDD')
      .then(response => {
        const data = response;
        if (Array.isArray(data)) {
          setHDDComponents(data);
        } else {
          console.error('Invalid data format received for HDD components:', data);
        }
      })
      .catch(error => console.error('Error fetching HDD components:', error));

      tipKomponente('RAM')
      .then(response => {
        const data = response;
        if (Array.isArray(data)) {
          setRAMComponents(data);
        } else {
          console.error('Invalid data format received for RAM components:', data);
        }
      })
      .catch(error => console.error('Error fetching RAM components:', error));

      tipKomponente('CASE')
      .then(response => {
        const data = response;
        if (Array.isArray(data)) {
          setCASEComponents(data);
        } else {
          console.error('Invalid data format received for CASE components:', data);
        }
      })
      .catch(error => console.error('Error fetching CASE components:', error));

    tipKomponente('CPU')
      .then(response => {
        const data = response;
        if (Array.isArray(data)) {
          setCPUComponents(data);
        } else {
          console.error('Invalid data format received for CPU components:', data);
        }
      })
      .catch(error => console.error('Error fetching CPU components:', error));
  }, []);

  const handleGPUDropdownChange = (event) => {
    const selectedGPUName = event.target.value;
    const selectedGPUComponent = gpuComponents.find(component => component.naziv === selectedGPUName);
    setSelectedGPU(selectedGPUComponent);
  };

  const handleCPUDropdownChange = (event) => {
    const selectedCPUName = event.target.value;
    const selectedCPUComponent = cpuComponents.find(component => component.naziv === selectedCPUName);
    setSelectedCPU(selectedCPUComponent);
  };

  const handlePSUDropdownChange = (event) => {
    const selectedPSUName = event.target.value;
    const selectedPSUComponent = psuComponents.find(component => component.naziv === selectedPSUName);
    setSelectedPSU(selectedPSUComponent); 
  };
  
  const handleMOTHERBOARDDropdownChange = (event) => {
    const selectedMOTHERBOARDName = event.target.value;
    const selectedMOTHERBOARDComponent = motherboardComponents.find(component => component.naziv === selectedMOTHERBOARDName);
    setSelectedMOTHERBOARD(selectedMOTHERBOARDComponent); 
  };
  
  const handleSSDDropdownChange = (event) => {
    const selectedSSDName = event.target.value;
    const selectedSSDComponent = ssdComponents.find(component => component.naziv === selectedSSDName);
    setSelectedSSD(selectedSSDComponent); 
  };
  
  const handleHDDDropdownChange = (event) => {
    const selectedHDDName = event.target.value;
    const selectedHDDComponent = hddComponents.find(component => component.naziv === selectedHDDName);
    setSelectedHDD(selectedHDDComponent);
  };

  const handleRAMDropdownChange = (event) => {
    const selectedRAMName = event.target.value;
    const selectedRAMComponent = ramComponents.find(component => component.naziv === selectedRAMName);
    setSelectedRAM(selectedRAMComponent);
  };

  const handleCASEDropdownChange = (event) => {
    const selectedCASEName = event.target.value;
    const selectedCASEComponent = caseComponents.find(component => component.naziv === selectedCASEName);
    setSelectedCASE(selectedCASEComponent);
  };

  useEffect(() => {
    calculateTotalPrice();
  }, [selectedGPU, selectedCPU, selectedPSU, selectedMOTHERBOARD, selectedSSD, selectedHDD, selectedRAM, selectedCASE]);

  return (
    <div>
      <Header />
      <Navbar />
      <div style={maintainerStyle} className="configuration-container">
        <h2>Sestavi svoj računalnik:</h2>
        <table> 
          <thead>
            <tr>
              <th>Tip Komponente</th>
              <th>Naziv</th>
              <th>Opis</th>
              <th>Trenutna Cena</th>
              <th>Dobavljivo pri:</th>
              {/* Add more columns for other attributes */}
            </tr>
          </thead>
          <tbody>
            {/* GPU Row */}
            <tr>
              <td>Grafična kartica</td>
              <td>
                <select onChange={handleGPUDropdownChange}>
                  <option value="">Izberi GPU</option>
                  {gpuComponents.map(component => (
                    <option key={component.id} value={component.naziv}>{component.naziv}</option>
                  ))}
                </select>
              </td>
              <td>{selectedGPU?.opis || '-'}</td>
              <td>{selectedGPU?.cena || '-'}eur</td>
              <td>
                {selectedGPU?.url ? (
                  <a href={selectedGPU.url} target="_blank" rel="noopener noreferrer">
                    {selectedGPU.url}
                  </a>
                ) : '-'}
              </td>
              {/* Add more cells for other attributes */}
            </tr>

            {/* CPU Row */}
            <tr>
              <td>Procesor</td>
              <td>
                <select onChange={handleCPUDropdownChange}>
                  <option value="">Izberi CPU</option>
                  {cpuComponents.map(component => (
                    <option key={component.id} value={component.naziv}>{component.naziv}</option>
                  ))}
                </select>
              </td>
              <td>{selectedCPU?.opis || '-'}</td>
              <td>{selectedCPU?.cena || '-'}eur</td>
              <td>
                {selectedCPU?.url ? (
                  <a href={selectedCPU.url} target="_blank" rel="noopener noreferrer">
                    {selectedCPU.url}
                  </a>
                ) : '-'}
              </td>
              {/* Add more cells for other attributes */}
            </tr>
            {/* PSU Row */}
            <tr>
              <td>Napajalnik</td>
              <td>
                <select onChange={handlePSUDropdownChange}>
                  <option value="">Izberi PSU</option>
                  {psuComponents.map(component => (
                    <option key={component.id} value={component.naziv}>{component.naziv}</option>
                  ))}
                </select>
              </td>
              <td>{selectedPSU?.opis || '-'}</td>
              <td>{selectedPSU?.cena || '-'}eur</td>
              <td>
                {selectedPSU?.url ? (
                  <a href={selectedPSU.url} target="_blank" rel="noopener noreferrer">
                    {selectedPSU.url}
                  </a>
                ) : '-'}
              </td>
              {/* Add more cells for other attributes */}
            </tr>
             {/* MOTHERBOARD Row */}
             <tr>
              <td>Matična plošča</td>
              <td>
                <select onChange={handleMOTHERBOARDDropdownChange}>
                  <option value="">Izberi MOTHERBOARD</option>
                  {motherboardComponents.map(component => (
                    <option key={component.id} value={component.naziv}>{component.naziv}</option>
                  ))}
                </select>
              </td>
              <td>{selectedMOTHERBOARD?.opis || '-'}</td>
              <td>{selectedMOTHERBOARD?.cena || '-'}eur</td>
              <td>
                {selectedMOTHERBOARD?.url ? (
                  <a href={selectedMOTHERBOARD.url} target="_blank" rel="noopener noreferrer">
                    {selectedMOTHERBOARD.url}
                  </a>
                ) : '-'}
              </td>
              {/* Add more cells for other attributes */}
            </tr>
            {/* SSD Row */}
            <tr>
              <td>SSD</td>
              <td>
                <select onChange={handleSSDDropdownChange}>
                  <option value="">Izberi SSD</option>
                  {ssdComponents.map(component => (
                    <option key={component.id} value={component.naziv}>{component.naziv}</option>
                  ))}
                </select>
              </td>
              <td>{selectedSSD?.opis || '-'}</td>
              <td>{selectedSSD?.cena || '-'}eur</td>
              <td>
                {selectedSSD?.url ? (
                  <a href={selectedSSD.url} target="_blank" rel="noopener noreferrer">
                    {selectedSSD.url}
                  </a>
                ) : '-'}
              </td>
              {/* Add more cells for other attributes */}
            </tr>
             {/* HDD Row */}
             <tr>
              <td>Trdi disk</td>
              <td>
                <select onChange={handleHDDDropdownChange}>
                  <option value="">Izberi HDD</option>
                  {hddComponents.map(component => (
                    <option key={component.id} value={component.naziv}>{component.naziv}</option>
                  ))}
                </select>
              </td>
              <td>{selectedHDD?.opis || '-'}</td>
              <td>{selectedHDD?.cena || '-'}eur</td>
              <td>
                {selectedHDD?.url ? (
                  <a href={selectedHDD.url} target="_blank" rel="noopener noreferrer">
                    {selectedHDD.url}
                  </a>
                ) : '-'}
              </td>
              {/* Add more cells for other attributes */}
            </tr>
            {/* RAM Row */}
            <tr>
              <td>RAM</td>
              <td>
                <select onChange={handleRAMDropdownChange}>
                  <option value="">Izberi RAM</option>
                  {ramComponents.map(component => (
                    <option key={component.id} value={component.naziv}>{component.naziv}</option>
                  ))}
                </select>
              </td>
              <td>{selectedRAM?.opis || '-'}</td>
              <td>{selectedRAM?.cena || '-'}eur</td>
              <td>
                {selectedRAM?.url ? (
                  <a href={selectedRAM.url} target="_blank" rel="noopener noreferrer">
                    {selectedRAM.url}
                  </a>
                ) : '-'}
              </td>
              {/* Add more cells for other attributes */}
            </tr>
            {/* RAM Row */}
            <tr>
              <td>Ohišje</td>
              <td>
                <select onChange={handleCASEDropdownChange}>
                  <option value="">Izberi PC CASE</option>
                  {caseComponents.map(component => (
                    <option key={component.id} value={component.naziv}>{component.naziv}</option>
                  ))}
                </select>
              </td>
              <td>{selectedCASE?.opis || '-'}</td>
              <td>{selectedCASE?.cena || '-'}eur</td>
              <td>
                {selectedCASE?.url ? (
                  <a href={selectedCASE.url} target="_blank" rel="noopener noreferrer">
                    {selectedCASE.url}
                  </a>
                ) : '-'}
              </td>
              {/* Add more cells for other attributes */}
            </tr>
            <tr>
                <td></td>

            </tr>
          </tbody>
        </table>
        <div>
        </div>
        <h5>Cena računalnika: {totalPrice}eur</h5>
        <input value={configurationName} type="text" onChange={e => setconfigurationName(e.target.value)}></input>
        <div>
        <button className='button' onClick={createConfiguration}>Shrani konfiguracijo</button>
        </div>
      </div>
      
      <Footer />
    </div>
  );
};

export default Configuration;

      
