import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getLivroById, decodeJwtToken, deleteBook, addFavorite, removeFavorite, isFavorite } from '../api';
import ReviewSection from '../components/ReviewSection';
import './BookDetailsPage.css';

function BookDetailsPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [bookDetails, setBookDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);
  const [isBookFavorite, setIsBookFavorite] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    setIsLoggedIn(!!token);

    if (token) {
      const decodedToken = decodeJwtToken(token);
      console.log('Token decoded:', decodedToken);
      if (decodedToken && decodedToken.email === 'adminlimi@gmail.com') {
        setIsAdmin(true);
        console.log('User is admin.');
      } else {
        setIsAdmin(false);
        console.log('User is NOT admin. Email:', decodedToken?.email);
      }
    } else {
      console.log('No token found. User is not logged in.');
    }

    const getBookDetails = async () => {
      try {
        setLoading(true);
        const details = await getLivroById(id);
        setBookDetails({
          id: details.id,
          titulo: details.titulo,
          autor: details.autor,
          anoPublicacao: details.anoPublicacao,
          sinopse: details.sinopse,
          urlImagem: details.urlImagem
        });

        if (token) {
          const favoriteStatus = await isFavorite(details.id, token);
          setIsBookFavorite(favoriteStatus);
        }

      } catch (err) {
        setError('Erro ao buscar detalhes do livro');
      } finally {
        setLoading(false);
      }
    };

    if (id) {
      getBookDetails();
    }
  }, [id]);

  const handleDelete = async () => {
    if (window.confirm('Tem certeza que deseja deletar este livro?')) {
      try {
        const token = localStorage.getItem('token');
        if (token) {
          await deleteBook(bookDetails.id, token);
          alert('Livro deletado com sucesso!');
          navigate('/');
        } else {
          alert('Você precisa estar logado para deletar um livro.');
        }
      } catch (err) {
        console.error('Erro ao deletar livro:', err);
        alert(`Erro ao deletar livro: ${err.message}`);
      }
    }
  };

  const handleFavoriteToggle = async () => {
    const token = localStorage.getItem('token');
    if (!token) {
      alert('Você precisa estar logado para adicionar/remover favoritos.');
      return;
    }

    try {
      if (isBookFavorite) {
        await removeFavorite(bookDetails.id, token);
        alert('Livro removido dos favoritos!');
      } else {
        await addFavorite(bookDetails.id, token);
        alert('Livro adicionado aos favoritos!');
      }
      setIsBookFavorite(!isBookFavorite);
    } catch (err) {
      console.error('Erro ao atualizar favoritos:', err);
      alert(`Erro ao atualizar favoritos: ${err.message}`);
    }
  };

  if (loading) {
    return <div className="book-details-loading">Carregando detalhes do livro...</div>;
  }

  if (error) {
    return <div className="book-details-error">Erro: {error}</div>;
  }

  if (!bookDetails) {
    return <div className="book-details-not-found">Nenhum detalhe do livro disponível.</div>;
  }

  return (
    <div className="book-details-container">
      <div className="book-details-card">
        <img src={bookDetails.urlImagem || 'https://via.placeholder.com/150x225.png?text=Sem+Capa'} alt={`${bookDetails.titulo} cover`} className="book-details-cover" />
        <div className="book-details-content">
          <h2 className="book-details-title">{bookDetails.titulo}</h2>
          <h3 className="book-details-author">por {bookDetails.autor}</h3>
          <p className="book-details-info"><strong>Publicado em:</strong> {bookDetails.anoPublicacao}</p>
          <p className="book-details-synopsis">{bookDetails.sinopse}</p>
          {isLoggedIn && (
            <button onClick={handleFavoriteToggle} className="favorite-book-btn">
              {isBookFavorite ? 'Remover dos Favoritos' : 'Adicionar aos Favoritos'}
            </button>
          )}
          {isAdmin && (
            <button onClick={handleDelete} className="delete-book-btn">
              Deletar Livro
            </button>
          )}
        </div>
      </div>
      <ReviewSection bookId={id} isLoggedIn={isLoggedIn} />
    </div>
  );
}

export default BookDetailsPage;