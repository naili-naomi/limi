import React, { useEffect } from 'react';
import './Toast.css';

function Toast({ message, type, onClose }) {
  useEffect(() => {
    // Only set a timer if there's a message to show.
    if (message) {
      const timer = setTimeout(() => {
        onClose();
      }, 3000); // Auto-close after 3 seconds

      return () => {
        clearTimeout(timer);
      };
    }
  }, [message, onClose]); // Rerun the effect when the message changes.

  if (!message) {
    return null;
  }

  return (
    // The 'show' class is added to trigger the CSS for visibility.
    <div className={`toast-container ${type} show`}>
      <div className="toast-message">{message}</div>
      <button onClick={onClose} className="toast-close-btn">×</button>
    </div>
  );
}

export default Toast;
