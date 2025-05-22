import { useState } from 'react';

function SignUp({ onSignUp }) {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // Aqui você pode integrar com sua API de cadastro
    if (!name || !email || !password) {
      setError('Preencha todos os campos.');
      return;
    }
    setError('');
    if (onSignUp) onSignUp({ name, email, password });
  };

  return (
    <div className="signup-container">
      <h2>Cadastro</h2>
      {error && <p style={{color:'red'}}>{error}</p>}
      <form onSubmit={handleSubmit}>
        <div>
          <label>Nome:</label>
          <input type="text" value={name}
                 onChange={e => setName(e.target.value)}
                 required />
        </div>
        <div>
          <label>Email:</label>
          <input type="email" value={email}
                 onChange={e => setEmail(e.target.value)}
                 required />
        </div>
        <div>
          <label>Senha:</label>
          <input type="password" value={password}
                 onChange={e => setPassword(e.target.value)}
                 required />
        </div>
        <button type="submit">Cadastrar</button>
      </form>
      <p>Já tem conta? <a href="/login">Faça login</a></p>
    </div>
  );
}

export default SignUp;