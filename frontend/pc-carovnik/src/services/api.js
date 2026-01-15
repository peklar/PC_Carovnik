import axios from 'axios';

const api = axios.create({
  baseURL: "http://localhost:8180/PC-Carovnik",
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json',
    Accept: 'application/json',
  },
});

export const login = (credentials) => {
  return api.post('/auth/login', credentials);
};

export const register = (userData) => {
  return api.post('/auth/register', userData);
};

export const komponenta = (componentData) => {
  return api.post('/komponenta', componentData);
};

export const konfiguracija = (id, naziv) => {
  return api.post('/konfiguracija/'+ id + "/" + naziv);
};

export const konfiguracijakomponenta = (id1,id2) => {
  return api.post('konfiguracija/dodajKomponento/'+ id1 +"/" +id2);
};

export const tipKomponente = async (type) => {
  try {
    const response = await api.get(`komponenta/Type/${type}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching tipKomponente:", error);
    throw error; 
  }
};

export const deletekomponenta = async (id) => {
  try {
    const response = await api.delete(`komponenta/${id}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching uporabnik:", error);
    throw error; 
  }
};

export const uporabnik = async (id) => {
  try {
    const response = await api.get(`uporabnik/${id}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching uporabnik:", error);
    throw error; 
  }
};

export const uporabnikIme = async (ime) => {
  try {
    const response = await api.get(`uporabnik/uporabnikIme/${ime}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching uporabnik:", error);
    throw error; 
  }
};

export const filteredKomponenta = (params) => {
  return axios({
    method: 'get',
    url: `http://localhost:8180/PC-Carovnik/komponenta/searchByEverything?type=${params.type}&minPrice=${params.minPrice}&maxPrice=${params.maxPrice}
    &minVrednost1=${params.minVrednost1}&maxVrednost1=${params.maxVrednost1}&minVrednost2=${params.minVrednost2}
    &maxVrednost2=${params.maxVrednost2}&minVrednost3=${params.minVrednost3}&maxVrednost3=${params.maxVrednost3}` ,
  })
};

export default api;
