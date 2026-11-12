INSERT INTO users (id, username, email, password, role)
VALUES ('1','admin', 'admin@example.com',
        '$2a$10$g3/VANujP9rjWSKEaNIbc.GfLhK3ht5tcu2yvTZ01jVoO/2SCWuse',
        'ROLE_ADMIN');
INSERT INTO shop (id, name, api_key, city)
VALUES ('1','Kruidvat', 'kruidvat1234',
        'Utrecht');