// src/App.jsx
import React from 'react';
import { Routes, Route, Link } from 'react-router-dom';
import './App.css'
import Home from './pages/Home';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import ForgotPassword from './pages/ForgotPassword';

function App() {
  // Exemplo simplificado: você mantém aqui as funções onLogin e onSignUp
  const handleLogin = ({ email, password }) => {
    if (email === 'admin@exemplo.com' && password === '123456') {
      return true;
    }
    return false;
  };

  const handleSignUp = ({ name, email, password }) => {
    if (email !== 'jaexiste@dominio.com') {
      return true;
    }
    return false;
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
        <nav style={{ marginLeft: 'auto' }}>
          <Link
            to="/login"
            style={{ marginRight: '1rem', color: '#5674fc', textDecoration: 'none' }}
          >
            Login
          </Link>
          <Link
            to="/signup"
            style={{ color: '#5674fc', textDecoration: 'none' }}
          >
            Cadastro
          </Link>
        </nav>
      </header>

      {/* ======= ÁREA PRINCIPAL COM ROTAS ======= */}
      <main style={{ minHeight: '70vh', padding: '2rem 1rem' }}>
        <Routes>
          <Route path="/" element={<Home />} />

          <Route path="/login" element={<Login onLogin={handleLogin} />} />

          <Route path="/signup" element={<SignUp onSignUp={handleSignUp} />} />

          {/* Nova rota para “Esqueci a senha” */}
          <Route path="/forgot-password" element={<ForgotPassword />} />

          {/* Opcional: rota “catch-all” (404) */}
          {/* <Route path="*" element={<NotFound />} /> */}
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