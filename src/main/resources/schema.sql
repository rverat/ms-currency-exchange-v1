CREATE TABLE IF NOT EXISTS currency_exchange_entity (
    operation_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    amount DECIMAL(19, 4),
    converted_amount DECIMAL(19, 4),
    currency_from VARCHAR(255),
    currency_to VARCHAR(255),
    exchange_rate DECIMAL(19, 4),
    date_time TIMESTAMP
);
