import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { cadastrarUsuario } from '../api';
import './SignUp.css';

function SignUp({ onSignUp }) {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!name || !email || !password) {
      setError('Preencha todos os campos.');
      return;
    }
    setError('');

    try {
      await cadastrarUsuario({
        nome: name,
        username: name.toLowerCase().replace(/\s+/g, ''),
        email,
        senha: password
      });
      navigate('/login');
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="signup-container">
      <div className="signup-form">
        <h2>Cadastro</h2>
        {error && <div className="error-message">{error}</div>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="signup-name">Nome:</label>
            <input
              id="signup-name"
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="signup-email">Email:</label>
            <input
              id="signup-email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="signup-password">Senha:</label>
            <input
              id="signup-password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="btn-signup">Cadastrar</button>
        </form>
        <div className="signup-links">
          <p>Já tem conta? <Link to="/login">Faça login</Link></p>
        </div>
      </div>
    </div>
  );
} 

export default SignUp;
