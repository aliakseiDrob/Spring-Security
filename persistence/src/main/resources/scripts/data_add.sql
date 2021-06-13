INSERT INTO gift_certificate (name, description, price, duration, is_available,create_date, last_update_date)
values ('first', 'for men', 100, 4,1, '2021-05-22 20:11:10', '2021-05-24 20:11:10'),
       ('second', 'for men', 100, 7,1, '2021-05-22 20:11:10', '2021-05-24 20:11:10'),
       ('third', 'for women', 230, 17,1, '2021-05-22 20:11:10', '2021-05-28 20:11:10'),
       ('first', 'for women', 340, 8,1, '2021-05-21 20:11:10', '2021-05-24 20:11:10'),
       ('fifth', 'for children', 25, 11,0, '2021-05-23 20:11:10', '2021-05-24 16:11:10'),
       ('sixth', 'for children', 50, 14,1, '2021-05-18 20:11:10', '2021-05-19 22:11:10');

INSERT INTO tag (name)
VALUES ('health'),
       ('games'),
       ('sport'),
       ('hobby'),
       ('reading'),
       ('quiz');

INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 4),
       (2, 6),
       (2, 1),
       (3, 3),
       (3, 4),
       (4, 1),
       (4, 5),
       (5, 2),
       (5, 5),
       (6, 6),
       (6, 3);

INSERT INTO account (user_id)
VALUES ('5ea0893e-ede1-4f5f-9dd0-af03f2591e4c'),
       ('bd44096e-6f44-44af-9a54-2a9ddac0b9b5');


INSERT INTO `orders` (date,order_cost,account_id,certificate_id)
VALUES ('2021-05-24 20:11:10',340,2,1),
       ('2021-05-30 20:11:10',100,2,1),
       ('2021-05-24 20:11:10',100,2,2),
       ('2021-05-29 20:11:10',230,2,3);