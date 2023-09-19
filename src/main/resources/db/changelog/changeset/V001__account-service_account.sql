CREATE TABLE balance (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    account_number varchar(20) NOT NULL,
    current_authorization_balance numeric,
    current_actual_balance numeric,
    created_at timestamptz DEFAULT current_timestamp NOT NULL,
    updated_at timestamptz DEFAULT current_timestamp NOT NULL,
    version bigint DEFAULT 0 NOT NULL,

    FOREIGN KEY (account_number) REFERENCES account(number)
);
