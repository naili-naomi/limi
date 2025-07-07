import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { resetPassword } from '../api';
import './ResetPassword.css'; // Vamos criar este CSS

function ResetPassword() {
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [msg, setMsg] = useState('');
  const [error, setError] = useState('');
  const [token, setToken] = useState(null);
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    const tokenFromUrl = queryParams.get('token');
    if (tokenFromUrl) {
      setToken(tokenFromUrl);
    } else {
      setError('Token de redefinição não encontrado na URL.');
    }
  }, [location.search]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!newPassword || !confirmPassword) {
      setError('Por favor, preencha todos os campos.');
      return;
    }
    if (newPassword !== confirmPassword) {
      setError('As senhas não coincidem.');
      return;
    }
    if (!token) {
      setError('Token de redefinição ausente.');
      return;
    }

    setError('');
    setMsg('');

    try {
      const response = await resetPassword(token, newPassword);
      setMsg(response.message || 'Senha redefinida com sucesso!');
      setTimeout(() => navigate('/login'), 3000);
    } catch (err) {
      setError(err.message || 'Erro ao redefinir senha.');
    }
  };

  return (
    <div className="reset-password-container">
      <div className="reset-password-form">
        <h2>Redefinir Senha</h2>
        {error && <div className="error-message">{error}</div>}
        {msg && <div className="success-message">{msg}</div>}
        {!token && !error && <div className="info-message">Aguardando token na URL...</div>}
        {token && (
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="new-password">Nova Senha:</label>
              <input
                id="new-password"
                type="password"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="confirm-password">Confirmar Senha:</label>
              <input
                id="confirm-password"
                type="password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
              />
            </div>
            <button type="submit" className="btn-reset-password">Redefinir Senha</button>
          </form>
        )}
        <div className="reset-password-links">
          <Link to="/login">Voltar ao login</Link>
        </div>
      </div>
    </div>
  );
}

export default ResetPassword;
