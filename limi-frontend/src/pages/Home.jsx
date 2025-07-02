// src/pages/Home.jsx
import React from 'react';
import ListGroup from '../components/ListGroup';

function Home() {
  const books = [
    { title: "Capitães de Areia", author: "Jorge Amado" },
    { title: "Dom Casmurro", author: "Machado de Assis" },
    { title: "Harry Potter e a Pedra Filosofal", author: "J.K. Rowling" },
    { title: "Senhor dos Anéis: A Sociedade do Anel", author: "J.R.R. Tolkien" },
    { title: "O Exorcista", author: "William Peter Blatty" },
    { title: "O Cemitério", author: "Stephen King" },
    { title: "Orgulho e Preconceito", author: "Jane Austen" },
    { title: "Duna", author: "Frank Herbert" },
    { title: "O Assassinato de Roger Ackroyd", author: "Agatha Christie" },
    { title: "Longa Caminhada até a Liberdade", author: "Nelson Mandela" },
    { title: "Sapiens: Uma Breve História da Humanidade", author: "Yuval Noah Harari" },
    { title: "Guerra e Paz", author: "Liev Tolstói" },
    { title: "Cem Sonetos de Amor", author: "Pablo Neruda" },
    { title: "O Poder do Hábito", author: "Charles Duhigg" },
    { title: "Garota Exemplar", author: "Gillian Flynn" }
  ];

  const handleSelectItem = (item) => {
    console.log(item);
  };

  return (
    <div>
      <ListGroup
        items={books}
        heading="Catálogo"
        onSelectItem={handleSelectItem}
      />
    </div>
  );
}

export default Home;
