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
  console.log('Enviando livro para adicionar:', bookData);
  const response = await fetch(`${API_BASE}/livros/complementar`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify(bookData)
  });

  console.log('Resposta do servidor:', response.status);

  if (!response.ok) {
    const data = await response.json().catch(() => ({}));
    console.error('Erro ao adicionar livro:', data);
    throw new Error(data.titulo || 'Erro ao adicionar livro');
  }

  const json = await response.json();
  console.log('Livro adicionado com sucesso:', json);
  return json;
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

export async function getReviewsByBookId(bookId) {
  const response = await fetch(`${API_BASE}/api/livros/${bookId}/reviews`);
  if (!response.ok) {
    throw new Error('Erro ao buscar reviews');
  }
  return await response.json();
}

export async function addReview(bookId, reviewData, token) {
  const response = await fetch(`${API_BASE}/api/livros/${bookId}/reviews`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify(reviewData)
  });

  if (!response.ok) {
    const data = await response.json().catch(() => ({}));
    throw new Error(data.error || 'Erro ao adicionar review');
  }

  return await response.json();
}

export async function updateReview(bookId, reviewId, reviewData, token) {
  const response = await fetch(`${API_BASE}/api/livros/${bookId}/reviews/${reviewId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify(reviewData)
  });

  if (!response.ok) {
    const data = await response.json().catch(() => ({}));
    throw new Error(data.error || 'Erro ao atualizar review');
  }

  return await response.json();
}

export async function deleteReview(bookId, reviewId, token) {
  const response = await fetch(`${API_BASE}/api/livros/${bookId}/reviews/${reviewId}`, {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${token}`
    },
  });

  if (!response.ok) {
    const data = await response.json().catch(() => ({}));
    throw new Error(data.error || 'Erro ao deletar review');
  }

  return response.status === 204; // No Content
}

export async function deleteBook(bookId, token) {
  const response = await fetch(`${API_BASE}/livros/${bookId}`, {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${token}`
    },
  });

  if (!response.ok) {
    const data = await response.json().catch(() => ({}));
    throw new Error(data.error || 'Erro ao deletar livro');
  }

  return response.status === 204; // No Content
}

export function decodeJwtToken(token) {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
  } catch (error) {
    console.error('Error decoding JWT token:', error);
    return null;
  }
}

export async function forgotPassword(email) {
  const response = await fetch(`${API_BASE}/auth/forgot-password`, {
    method: 'POST',
    headers: { 'Content-Type': 'text/plain' }, // Mudar para text/plain
    body: email // Enviar o e-mail como texto puro
  });

  if (!response.ok) {
    const data = await response.text().catch(() => 'Erro desconhecido');
    throw new Error(data || 'Erro ao solicitar redefinição de senha');
  }

  return await response.text();
}

export async function resetPassword(token, newPassword) {
  const response = await fetch(`${API_BASE}/auth/reset-password`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ token, newPassword })
  });

  if (!response.ok) {
    const data = await response.text().catch(() => 'Erro desconhecido');
    throw new Error(data || 'Erro ao redefinir a senha');
  }

  return await response.text();
}

export const addLike = async (reviewId, token) => {
  const response = await fetch(`${API_BASE}/reviews/${reviewId}/like`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });
  if (!response.ok) {
    throw new Error('Failed to like review');
  }
};

export const removeLike = async (reviewId, token) => {
  const response = await fetch(`${API_BASE}/reviews/${reviewId}/like`, {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });
  if (!response.ok) {
    throw new Error('Failed to unlike review');
  }
};

export const getFavorites = async (token) => {
  const response = await fetch(`${API_BASE}/favorites`, {
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });
  if (!response.ok) {
    throw new Error('Failed to fetch favorites');
  }
  return await response.json();
};

export const addFavorite = async (livroId, token) => {
  const response = await fetch(`${API_BASE}/favorites/${livroId}`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });
  if (!response.ok) {
    throw new Error('Failed to add favorite');
  }
};

export const removeFavorite = async (livroId, token) => {
  const response = await fetch(`${API_BASE}/favorites/${livroId}`, {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });
  if (!response.ok) {
    throw new Error('Failed to remove favorite');
  }
};

export const isFavorite = async (livroId, token) => {
  const response = await fetch(`${API_BASE}/favorites/isFavorite/${livroId}`, {
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });
  if (!response.ok) {
    throw new Error('Failed to check favorite status');
  }
  return await response.json();
};
