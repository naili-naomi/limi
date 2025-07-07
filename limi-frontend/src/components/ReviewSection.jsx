import React, { useState, useEffect } from 'react';
import Toast from './Toast';
import './ReviewSection.css';
import { getReviewsByBookId, addReview, updateReview, deleteReview, decodeJwtToken } from '../api';

function ReviewSection({ bookId, isLoggedIn }) {
  const [reviews, setReviews] = useState([]);
  const [newReviewText, setNewReviewText] = useState('');
  const [newReviewRating, setNewReviewRating] = useState(0);
  const [editingReviewId, setEditingReviewId] = useState(null);
  const [editingReviewText, setEditingReviewText] = useState('');
  const [editingReviewRating, setEditingReviewRating] = useState(0);
  const [averageRating, setAverageRating] = useState(0);
  const [toast, setToast] = useState({ message: '', type: '' });
  const [currentUserId, setCurrentUserId] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken = decodeJwtToken(token);
      if (decodedToken) {
        setCurrentUserId(decodedToken.userId);
      }
    }

    const fetchReviews = async () => {
      try {
        const fetchedReviews = await getReviewsByBookId(bookId);
        setReviews(fetchedReviews);
      } catch (error) {
        console.error('Erro ao buscar avaliações:', error);
        setToast({ message: 'Erro ao buscar avaliações', type: 'error' });
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
      alert('Por favor, forneça uma nota e um comentário.');
      return;
    }

    const token = localStorage.getItem('token');
    if (!token) {
      setToast({ message: 'Por favor, faça login para enviar uma avaliação.', type: 'error' });
      return;
    }

    try {
      console.log('Submitting review for bookId:', bookId);
      console.log('Review data:', { comentario: newReviewText, nota: newReviewRating });
      console.log('Auth token:', token);

    // 1. Enviar a nova review
    await addReview(
      bookId,
      { comentario: newReviewText, nota: newReviewRating },
      token
    );

    // 2. Recarregar as reviews com pequeno delay
    await new Promise(resolve => setTimeout(resolve, 300)); // Pequeno delay

    // 3. Buscar reviews atualizadas
    const updatedReviews = await getReviewsByBookId(bookId);
    setReviews(updatedReviews);

    // 4. Resetar formulário
    setNewReviewText('');
    setNewReviewRating(0);
    setToast({ message: 'Avaliação enviada com sucesso!', type: 'success' });
  } catch (error) {
    console.error('Erro ao enviar avaliação:', error);
    setToast({ message: 'Erro ao enviar avaliação', type: 'error' });
  }
  };

  const handleEditClick = (review) => {
    setEditingReviewId(review.id);
    setEditingReviewText(review.comentario);
    setEditingReviewRating(review.nota);
  };

  const handleDeleteClick = async (reviewId) => {
    if (window.confirm('Tem certeza que deseja deletar esta avaliação?')) {
      const token = localStorage.getItem('token');
      if (!token) {
        setToast({ message: 'Por favor, faça login para deletar uma avaliação.', type: 'error' });
        return;
      }
      try {
        await deleteReview(bookId, reviewId, token);
        setReviews(reviews.filter((review) => review.id !== reviewId));
        setToast({ message: 'Avaliação deletada com sucesso!', type: 'success' });
      } catch (error) {
        console.error('Erro ao deletar avaliação:', error);
        setToast({ message: 'Erro ao deletar avaliação', type: 'error' });
      }
    }
  };

  const handleUpdateReview = async (e) => {
    e.preventDefault();
    if (editingReviewText.trim() === '' || editingReviewRating === 0) {
      alert('Por favor, forneça uma nota e um comentário.');
      return;
    }

    const token = localStorage.getItem('token');
    if (!token) {
      setToast({ message: 'Por favor, faça login para atualizar uma avaliação.', type: 'error' });
      return;
    }

    try {
      console.log('Updating review:', editingReviewId);
      console.log('Updated data:', { comentario: editingReviewText, nota: editingReviewRating });
      console.log('Auth token:', token);
      const updatedReview = await updateReview(
        bookId,
        editingReviewId,
        { comentario: editingReviewText, nota: editingReviewRating },
        token
      );
      console.log('Review updated successfully:', updatedReview);
      setReviews(
        reviews.map((review) =>
          review.id === editingReviewId ? { ...review, ...updatedReview } : review
        )
      );
      setEditingReviewId(null);
      setEditingReviewText('');
      setEditingReviewRating(0);
      setToast({ message: 'Avaliação atualizada com sucesso!', type: 'success' });
    } catch (error) {
      console.error('Erro ao atualizar avaliação:', error);
      setToast({ message: 'Erro ao atualizar avaliação', type: 'error' });
    }
  };

  const handleLikeClick = async (reviewId) => {
    const token = localStorage.getItem('token');
    if (!token) {
      setToast({ message: 'Por favor, faça login para curtir uma avaliação.', type: 'error' });
      return;
    }

    const isLiked = likedReviews.has(reviewId);

    try {
      if (isLiked) {
        // Unlike
        await removeLike(reviewId, token);
        setLikedReviews(prev => {
          const newSet = new Set(prev);
          newSet.delete(reviewId);
          return newSet;
        });
      } else {
        // Like
        await addLike(reviewId, token);
        setLikedReviews(prev => new Set(prev).add(reviewId));
      }
    } catch (error) {
      console.error('Erro ao curtir avaliação:', error);
      setToast({ message: 'Erro ao curtir avaliação', type: 'error' });
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
      <h3>Avaliações</h3>
      <div className="average-rating">
        Nota Média: {averageRating.toFixed(1)} / 5 {renderStars(averageRating)}
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
                <button type="submit">Salvar</button>
                <button type="button" onClick={() => setEditingReviewId(null)}>Cancelar</button>
              </form>
            ) : (
              <>
                <p><strong>{review.username}</strong> {renderStars(review.nota)}</p>
                <p>{review.comentario}</p>
                <div class="review-actions">
                  <button onClick={() => handleLikeClick(review.id)}>Curtir</button>
                  {isLoggedIn && currentUserId === review.userId && ( // Only show edit/delete if logged in AND current user is the author
                    <>
                      <button onClick={() => handleEditClick(review)}>Editar</button>
                      <button onClick={() => handleDeleteClick(review.id)}>Deletar</button>
                    </>
                  )}
                </div>
              </>
            )}
          </div>
        ))}
      </div>

      {isLoggedIn ? (
        <form onSubmit={handleReviewSubmit} className="review-form">
          <h4>Adicione Sua Avaliação</h4>
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
            placeholder="Escreva sua avaliação..."
            value={newReviewText}
            onChange={(e) => setNewReviewText(e.target.value)}
            rows="4"
          ></textarea>
          <button type="submit">Enviar Avaliação</button>
        </form>
      ) : (
        <p className="login-prompt">Por favor, faça login para adicionar uma avaliação.</p>
      )}
    </div>
  );
}

export default ReviewSection;
