// src/pages/Home.jsx
import React from 'react';
import ListGroup from '../components/ListGroup';

function Home({ items }) {
  return (
    <div>
      <h2>Livros</h2>
      <ListGroup items={items} />
    </div>
  );
}

export default Home;
