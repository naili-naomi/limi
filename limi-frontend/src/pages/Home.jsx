import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ListGroup from '../components/ListGroup';
import { getLivros } from '../api';

function Home() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const data = await getLivros();
        setBooks(data);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchBooks();
  }, []);

  const handleSelectItem = (book) => {
    navigate(`/livros/${book.id}`);
  };

  if (loading) {
    return <div>Carregando...</div>;
  }

  if (error) {
    return <div>Erro: {error}</div>;
  }

  return (
    <div>
      <ListGroup
        items={books}
        heading="Catálogo"
        onSelectItem={handleSelectItem}
      />
    </div>
  );
}

export default Home;