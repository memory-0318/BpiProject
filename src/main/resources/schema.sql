CREATE TABLE IF NOT EXISTS currency_mapping
(
    pkey               INT AUTO_INCREMENT,
    hash_id            VARCHAR(128) NOT NULL,
    currency_code      VARCHAR(3),
    currency_name_zhtw VARCHAR(16),
    PRIMARY KEY (pkey),
    CONSTRAINT UC_CURRENCY_CODE UNIQUE (currency_code),
    CONSTRAINT UC_HASH_ID UNIQUE (hash_id)
);