import React, { useState, useEffect } from 'react';
import './UserProfile.css';

function UserProfile() {
  const [user, setUser] = useState({ nome: '', email: '' });
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  useEffect(() => {
    const nome = localStorage.getItem('nome');
    const email = localStorage.getItem('email'); // Assumindo que o email é salvo no login
    if (nome) {
      setUser({ nome, email: email || 'email@exemplo.com' }); // Use um email de exemplo se não houver
    }
  }, []);

  const handlePasswordChange = (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    if (newPassword !== confirmPassword) {
      setError('As novas senhas não correspondem.');
      return;
    }
    if (newPassword.length < 6) {
      setError('A nova senha deve ter pelo menos 6 caracteres.');
      return;
    }

    // Lógica para chamar a API de mudança de senha aqui
    console.log('Mudando a senha...');

    setSuccess('Senha alterada com sucesso!');
    setCurrentPassword('');
    setNewPassword('');
    setConfirmPassword('');
  };

  return (
    <div className="user-profile-container">
      <div className="user-profile-card">
        <h2>Perfil do Usuário</h2>
        <div className="user-info">
          <p><strong>Nome:</strong> {user.nome}</p>
          <p><strong>Email:</strong> {user.email}</p>
        </div>

        <div className="change-password-section">
          <h3>Alterar Senha</h3>
          {error && <div className="error-message">{error}</div>}
          {success && <div className="success-message">{success}</div>}
          <form onSubmit={handlePasswordChange}>
            <div className="form-group">
              <label>Senha Atual:</label>
              <input 
                type="password" 
                value={currentPassword} 
                onChange={(e) => setCurrentPassword(e.target.value)} 
                required 
              />
            </div>
            <div className="form-group">
              <label>Nova Senha:</label>
              <input 
                type="password" 
                value={newPassword} 
                onChange={(e) => setNewPassword(e.target.value)} 
                required 
              />
            </div>
            <div className="form-group">
              <label>Confirmar Nova Senha:</label>
              <input 
                type="password" 
                value={confirmPassword} 
                onChange={(e) => setConfirmPassword(e.target.value)} 
                required 
              />
            </div>
            <button type="submit" className="btn-change-password">Alterar Senha</button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default UserProfile;
