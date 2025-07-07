import React, { useState, useEffect } from 'react';
import { getFavorites } from '../api';
import './UserProfile.css';

function UserProfile() {
  const [user, setUser] = useState({ nome: '', email: '' });
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [activeSection, setActiveSection] = useState('personal-data'); // Default para dados pessoais
  const [favoriteBooks, setFavoriteBooks] = useState([]);

  useEffect(() => {
    const nome = localStorage.getItem('nome');
    const email = localStorage.getItem('email');
    if (nome) {
      setUser({ nome, email: email || 'email@exemplo.com' });
    }
  }, []);

  useEffect(() => {
    if (activeSection === 'favorites') {
      const fetchFavorites = async () => {
        try {
          const token = localStorage.getItem('token');
          if (token) {
            const favorites = await getFavorites(token);
            setFavoriteBooks(favorites);
          }
        } catch (err) {
          console.error('Erro ao buscar favoritos:', err);
          setError('Erro ao carregar favoritos.');
        }
      };
      fetchFavorites();
    }
  }, [activeSection]);

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

    console.log('Mudando a senha...');

    setSuccess('Senha alterada com sucesso!');
    setCurrentPassword('');
    setNewPassword('');
    setConfirmPassword('');
  };

  const renderSection = () => {
    switch (activeSection) {
      case 'personal-data':
        return (
          <div className="personal-data-section">
            <h2>Dados Pessoais</h2>
            <div className="user-info">
              <p><strong>Nome:</strong> {user.nome}</p>
              <p><strong>Email:</strong> {user.email}</p>
            </div>
          </div>
        );
      case 'change-password':
        return (
          <div className="change-password-section-content">
            <h2>Alterar Senha</h2>
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
        );
      case 'favorites':
        return (
          <div className="favorites-section">
            <h2>Meus Favoritos</h2>
            {favoriteBooks.length > 0 ? (
              <div className="favorite-books-list">
                {favoriteBooks.map(book => (
                  <div key={book.id} className="favorite-book-item">
                    <img src={book.urlImagem || 'https://via.placeholder.com/100x150.png?text=Sem+Capa'} alt={book.titulo} />
                    <div className="favorite-book-info">
                      <h4>{book.titulo}</h4>
                      <p>{book.autor}</p>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <p>Você ainda não adicionou nenhum livro aos favoritos.</p>
            )}
          </div>
        );
      default:
        return null;
    }
  };

  return (
    <div className="user-profile-page">
      <div className="list-group-container user-profile-sidebar-override">
        <ul className="list-group-grid">
          <li className={activeSection === 'personal-data' ? 'list-group-item active' : 'list-group-item'} onClick={() => setActiveSection('personal-data')}>Dados Pessoais</li>
          <li className={activeSection === 'change-password' ? 'list-group-item active' : 'list-group-item'} onClick={() => setActiveSection('change-password')}>Alterar Senha</li>
          <li className={activeSection === 'favorites' ? 'list-group-item active' : 'list-group-item'} onClick={() => setActiveSection('favorites')}>Favoritos</li>
        </ul>
      </div>
      <div className="user-profile-content">
        {renderSection()}
      </div>
    </div>
  );
}

export default UserProfile;
