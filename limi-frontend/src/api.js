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

export async function addBook(bookData, token) {
  const response = await fetch(`${API_BASE}/livros`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify(bookData)
  });

  if (!response.ok) {
    const data = await response.json().catch(() => ({}));
    throw new Error(data.error || 'Erro ao adicionar livro');
  }

  return await response.json();
}

export async function getLivros() {
  const response = await fetch(`${API_BASE}/livros`);
  if (!response.ok) {
    throw new Error('Erro ao buscar livros');
  }
  return await response.json();
}

export async function getLivroById(id) {
  const response = await fetch(`${API_BASE}/catalogo/${id}`);
  if (!response.ok) {
    throw new Error('Erro ao buscar detalhes do livro');
  }
  return await response.json();
}

export async function searchLivros(query) {
  const response = await fetch(`${API_BASE}/catalogo/search?query=${encodeURIComponent(query)}`);
  if (!response.ok) {
    throw new Error('Erro ao buscar livros');
  }
  return await response.json();
}

export async function getLivrosByGenero(genero) {
  const response = await fetch(`${API_BASE}/catalogo/genero/${encodeURIComponent(genero)}`);
  if (!response.ok) {
    throw new Error(`Erro ao buscar livros do gênero ${genero}`);
  }
  return await response.json();
}

export async function getAllGeneros() {
  const response = await fetch(`${API_BASE}/catalogo/generos`);
  if (!response.ok) {
    throw new Error('Erro ao buscar gêneros');
  }
  return await response.json();
}
