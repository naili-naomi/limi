import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { addBook } from '../api';
import './AddBookPage.css';

function AddBookPage() {
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [genres, setGenres] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    const token = localStorage.getItem('token');
    if (!token) {
      setError('Você precisa estar logado para adicionar um livro.');
      return;
    }

    const bookData = {
      titulo: title,
      generos: genres.split(',').map(g => g.trim()),
    };

    try {
      await addBook(bookData, token);
      setSuccess('Livro adicionado com sucesso!');
      setTitle('');
      setGenres('');
      navigate('/'); // Redirect to home or book details page
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="add-book-container">
      <div className="add-book-form">
        <h2>Adicionar Novo Livro</h2>
        {error && <div className="error-message">{error}</div>}
        {success && <div className="success-message">{success}</div>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="title">Título:</label>
            <input
              id="title"
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="genres">Gêneros (separados por vírgula):</label>
            <input
              id="genres"
              type="text"
              value={genres}
              onChange={(e) => setGenres(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="btn-add-book">Adicionar Livro</button>
        </form>
      </div>
    </div>
  );
}

export default AddBookPage;
