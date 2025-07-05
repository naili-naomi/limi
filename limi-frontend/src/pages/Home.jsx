import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import ListGroup from '../components/ListGroup';
import { getLivros, searchLivros, getAllGeneros, getLivrosByGenero } from '../api';
import './Home.css';

function Home() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [generos, setGeneros] = useState([]);
  const [selectedGenero, setSelectedGenero] = useState(null);
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const fetchGeneros = async () => {
      try {
        const data = await getAllGeneros();
        setGeneros(data);
      } catch (err) {
        console.error("Erro ao buscar gêneros:", err);
      }
    };
    fetchGeneros();
  }, []);

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        setLoading(true);
        const queryParams = new URLSearchParams(location.search);
        const searchTerm = queryParams.get('search');

        let data;
        if (searchTerm) {
          data = await searchLivros(searchTerm);
        } else if (selectedGenero) {
          data = await getLivrosByGenero(selectedGenero);
        } else {
          data = await getLivros();
        }
        setBooks(data);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchBooks();
  }, [location.search, selectedGenero]);

  const handleSelectItem = (book) => {
    navigate(`/book/${book.id}`);
  };

  const handleGeneroClick = (genero) => {
    setSelectedGenero(genero);
    navigate(`/?genero=${genero}`);
  };

  if (loading) {
    return <div>Carregando...</div>;
  }

  if (error) {
    return <div>Erro: {error}</div>;
  }

  return (
    <div className="home-container">
      <div className="genero-list">
        <h3>Gêneros</h3>
        <ul>
          <li
            className={!selectedGenero ? 'active' : ''}
            onClick={() => {
              setSelectedGenero(null);
              navigate('/');
            }}
          >
            Todos
          </li>
          {generos.map((genero) => (
            <li
              key={genero}
              className={selectedGenero === genero ? 'active' : ''}
              onClick={() => handleGeneroClick(genero)}
            >
              {genero}
            </li>
          ))}
        </ul>
      </div>
      <div className="book-list">
        <ListGroup
          items={books}
          heading={selectedGenero ? `Livros de ${selectedGenero}` : "Catálogo"}
          onSelectItem={handleSelectItem}
        />
      </div>
    </div>
  );
}

export default Home;