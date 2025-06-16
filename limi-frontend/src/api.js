const API_BASE = 'http://localhost:8080';

export async function cadastrarUsuario({ nome, username, email, senha }) {
  const response = await fetch(`${API_BASE}/usuarios/cadastro`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ nome, username, email, senha })
  });

  if (!response.ok) {
    const data = await response.json().catch(() => ({}));
    throw new Error(data.error || 'Erro ao cadastrar usuário');
  }

  return await response.json();
}

export async function loginUsuario({ email, senha }) {
  const response = await fetch(`${API_BASE}/usuarios/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, senha })
  });

  if (!response.ok) {
    const data = await response.json().catch(() => ({}));
    throw new Error(data.error || 'Erro no login');
  }

  return await response.json(); // deve conter { token: "..." }
}
