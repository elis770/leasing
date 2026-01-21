INSERT INTO owners (first_name, last_name, descripcion, bank_account, photo_url)
VALUES (
  'John',
  'Doe',
  'Owner of multiple Ford vehicles',
  'BANK-123456',
  'https://img.owner/john.png'
);

INSERT INTO cars (brand, model, color, descripcion, photo_url, price_per_day, status, owner_id)
VALUES
(
  'Ford',
  'Focus',
  'Blue',
  'Economic and comfortable car',
  'https://img.car/focus.png',
  45.00,
  'ACTIVE',
  1
),
(
  'Ford',
  'Mustang',
  'Red',
  'Sport car currently unavailable',
  'https://img.car/mustang.png',
  120.00,
  'INACTIVE',
  1
);

INSERT INTO clients (first_name, last_name, phone)
VALUES
('Alice', 'Smith', '111-222-333'),
('Bob', 'Johnson', '222-333-444'),
('Charlie', 'Brown', '333-444-555');

INSERT INTO reservations (
  created_at,
  start_date,
  end_date,
  status,
  total_price,
  car_id,
  client_id
)
VALUES
(
  CURRENT_TIMESTAMP,
  '2026-01-10 10:00:00',
  '2026-01-12 10:00:00',
  'CONFIRMED',
  90.00,
  1,
  1
),
(
  CURRENT_TIMESTAMP,
  '2026-01-15 09:00:00',
  '2026-01-18 09:00:00',
  'CONFIRMED',
  135.00,
  1,
  2
),
(
  CURRENT_TIMESTAMP,
  '2026-01-20 12:00:00',
  '2026-01-22 12:00:00',
  'PENDING',
  90.00,
  1,
  3
);

INSERT INTO reviews (
  comment,
  rating,
  created_at,
  car_id,
  client_id
)
VALUES
(
  'Great car, very smooth ride!',
  5,
  CURRENT_TIMESTAMP,
  1,
  1
),
(
  'Good value for the price.',
  4,
  CURRENT_TIMESTAMP,
  1,
  2
),
(
  'Car was okay, but interior was not clean.',
  3,
  CURRENT_TIMESTAMP,
  1,
  3
);