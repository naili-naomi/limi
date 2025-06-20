// src/App.jsx
import React, { useEffect, useState } from 'react';
import { Routes, Route, Link, useNavigate } from 'react-router-dom';
import './App.css';
import Home from './pages/Home';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import ForgotPassword from './pages/ForgotPassword';
import logo from './assets/logo_horizontal_transparente.png';

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
      <header>
        <Link to="/">
          <img
            src={logo}
            alt="Logo"
            className="logo"
          />
        </Link>

        {/* ======= BARRA DE PESQUISA ======= */}
        <form className="search-form" onSubmit={e => { e.preventDefault(); /* Adicione lógica de busca aqui */ }}>
          <input
            type="text"
            className="search-input"
            placeholder="Pesquisar livros..."
            // value={searchTerm}
            // onChange={e => setSearchTerm(e.target.value)}
          />
          <button type="submit" className="search-btn">Buscar</button>
        </form>

        {/* ======= NAVEGAÇÃO DINÂMICA ======= */}
        <nav className="nav-header">
          {!logado ? (
            <>
              <Link to="/login" className="nav-link">
                Login
              </Link>
              <h3> | </h3>
              <Link to="/signup" className="nav-link">
                Cadastro
              </Link>
            </>
          ) : (
            <>
              <span className="nav-bemvindo">Bem-vindo, {nome}</span>
              <button onClick={handleLogout} className="nav-sair">
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
      <footer >
        © {new Date().getFullYear()} Limi. Todos os direitos reservados.
      </footer>
    </div>
  );
}

export default App;
