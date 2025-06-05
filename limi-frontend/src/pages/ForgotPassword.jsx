// src/pages/ForgotPassword.jsx
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './forms.css';

function ForgotPassword() {
  const [email, setEmail] = useState('');
  const [msg, setMsg]     = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!email) {
      setError('Por favor, informe seu e-mail.');
      return;
    }
    setError('');
    // Aqui você faria a chamada ao seu backend:
    //  fetch('/api/forgot-password', { method:'POST', body: JSON.stringify({ email }) })
    //  … receber resposta e exibir msg de sucesso
    setMsg('Se esse e-mail estiver cadastrado, você receberá instruções para resetar a senha.');
    // Opcional: você pode desabilitar o botão ou redirecionar após alguns segundos:
    // setTimeout(() => navigate('/login'), 3000);
  };

  return (
    <div className="form-container">
      <h2>Recuperar senha</h2>

      {error && <div className="error-message">{error}</div>}
      {msg   && <div className="error-message" style={{ background: '#eef8ee', color: '#2c662d', borderColor: '#b9e2b3' }}>{msg}</div>}

      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="forgot-email">Email:</label>
          <input
            id="forgot-email"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <button type="submit">Enviar instruções</button>
      </form>

      <p style={{ marginTop: '1rem', textAlign: 'center' }}>
        <Link to="/login" style={{ color: '#5674fc', textDecoration: 'none' }}>
          Voltar ao login
        </Link>
      </p>
    </div>
  );
}

export default ForgotPassword;
