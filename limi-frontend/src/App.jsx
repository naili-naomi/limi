// src/App.jsx
import React, { useEffect, useState } from 'react';
import { Routes, Route, Link, useNavigate } from 'react-router-dom';
import './App.css';
import Home from './pages/Home';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import ListGroup from './components/ListGroup';
import ForgotPassword from './pages/ForgotPassword';
import ResetPassword from './pages/ResetPassword';
import BookDetailsPage from './pages/BookDetailsPage';
import AddBookPage from './pages/AddBookPage';
import UserProfile from './pages/UserProfile'; // Importe a nova página
import logo from './assets/logo_horizontal_transparente.png';
import './components/SearchDropdown.css'; // Importe o CSS do dropdown
import { searchLivros } from './api'; // Importe a função de busca

function App() {
  const [logado, setLogado] = useState(false);
  const [nome, setNome] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [showResults, setShowResults] = useState(false);
  const navigate = useNavigate();

  // Debounce function
  const debounce = (func, delay) => {
    let timeout;
    return function(...args) {
      const context = this;
      clearTimeout(timeout);
      timeout = setTimeout(() => func.apply(context, args), delay);
    };
  };

  const fetchSearchResults = async (query) => {
    console.log('fetchSearchResults called with query:', query);
    if (query.length > 2) { // Only search if query is at least 3 characters long
      try {
        const results = await searchLivros(query);
        console.log('Search results:', results);
        setSearchResults(results);
        setShowResults(true);
      } catch (error) {
        console.error('Error fetching search results:', error);
        setSearchResults([]);
        setShowResults(false);
      }
    } else {
      console.log('Query too short, not searching.');
      setSearchResults([]);
      setShowResults(false);
    }
  };

  const debouncedFetchSearchResults = debounce(fetchSearchResults, 300);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const nomeSalvo = localStorage.getItem('nome');
    if (token && nomeSalvo) {
      setLogado(true);
      setNome(nomeSalvo);
    }
  }, []);

  const handleSearch = (e) => {
    e.preventDefault();
    setShowResults(false); // Hide results when form is submitted
    navigate(`/?search=${searchTerm}`);
  };

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

  const handleBookClick = (bookId) => {
    setShowResults(false); // Hide results when a book is clicked
    if (bookId === 'add-book-static') {
      navigate('/add-book');
    } else {
      navigate(`/book/${bookId}`);
    }
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
        <form className="search-form" onSubmit={handleSearch}>
          <input
            type="text"
            className="search-input"
            placeholder="Pesquisar livros..."
            value={searchTerm}
            onChange={(e) => {
              console.log('Input onChange triggered:', e.target.value);
              setSearchTerm(e.target.value);
              debouncedFetchSearchResults(e.target.value);
            }}
            onFocus={() => setShowResults(true)} // Sempre mostra ao focar
            onBlur={() => setTimeout(() => setShowResults(false), 200)} // Aumentado o delay para 200ms
          />
          <button type="submit" className="search-btn">Buscar</button>
          {showResults && (
            <div className="search-results-dropdown">
              {searchResults.length > 0 && searchResults.map((book) => (
                <div key={book.id} className="search-result-item" onClick={() => handleBookClick(book.id)}>
                  {book.titulo}
                </div>
              ))}
              {searchTerm && (
                <div key="add-book-static" className="search-result-item add-book-item" onClick={() => handleBookClick('add-book-static')}>
                  Adicionar Livro
                </div>
              )}
            </div>
          )}
        </form>

        {/* ======= NAVEGAÇÃO DINÂMICA ======= */}
        <nav className="nav-header">
          {!logado ? (
            <>
              <Link to="/login" className="nav-link" style={{ color: '#a30045' }}>
                Login
              </Link>
              <h3 id='separator'> | </h3>
              <Link to="/signup" className="nav-link" style={{ color: '#a30045' }}>
                Cadastro
              </Link>
            </>
          ) : (
            <div className="user-controls">
              <Link to="/add-book" className="nav-link" title="Adicionar Livro" style={{ color: '#a30045', fontSize: '1.8rem', fontWeight: 'bold', textDecoration: 'none' }}>
                +
              </Link>
              <Link to="/profile" className="nav-link user-icon" title="Meu Perfil" style={{ color: '#a30045' }}>
                👤
              </Link>
              <button onClick={handleLogout} className="nav-sair">
                Sair
              </button>
            </div>
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
          <Route path="/reset-password" element={<ResetPassword />} />
          <Route path="/book/:id" element={<BookDetailsPage />} />
          <Route path="/add-book" element={<AddBookPage />} />
          <Route path="/profile" element={<UserProfile />} /> {/* Adicione a nova rota */}
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
