import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { cadastrarUsuario } from '../api';
import './forms.css';

function SignUp({ onSignUp }) {
  const [name, setName]         = useState('');
  const [email, setEmail]       = useState('');
  const [password, setPassword] = useState('');
  const [error, setError]       = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!name || !email || !password) {
      setError('Preencha todos os campos.');
      return;
    }
    setError('');

    // Chama a função enviada por props (de App.jsx). Se onSignUp() retornar true, redireciona.
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
    <div className="form-container">
      <h2>Cadastro</h2>

      {error && <div className="error-message">{error}</div>}

      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="signup-name">Nome:</label>
          <input
            id="signup-name"
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>

        <div>
          <label htmlFor="signup-email">Email:</label>
          <input
            id="signup-email"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>

        <div>
          <label htmlFor="signup-password">Senha:</label>
          <input
            id="signup-password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>

        <button type="submit">Cadastrar</button>
      </form>

      <p style={{ marginTop: '1rem', textAlign: 'center' }}>
        Já tem conta?{' '}
        <Link to="/login" style={{ color: '#5674fc', textDecoration: 'none' }}>
          Faça login
        </Link>
      </p>
    </div>
  );
}

export default SignUp;
