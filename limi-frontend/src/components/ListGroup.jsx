import React from 'react';
import './ListGroup.css';

function ListGroup({ items, heading, onSelectItem }) {
  return (
    <div className="list-group-container">
      <h1>{heading}</h1>
      {items.length === 0 && <p>No items found</p>}
      <ul className="list-group-grid">
        {items.map((item, index) => (
          <li
            key={index}
            className="list-group-item"
            onClick={() => onSelectItem(item)}
          >
            <div className="book-cover-placeholder">Cover</div>
            <div className="book-card-content">
              <h3 className="book-title">{item.title}</h3>
              <p className="book-author">{item.author}</p>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ListGroup;
