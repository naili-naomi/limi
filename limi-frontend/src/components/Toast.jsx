import React, { useEffect } from 'react';
import './Toast.css';

function Toast({ message, type, onClose }) {
  useEffect(() => {
    const timer = setTimeout(() => {
      onClose();
    }, 3000); // Auto-close after 3 seconds

    return () => {
      clearTimeout(timer);
    };
  }, [onClose]);

  if (!message) {
    return null;
  }

  return (
    <div className={`toast-container ${type}`}>
      <div className="toast-message">{message}</div>
      <button onClick={onClose} className="toast-close-btn">×</button>
    </div>
  );
}

export default Toast;
