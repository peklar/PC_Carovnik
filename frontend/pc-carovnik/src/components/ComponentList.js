import React, { useState, useEffect } from 'react';

import { komponenta } from '../services/api';
import { filteredKomponenta } from '../services/api';

import { uporabnikIme } from '../services/api';

import { deletekomponenta } from '../services/api';


const ComponentList = (params) => {

    const [komponente, setKomponente] = useState(null);

    useEffect(() => {
      const fetchData = async () => {
        try {
          const response = await filteredKomponenta(params);
          const data = response.data;
          console.log(data);

          setKomponente(data);
        } catch (error) {
          console.error('Error fetching components:', error);
        }
      };
      
      fetchData();
    }, [params]);

    async function izbrisi(id){
      deletekomponenta(id);
    }

    const [isImeSet, setIsImeSet] = useState(false);
    useEffect(() => {
      const ime = localStorage.getItem('ime');
      setIsImeSet(!!ime);
      admincheck();
    }, []);

    const [isAdmin, setIsAdmin] = useState(false);

    async function admincheck() {
      if (isImeSet) {
        const uporabnik = await uporabnikIme(localStorage.getItem('ime'));
        setIsAdmin(uporabnik[0].isAdmin);
      }
    }

    console.log(komponente);
    if (komponente === null) {
      return <div>Loading...</div>;
    }

    if(komponente == []) {
        return <div>empty...</div>;
      }
      admincheck();

    return(
      <div>
      {komponente.map((komponent, index) => (
        <div key={index} className='component'>
        <div key={index} className="componentInfo">
        {isAdmin && (
          <div className="componentChange">
            <button onClick={() => izbrisi(komponent.id)}>DELETE</button>
          </div>
          )}
          <p id={`naziv${index + 1}`}>{komponent.naziv}</p>
          <p id={`cena${index + 1}`}>{komponent.cena}eur</p>
          <p id={`vrednost11${index + 1}`}>{komponent.vrednost1}</p>
          <p id={`vrednost21${index + 1}`}>{komponent.vrednost2}</p>
          <p id={`vrednost31${index + 1}`}>{komponent.vrednost3}</p>
        </div>
        </div>
      ))}
    </div>
    )
};



export default ComponentList;
