import React, { useState, useEffect } from 'react';
import Toast from './Toast';
import './ReviewSection.css';

function ReviewSection({ bookTitle, isLoggedIn }) {
  const [reviews, setReviews] = useState([]);
  const [newReviewText, setNewReviewText] = useState('');
  const [newReviewRating, setNewReviewRating] = useState(0);
  const [averageRating, setAverageRating] = useState(0);
  const [toast, setToast] = useState({ message: '', type: '' });

  useEffect(() => {
    // In a real application, you would fetch reviews for bookTitle from your backend
    // For now, we'll use mock data
    const mockReviews = [
      { id: 1, user: 'User1', rating: 4, comment: 'Great book!' },
      { id: 2, user: 'User2', rating: 5, comment: 'Loved it!' },
      { id: 3, user: 'User3', rating: 3, comment: 'It was okay.' },
    ];
    setReviews(mockReviews);
  }, [bookTitle]);

  useEffect(() => {
    if (reviews.length > 0) {
      const totalRating = reviews.reduce((sum, review) => sum + review.rating, 0);
      setAverageRating(totalRating / reviews.length);
    } else {
      setAverageRating(0);
    }
  }, [reviews]);

  const handleReviewSubmit = (e) => {
    e.preventDefault();
    if (newReviewText.trim() === '' || newReviewRating === 0) {
      alert('Please provide a rating and a comment.');
      return;
    }

    const newReview = {
      id: reviews.length + 1,
      user: 'CurrentUser', // Replace with actual logged-in user
      rating: newReviewRating,
      comment: newReviewText,
    };

    setReviews([...reviews, newReview]);
    setNewReviewText('');
    setNewReviewRating(0);
    setToast({ message: 'Avaliação enviada com sucesso!', type: 'success' });
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
            <p><strong>{review.user}</strong> {renderStars(review.rating)}</p>
            <p>{review.comment}</p>
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
