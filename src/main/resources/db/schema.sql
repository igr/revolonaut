
CREATE TABLE IF NOT EXISTS currency (
  id INT PRIMARY KEY,
  code CHAR(3) NOT NULL,
  symbol CHAR(1) NOT NULL,
  name VARCHAR(30) NOT NULL
);


CREATE TABLE IF NOT EXISTS bank_account (
  id IDENTITY,
  iban CHAR(34) NOT NULL,
  owner VARCHAR(256) NOT NULL,
  balance DECIMAL(19,4) NOT NULL,
  currency_id INT NOT NULL,

  FOREIGN KEY(currency_id) REFERENCES currency(id),
);

CREATE INDEX ndx_bank_account_iban ON bank_account(iban);



CREATE TABLE IF NOT EXISTS transfer_status (
  id INT PRIMARY KEY,
  name CHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS transfer_log (
    id IDENTITY,
    from_account_id BIGINT NOT NULL,
    to_account_id BIGINT NOT NULL,
    amount DECIMAL(19,4) NOT NULL,
    currency_id INT NOT NULL,
    date TIMESTAMP NOT NULL,
    status_id INT NOT NULL,
    note VARCHAR(1000),

    FOREIGN KEY(from_account_id) REFERENCES bank_account(id),
    FOREIGN KEY(to_account_id) REFERENCES bank_account(id),
    FOREIGN KEY(currency_id) REFERENCES currency(id),
    FOREIGN KEY(status_id) REFERENCES transfer_status(id)
);