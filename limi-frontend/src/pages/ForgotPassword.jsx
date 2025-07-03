import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './ForgotPassword.css';

function ForgotPassword() {
  const [email, setEmail] = useState('');
  const [msg, setMsg] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!email) {
      setError('Por favor, informe seu e-mail.');
      return;
    }
    setError('');
    setMsg('Se esse e-mail estiver cadastrado, você receberá instruções para resetar a senha.');
  };

  return (
    <div className="forgot-password-container">
      <div className="forgot-password-form">
        <h2>Recuperar senha</h2>
        {error && <div className="error-message">{error}</div>}
        {msg && <div className="success-message">{msg}</div>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="forgot-email">Email:</label>
            <input
              id="forgot-email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="btn-forgot-password">Enviar instruções</button>
        </form>
        <div className="forgot-password-links">
          <Link to="/login">Voltar ao login</Link>
        </div>
      </div>
    </div>
  );
}

export default ForgotPassword;
