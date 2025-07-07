import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getLivroById, decodeJwtToken, deleteBook } from '../api';
import ReviewSection from '../components/ReviewSection';
import './BookDetailsPage.css';

function BookDetailsPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [bookDetails, setBookDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isLoggedIn, setIsLoggedIn] = useState(false); // State to track login status
  const [isAdmin, setIsAdmin] = useState(false);

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
          id: details.id, // Adicionado o ID para uso na exclusão
          titulo: details.titulo,
          autor: details.autor,
          anoPublicacao: details.anoPublicacao,
          sinopse: details.sinopse,
          urlImagem: details.urlImagem
        });
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
          navigate('/'); // Redireciona para a página inicial
        } else {
          alert('Você precisa estar logado para deletar um livro.');
        }
      } catch (err) {
        console.error('Erro ao deletar livro:', err);
        alert(`Erro ao deletar livro: ${err.message}`);
      }
    }
  };

  if (loading) {
    return <div className="book-details-loading">Loading book details...</div>;
  }

  if (error) {
    return <div className="book-details-error">Error: {error}</div>;
  }

  if (!bookDetails) {
    return <div className="book-details-not-found">No book details available.</div>;
  }

  return (
    <div className="book-details-container">
      <div className="book-details-card">
        <img src={bookDetails.urlImagem || 'https://via.placeholder.com/150x225.png?text=No+Cover'} alt={`${bookDetails.titulo} cover`} className="book-details-cover" />
        <div className="book-details-content">
          <h2 className="book-details-title">{bookDetails.titulo}</h2>
          <h3 className="book-details-author">by {bookDetails.autor}</h3>
          <p className="book-details-info"><strong>Published:</strong> {bookDetails.anoPublicacao}</p>
          <p className="book-details-synopsis">{bookDetails.sinopse}</p>
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