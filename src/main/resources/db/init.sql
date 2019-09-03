
-- currency list

INSERT INTO
    currency(id, code, symbol, name)
VALUES
    (1, 'USD', '$', 'US Dollar'),
    (2, 'EUR', '€', 'Euro'),
    (3, 'GBP', '£', 'British Pound')
;

-- accounts

INSERT INTO
    bank_account(iban, owner, balance, currency_id)
VALUES
        ('RE0101', 'William Gibson', 100.49, 1),
        ('RE0202', 'Quentin Tarantino', 99.17, 1),
        ('RE0303', 'Richy Rich', 111804057.94, 2)
;

-- transfer statuses

INSERT INTO
    transfer_status(id, name)
VALUES
    (1, 'Started'),
    (0, 'Failed'),
    (200, 'Succeed')
;
