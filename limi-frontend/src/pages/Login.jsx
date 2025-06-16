import React, { useState } from 'react';
import { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { loginUsuario } from '../api';
import './forms.css';

function Login({ onLogin }) {
  const [email, setEmail]       = useState('');
  const [password, setPassword] = useState('');
  const [error, setError]       = useState('');
  const navigate = useNavigate();


   const handleSubmit = async (e) => {
    e.preventDefault();



    if (!email || !password) {
      setError('Preencha todos os campos.');
      return;
    }
    setError('');

    try {
      const { token, nome } = await loginUsuario({ email, senha: password });
     onLogin({token, nome});

      navigate('/');
    } catch (err) {
      setError(err.message);
    }


  };

  return (
    <div className="form-container">
      <h2>Login</h2>

      {error && <div className="error-message">{error}</div>}

      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="login-email">Email:</label>
          <input
            id="login-email"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>

        <div>
          <label htmlFor="login-password">Senha:</label>
          <input
            id="login-password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>

        {/* Botão de Entrar */}
        <button type="submit">Entrar</button>

        {/* Link “Esqueci a senha” abaixo do botão */}
        <p style={{ marginTop: '0.5rem', textAlign: 'right' }}>
          <Link
            to="/forgot-password"
            style={{ fontSize: '0.95rem', color: '#5674fc', textDecoration: 'none' }}
          >
            Esqueci a senha
          </Link>
        </p>
      </form>

      <p style={{ marginTop: '1rem', textAlign: 'center' }}>
        Não tem conta?{' '}
        <Link to="/signup" style={{ color: '#5674fc', textDecoration: 'none' }}>
          Cadastre-se
        </Link>
      </p>
    </div>
  );
}

export default Login;
