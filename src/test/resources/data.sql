INSERT INTO country (name, flagImageUrl)
SELECT 'United States', 'https://example.com/us-flag.png'
WHERE NOT EXISTS (SELECT 1 FROM country WHERE id = 1);

INSERT INTO country (name, flagImageUrl)
SELECT 'United Kingdom', 'https://example.com/uk-flag.png'
WHERE NOT EXISTS (SELECT 2 FROM country WHERE id = 2);

INSERT INTO country (name, flagImageUrl)
SELECT 'Australia', 'https://example.com/au-flag.png'
WHERE NOT EXISTS (SELECT 3 FROM country WHERE id = 3);

INSERT INTO athlete (name, sport, countryId)
SELECT 'Michael Phelps', 'Swimming', 1
WHERE NOT EXISTS (SELECT 1 FROM athlete WHERE id = 1);

INSERT INTO athlete (name, sport, countryId)
SELECT 'Serena Williams', 'Tennis', 1
WHERE NOT EXISTS (SELECT 2 FROM athlete WHERE id = 2);

INSERT INTO athlete (name, sport, countryId)
SELECT 'Mo Farah', 'Long-distance running', 2
WHERE NOT EXISTS (SELECT 3 FROM athlete WHERE id = 3);

INSERT INTO athlete (name, sport, countryId)
SELECT 'Andy Murray', 'Tennis', 2
WHERE NOT EXISTS (SELECT 4 FROM athlete WHERE id = 4);

INSERT INTO athlete (name, sport, countryId)
SELECT 'Steve Smith', 'Cricket', 3
WHERE NOT EXISTS (SELECT 5 FROM athlete WHERE id = 5);

INSERT INTO athlete (name, sport, countryId)
SELECT 'Ellyse Perry', 'Cricket', 3
WHERE NOT EXISTS (SELECT 6 FROM athlete WHERE id = 6);