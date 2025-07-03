import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { loginUsuario } from '../api';
import './Login.css';

function Login({ onLogin }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
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
      onLogin({ token, nome });

      navigate('/');
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="login-container">
      <div className="login-form">
        <h2>Login</h2>
        {error && <div className="error-message">{error}</div>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="login-email">Email:</label>
            <input
              id="login-email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="login-password">Senha:</label>
            <input
              id="login-password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="btn-login">Entrar</button>
        </form>
        <div className="login-links">
          <Link to="/forgot-password">Esqueci a senha</Link>
          <span>|</span>
          <Link to="/signup">Cadastre-se</Link>
        </div>
      </div>
    </div>
  );
}

export default Login;
