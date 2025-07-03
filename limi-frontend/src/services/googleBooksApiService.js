const API_URL = 'https://www.googleapis.com/books/v1/volumes';

export const fetchBookCover = async (title) => {
  try {
    const response = await fetch(`${API_URL}?q=intitle:${encodeURIComponent(title)}&maxResults=1`);
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    const data = await response.json();
    if (data.items && data.items.length > 0) {
      const book = data.items[0];
      return book.volumeInfo.imageLinks?.thumbnail || null;
    }
    return null;
  } catch (error) {
    console.error('Error fetching book cover:', error);
    return null;
  }
};

export const fetchBookDetails = async (title) => {
  try {
    const response = await fetch(`${API_URL}?q=intitle:${encodeURIComponent(title)}&maxResults=1`);
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    const data = await response.json();
    if (data.items && data.items.length > 0) {
      const book = data.items[0];
      const volumeInfo = book.volumeInfo;
      return {
        title: volumeInfo.title,
        author: volumeInfo.authors ? volumeInfo.authors.join(', ') : 'Unknown',
        publishedDate: volumeInfo.publishedDate || 'N/A',
        publisher: volumeInfo.publisher || 'N/A',
        pageCount: volumeInfo.pageCount || 'N/A',
        synopsis: volumeInfo.description || 'No synopsis available.',
        cover: volumeInfo.imageLinks?.thumbnail || null,
      };
    }
    return null;
  } catch (error) {
    console.error('Error fetching book details:', error);
    return null;
  }
};
