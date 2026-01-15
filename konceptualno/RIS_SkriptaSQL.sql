USE pc_carovnik;


DROP PROCEDURE IF EXISTS napolniKomponente;

DELIMITER $$

CREATE PROCEDURE napolniKomponente()
BEGIN
  DECLARE counter INT DEFAULT 1;

  -- Filling komponenta table
  SET counter = 1;
  WHILE counter <= 100 DO
    INSERT INTO komponenta (tip_komponente, naziv, cena, datum_izdaje, opis, vrednost1, vrednost2, vrednost3)
    VALUES (
      CASE FLOOR(RAND() * 9)
        WHEN 0 THEN 'GPU'
        WHEN 1 THEN 'CPU'
        WHEN 2 THEN 'RAM'
        WHEN 3 THEN 'HDD'
        WHEN 4 THEN 'SSD'
        WHEN 5 THEN 'PSU'
        WHEN 6 THEN 'MOTHERBOARD'
        WHEN 7 THEN 'ACCESSORY'
      END,
      CONCAT('Komponenta', counter),
      ROUND(RAND() * (1000 - 200) + 200, 2),
      DATE_ADD('2022-01-01', INTERVAL ROUND(RAND() * 365) DAY),
      CONCAT('Opis', counter),
      ROUND(RAND() * 9 + 1, 2),
      ROUND(RAND() * (2000 - 1000) + 1000, 2),
      ROUND(RAND() * (200 - 100) + 100, 2)
    );
    SET counter = counter + 1;
  END WHILE;

END $$

DELIMITER ;


