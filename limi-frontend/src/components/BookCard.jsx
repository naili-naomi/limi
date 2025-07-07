import React from 'react';
import './ListGroup.css';

const BookCard = ({ item, onSelectItem }) => {
  const placeholderUrl = 'https://via.placeholder.com/150x225.png?text=No+Cover';

  return (
    <li className="list-group-item" onClick={() => onSelectItem(item)}>
      <img src={item.urlImagem || placeholderUrl} alt={`${item.titulo} cover`} className="book-cover" />
      <div className="book-card-content">
        <h3 className="book-title">{item.titulo}</h3>
        <p className="book-author">{item.autor}</p>
      </div>
    </li>
  );
};

export default BookCard;
