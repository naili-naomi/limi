import { useState } from 'react';

function Login({ onLogin }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // Aqui você pode integrar com sua API de autenticação
    if (!email || !password) {
      setError('Preencha todos os campos.');
      return;
    }
    setError('');
    // Exemplo de callback para autenticação
    if (onLogin) onLogin({ email, password });
  };

  return (
    <div className="login-container">
      <h2>Login</h2>
      {error && <p style={{color:'red'}}>{error}</p>}
      <form onSubmit={handleSubmit}>
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
        <button type="submit">Entrar</button>
      </form>
      <p>Não tem conta? <a href="/signup">Cadastre-se</a></p>
    </div>
  );
}

export default Login;