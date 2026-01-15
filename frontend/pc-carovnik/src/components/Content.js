import React from 'react';

const Content = () => {
  const contentStyle = {
    minHeight: '130vh',
    display: 'flex',
    color: 'black',
    flexDirection: 'column',
    paddingTop: '70px',
    alignItems: 'center',
    fontSize: '1.5rem',
    fontWeight: '500',
  };
  const sectionStyle = {
    backgroundColor: 'rgba(0, 0, 0, 0.50)',
    borderRadius: '10px',
    padding: '20px',
    margin: '10px 0',
    width: '60%',
    textAlign: 'center',
  };

  const textStyle = {
    color: 'white',
    fontSize: '1.5rem',
    fontWeight: '500',
  };

  return (
    <div className="fluid-container" style={contentStyle}>
      <h1>Dobrodošli v PC Čarovnik</h1>
      <div style={sectionStyle}>
        <p style={textStyle}>
          Dobrodošli v PC Čarovnik, vašem najboljšem viru za izbiro in
          sestavljanje računalniških konfiguracij po meri. S PC Čarovnikom lahko
          enostavno brskate po številnih računalniških komponentah, jih
          primerjate in ustvarite svoj popoln računalnik.
        </p>
      </div>

      <div style={sectionStyle}>
        <p style={textStyle}>
          Ponujamo najnovejše in najboljše komponente iz sveta računalništva.
          Naš konfigurator je preprost za uporabo, tudi če ste začetnik. Ne
          boste izgubili svojih konfiguracij, saj jih lahko shranite za
          prihodnjo uporabo.
        </p>
      </div>

      <div style={sectionStyle}>
        <p style={textStyle}>
          Pridobite informacije o najnovejših tehnoloških novostih in trendih.
          Sledite nam za redne posodobitve in nasvete strokovnjakov.
        </p>
      </div>

      <div style={sectionStyle}>
        <p style={textStyle}>
          Odkrijte našo obsežno podporo in skupnost, ki vam lahko pomaga pri
          vseh vprašanjih in težavah. Postanite del PC Čarovnik skupnosti danes!
        </p>
      </div>
    </div>
  );
};

export default Content;
