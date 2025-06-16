// src/App.jsx
import React, { useEffect, useState } from 'react';
import { Routes, Route, Link, useNavigate } from 'react-router-dom';
import './App.css';
import Home from './pages/Home';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import ForgotPassword from './pages/ForgotPassword';

function App() {
  const [logado, setLogado] = useState(false);
  const [nome, setNome] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    const nomeSalvo = localStorage.getItem('nome');
    if (token && nomeSalvo) {
      setLogado(true);
      setNome(nomeSalvo);
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('nome');
    setLogado(false);
    setNome('');
    navigate('/login');
  };

    const handleLogin = ({ token, nome }) => {
      localStorage.setItem('token', token);
      localStorage.setItem('nome', nome);
      setLogado(true);
      setNome(nome);
    };

  const handleSignUp = ({ name, email, password }) => {
    if (email !== 'jaexiste@dominio.com') {
      return true;
    }
    return false;
  };

    const handleLoginSuccess = (token, nomeDoUsuario) => {
      localStorage.setItem('token', token);
      localStorage.setItem('nome', nomeDoUsuario);
      setLogado(true);
      setNome(nomeDoUsuario);
      navigate('/'); // redireciona pra home ou onde quiser
    };

  return (
    <div className="app-container">
      {/* ======= HEADER ======= */}
      <header
        style={{
          display: 'flex',
          alignItems: 'center',
          padding: '1rem',
          borderBottom: '1px solid #ddd',
          background: '#fafafa',
        }}
      >
        <img
          src="./assets/nova_logo.jpeg"
          alt="Logo"
          style={{ height: 48, marginRight: 16 }}
        />
        <h1 style={{ fontSize: 24, margin: 0 }}>
          <Link to="/" style={{ textDecoration: 'none', color: '#222' }}>
            Catálogo de Livros
          </Link>
        </h1>

        {/* ======= NAVEGAÇÃO DINÂMICA ======= */}
        <nav style={{ marginLeft: 'auto', display: 'flex', gap: '1rem', alignItems: 'center' }}>
          {!logado ? (
            <>
              <Link to="/login" style={{ color: '#5674fc', textDecoration: 'none' }}>
                Login
              </Link>
              <Link to="/signup" style={{ color: '#5674fc', textDecoration: 'none' }}>
                Cadastro
              </Link>
            </>
          ) : (
            <>
              <span style={{ color: '#333' }}>Bem-vindo, {nome}</span>
              <button onClick={handleLogout} style={{ background: 'none', border: 'none', color: '#5674fc', cursor: 'pointer' }}>
                Sair
              </button>
            </>
          )}
        </nav>
      </header>

      {/* ======= ROTAS ======= */}
      <main style={{ minHeight: '70vh', padding: '2rem 1rem' }}>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login onLogin={handleLogin} />} />
          <Route path="/signup" element={<SignUp onSignUp={handleSignUp} />} />
          <Route path="/forgot-password" element={<ForgotPassword />} />
        </Routes>
      </main>

      {/* ======= FOOTER ======= */}
      <footer
        style={{
          borderTop: '1px solid #ddd',
          background: '#fafafa',
          padding: '1rem',
          textAlign: 'center',
          fontSize: 14,
        }}
      >
        © {new Date().getFullYear()} Catálogo de Livros
      </footer>
    </div>
  );
}

export default App;
