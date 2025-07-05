import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getLivroById } from '../api';
import ReviewSection from '../components/ReviewSection';
import './BookDetailsPage.css';

function BookDetailsPage() {
  const { id } = useParams();
  const [bookDetails, setBookDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isLoggedIn, setIsLoggedIn] = useState(false); // State to track login status

  useEffect(() => {
    // Check login status (e.g., from localStorage or context)
    const token = localStorage.getItem('token');
    setIsLoggedIn(!!token);

    const getBookDetails = async () => {
      try {
        setLoading(true);
        const details = await getLivroById(id);
        setBookDetails({
          titulo: livro.titulo,
          autor: livro.autor,
          anoPublicacao: livro.anoPublicacao,
          sinopse: livro.sinopse,
          urlImagem: livro.urlImagem
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
        </div>
      </div>
      <ReviewSection bookTitle={bookDetails.titulo} isLoggedIn={isLoggedIn} />
    </div>
  );
}

export default BookDetailsPage;