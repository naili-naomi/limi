import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Message from './Message'
import ListGroup from './components/ListGroup'

function App() {
  return (
    <div className="app-container">
      <header style={{
        display: 'flex',
        alignItems: 'center',
        padding: '1rem',
        borderBottom: '1px solid #ddd',
        background: '#fafafa'
      }}>
        <img src="../nova_logo.jpeg" alt="Logo" style={{ height: 48, marginRight: 16 }} />
        <h1 style={{ fontSize: 24, margin: 0 }}>Catálogo de Livros</h1>
      </header>
      <main style={{ minHeight: '70vh', padding: '2rem 1rem' }}>
        <Message />
        <ListGroup />
      </main>
      <footer style={{
        borderTop: '1px solid #ddd',
        background: '#fafafa',
        padding: '1rem',
        textAlign: 'center',
        fontSize: 14
      }}>
        © {new Date().getFullYear()} Catálogo de Livros
      </footer>
    </div>
  );
}

export default App
