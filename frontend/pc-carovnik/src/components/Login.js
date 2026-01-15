import React, { useState } from 'react';
import { login } from '../services/api';
import { useNavigate } from 'react-router-dom'; // redir na home

const Login = () => {
  const [loginInfo, setLoginInfo] = useState({ ime: '', geslo: '' });
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginInfo((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (loginInfo.ime != '' || loginInfo.geslo != ''){
      console.log('Logging in with:', loginInfo);

      console.log(loginInfo);
      localStorage.setItem('ime', loginInfo.ime);

      navigate('/');
      }
      else{
        alert('Uporabniško ime ali geslo je napačno')
      }
    } catch (error) {
      console.error('Login error:', error);
      if (error.response) {
        console.error('Login error response:', error.response.data);
      }
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center min-vh-100">
      <div className="m-5 p-4 border rounded shadow-lg">
        <h2 className="text-center mb-2">Prijavite se tukaj!</h2>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <input
              type="text"
              name="ime"
              value={loginInfo.ime}
              onChange={handleChange}
              className="form-control"
              placeholder="Uporabniško ime"
            />
          </div>
          <div className="form-group">
            <input
              type="password"
              name="geslo"
              value={loginInfo.geslo}
              onChange={handleChange}
              className="form-control"
              placeholder="Geslo"
            />
          </div>
          <button type="submit" className="btn btn-primary btn-block">
            Login
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;
