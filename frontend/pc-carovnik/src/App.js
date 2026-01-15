import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import Login from './components/Login';
import Register from './components/Register';
import Components from './components/Components';
import ChooseCType from './components/ChooseCType';

import Maintainer from './components/Maintainer';
import Configuration from './components/Configuration';
import 'bootstrap/dist/css/bootstrap.min.css'; // Dodal react bootstrap import

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/components" element={<Components />} />
          <Route path="/choosetype" element={<ChooseCType />} />
          <Route path="/maintainer" element={<Maintainer />} />
          <Route path="/configuration" element={<Configuration />} />
        </Routes>
      </BrowserRouter>  
    </div>
  );
}

export default App;
