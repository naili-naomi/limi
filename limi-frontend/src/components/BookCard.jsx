import React, { useState, useEffect } from 'react';
import { fetchBookCover } from '../services/googleBooksApiService';
import './ListGroup.css';

const BookCard = ({ item, onSelectItem }) => {
  const [coverUrl, setCoverUrl] = useState(null);
  const placeholderUrl = 'https://via.placeholder.com/150x225.png?text=No+Cover';

  useEffect(() => {
    const getCover = async () => {
      const url = await fetchBookCover(item.title);
      setCoverUrl(url);
    };
    getCover();
  }, [item.title]);

  return (
    <li className="list-group-item" onClick={() => onSelectItem(item)}>
      <img src={coverUrl || placeholderUrl} alt={`${item.title} cover`} className="book-cover" />
      <div className="book-card-content">
        <h3 className="book-title">{item.title}</h3>
        <p className="book-author">{item.author}</p>
      </div>
    </li>
  );
};

export default BookCard;
