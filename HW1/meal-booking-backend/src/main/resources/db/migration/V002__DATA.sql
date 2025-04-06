INSERT INTO bookings (email, restaurant, week, day_index, status, created_at)
VALUES ('john.doe@example.com', 'Castro', '13', 1, 'CONFIRMED', '2025-04-01 10:00:00'),
       ('jane.smith@example.com', 'Santiago', '13', 2, 'CONFIRMED', '2025-04-02 11:00:00'),
       ('alice.jones@example.com', 'Castro', '13', 3, 'CONFIRMED', '2025-04-03 12:00:00');

SELECT * FROM bookings;