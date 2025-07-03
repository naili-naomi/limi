import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { fetchBookDetails } from '../services/googleBooksApiService';
import ReviewSection from '../components/ReviewSection';
import './BookDetailsPage.css';

function BookDetailsPage() {
  const { title } = useParams();
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
        const details = await fetchBookDetails(title);
        if (details) {
          setBookDetails(details);
        } else {
          setError('Book details not found.');
        }
      } catch (err) {
        setError('Error fetching book details.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    if (title) {
      getBookDetails();
    }
  }, [title]);

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
        <img src={bookDetails.cover || 'https://via.placeholder.com/150x225.png?text=No+Cover'} alt={`${bookDetails.title} cover`} className="book-details-cover" />
        <div className="book-details-content">
          <h2 className="book-details-title">{bookDetails.title}</h2>
          <h3 className="book-details-author">by {bookDetails.author}</h3>
          <p className="book-details-info"><strong>Published:</strong> {bookDetails.publishedDate}</p>
          <p className="book-details-info"><strong>Publisher:</strong> {bookDetails.publisher}</p>
          <p className="book-details-info"><strong>Pages:</strong> {bookDetails.pageCount}</p>
          <p className="book-details-synopsis">{bookDetails.synopsis}</p>
        </div>
      </div>
      <ReviewSection bookTitle={bookDetails.title} isLoggedIn={isLoggedIn} />
    </div>
  );
}

export default BookDetailsPage;
