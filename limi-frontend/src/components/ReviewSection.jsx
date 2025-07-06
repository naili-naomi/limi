import React, { useState, useEffect } from 'react';
import Toast from './Toast';
import './ReviewSection.css';
import { getReviewsByBookId, addReview, updateReview, deleteReview } from '../api';

function ReviewSection({ bookId, isLoggedIn }) {
  const [reviews, setReviews] = useState([]);
  const [newReviewText, setNewReviewText] = useState('');
  const [newReviewRating, setNewReviewRating] = useState(0);
  const [editingReviewId, setEditingReviewId] = useState(null);
  const [editingReviewText, setEditingReviewText] = useState('');
  const [editingReviewRating, setEditingReviewRating] = useState(0);
  const [averageRating, setAverageRating] = useState(0);
  const [toast, setToast] = useState({ message: '', type: '' });

  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const fetchedReviews = await getReviewsByBookId(bookId);
        setReviews(fetchedReviews);
      } catch (error) {
        console.error('Error fetching reviews:', error);
        setToast({ message: 'Error fetching reviews', type: 'error' });
      }
    };

    if (bookId) {
      fetchReviews();
    }
  }, [bookId]);

  useEffect(() => {
    if (reviews.length > 0) {
      const totalRating = reviews.reduce((sum, review) => sum + review.nota, 0);
      setAverageRating(totalRating / reviews.length);
    } else {
      setAverageRating(0);
    }
  }, [reviews]);

  const handleReviewSubmit = async (e) => {
    e.preventDefault();
    if (newReviewText.trim() === '' || newReviewRating === 0) {
      alert('Please provide a rating and a comment.');
      return;
    }

    const token = localStorage.getItem('token');
    if (!token) {
      setToast({ message: 'Please log in to submit a review.', type: 'error' });
      return;
    }

    try {
      console.log('Submitting review for bookId:', bookId);
      console.log('Review data:', { comentario: newReviewText, nota: newReviewRating });
      console.log('Auth token:', token);
      const createdReview = await addReview(
        bookId,
        { comentario: newReviewText, nota: newReviewRating },
        token
      );
      console.log('Review submitted successfully, re-fetching reviews...');
      // Re-fetch all reviews to ensure the new review with username is displayed
      //const updatedReviews = await getReviewsByBookId(bookId);
      console.log('Fetched updated reviews:', updatedReviews);
      // Log the new review to confirm its presence
      console.log('New review should be in updatedReviews:', updatedReviews.find(r => r.comentario === newReviewText && r.nota === newReviewRating));
      setReviews(prev => [...prev, createdReview]);
      setNewReviewText('');
      setNewReviewRating(0);
      setToast({ message: 'Avaliação enviada com sucesso!', type: 'success' });
    } catch (error) {
      console.error('Error submitting review:', error);
      setToast({ message: 'Error submitting review', type: 'error' });
    }
  };

  const handleEditClick = (review) => {
    setEditingReviewId(review.id);
    setEditingReviewText(review.comentario);
    setEditingReviewRating(review.nota);
  };

  const handleDeleteClick = async (reviewId) => {
    if (window.confirm('Are you sure you want to delete this review?')) {
      const token = localStorage.getItem('token');
      if (!token) {
        setToast({ message: 'Please log in to delete a review.', type: 'error' });
        return;
      }
      try {
        await deleteReview(bookId, reviewId, token);
        setReviews(reviews.filter((review) => review.id !== reviewId));
        setToast({ message: 'Review deleted successfully!', type: 'success' });
      } catch (error) {
        console.error('Error deleting review:', error);
        setToast({ message: 'Error deleting review', type: 'error' });
      }
    }
  };

  const handleUpdateReview = async (e) => {
    e.preventDefault();
    if (editingReviewText.trim() === '' || editingReviewRating === 0) {
      alert('Please provide a rating and a comment.');
      return;
    }

    const token = localStorage.getItem('token');
    if (!token) {
      setToast({ message: 'Please log in to update a review.', type: 'error' });
      return;
    }

    try {
      const updatedReview = await updateReview(
        bookId,
        editingReviewId,
        { comentario: editingReviewText, nota: editingReviewRating },
        token
      );
      setReviews(
        reviews.map((review) =>
          review.id === editingReviewId ? { ...review, ...updatedReview } : review
        )
      );
      setEditingReviewId(null);
      setEditingReviewText('');
      setEditingReviewRating(0);
      setToast({ message: 'Review updated successfully!', type: 'success' });
    } catch (error) {
      console.error('Error updating review:', error);
      setToast({ message: 'Error updating review', type: 'error' });
    }
  };

  const renderStars = (rating) => {
    return (
      <div className="star-rating-display">
        {[...Array(5)].map((_, i) => (
          <span key={i} className={i < rating ? 'star-filled' : 'star-empty'}>★</span>
        ))}
      </div>
    );
  };

  return (
    <div className="review-section-container">
      <Toast message={toast.message} type={toast.type} onClose={() => setToast({ message: '', type: '' })} />
      <h3>Reviews</h3>
      <div className="average-rating">
        Average Rating: {averageRating.toFixed(1)} / 5 {renderStars(averageRating)}
      </div>
      <div className="reviews-list">
        {reviews.map((review) => (
          <div key={review.id} className="review-item">
            {editingReviewId === review.id ? (
              <form onSubmit={handleUpdateReview} className="edit-review-form">
                <div className="rating-input">
                  {[1, 2, 3, 4, 5].map((star) => (
                    <span
                      key={star}
                      className={star <= editingReviewRating ? 'star-filled' : 'star-empty'}
                      onClick={() => setEditingReviewRating(star)}
                    >
                      ★
                    </span>
                  ))}
                </div>
                <textarea
                  value={editingReviewText}
                  onChange={(e) => setEditingReviewText(e.target.value)}
                  rows="3"
                ></textarea>
                <button type="submit">Save</button>
                <button type="button" onClick={() => setEditingReviewId(null)}>Cancel</button>
              </form>
            ) : (
              <>
                <p><strong>{review.username}</strong> {renderStars(review.nota)}</p>
                <p>{review.comentario}</p>
                {isLoggedIn && ( // Only show edit/delete if logged in
                  <div className="review-actions">
                    <button onClick={() => handleEditClick(review)}>Edit</button>
                    <button onClick={() => handleDeleteClick(review.id)}>Delete</button>
                  </div>
                )}
              </>
            )}
          </div>
        ))}
      </div>

      {isLoggedIn ? (
        <form onSubmit={handleReviewSubmit} className="review-form">
          <h4>Add Your Review</h4>
          <div className="rating-input">
            {[1, 2, 3, 4, 5].map((star) => (
              <span
                key={star}
                className={star <= newReviewRating ? 'star-filled' : 'star-empty'}
                onClick={() => setNewReviewRating(star)}
              >
                ★
              </span>
            ))}
          </div>
          <textarea
            placeholder="Write your review..."
            value={newReviewText}
            onChange={(e) => setNewReviewText(e.target.value)}
            rows="4"
          ></textarea>
          <button type="submit">Submit Review</button>
        </form>
      ) : (
        <p className="login-prompt">Please log in to add a review.</p>
      )}
    </div>
  );
}

export default ReviewSection;
