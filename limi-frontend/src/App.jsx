import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Message from './Message'
import ListGroup from './components/ListGroup'

function App() {
  return (
    <div>
      <Message />
      <ListGroup />
    </div>
  );
}

export default App
