CREATE TABLE bookings
(
    id         SERIAL PRIMARY KEY,
    email      VARCHAR(255) NOT NULL,
    restaurant VARCHAR(255) NOT NULL,
    week       VARCHAR(255)  NOT NULL,
    day_index  INT          NOT NULL,
    status     VARCHAR(255)  NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);