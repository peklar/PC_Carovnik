import React, { useState } from 'react';
import { register } from '../services/api';
import { useNavigate } from 'react-router-dom';
import '../assets/Register.css';

const Register = () => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      
      const response = await register({
        ime: username,
        email,
        geslo: password,
      });
      navigate('/');

      console.log(response.data);
    } catch (error) {
      console.error(error.response.data);
      alert(error.response.data)
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center min-vh-100">
      <div className="m-5 p-4 border rounded shadow-lg">
        <h2 className="text-center mb-4">Registrirajte se tukaj!</h2>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="form-control"
              placeholder="Uporabniško ime"
            />
          </div>
          <div className="form-group">
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="form-control"
              placeholder="E-pošta"
            />
          </div>
          <div className="form-group">
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="form-control"
              placeholder="Geslo"
            />
          </div>
          <button type="submit" className="btn btn-primary btn-block">
            Register
          </button>
        </form>
      </div>
    </div>
  );
};

export default Register;
