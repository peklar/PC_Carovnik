import React from 'react';

const ComponentLink = ({ href, backgroundImage, text }) => {
  const linkStyle = {
    backgroundImage: `url(${backgroundImage})`,
    backgroundPosition: 'center',
    backgroundRepeat: 'no-repeat',
    backgroundSize: 'cover',
    color: 'white',
    fontSize: 'x-large',
    fontWeight: '600',
  };

  return (
    <a
      href={href}
      className="btn btn-secondary"
      style={linkStyle}
      role="button"
    >
      {text}
    </a>
  );
};

export default ComponentLink;
