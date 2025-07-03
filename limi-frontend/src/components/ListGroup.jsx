import React from 'react';
import BookCard from './BookCard';
import './ListGroup.css';
import { useNavigate } from 'react-router-dom';

function ListGroup({ items, heading }) {
  const navigate = useNavigate();

  const handleSelectItem = (item) => {
    navigate(`/book/${item.title}`);
  };

  return (
    <div className="list-group-container">
      <h1>{heading}</h1>
      {items.length === 0 && <p>No items found</p>}
      <ul className="list-group-grid">
        {items.map((item, index) => (
          <BookCard key={index} item={item} onSelectItem={handleSelectItem} />
        ))}
      </ul>
    </div>
  );
}

export default ListGroup;
