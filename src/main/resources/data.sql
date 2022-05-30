create table admin_token
(
    id    bigint       not null
        primary key,
    token varchar(255) null
)
    engine = MyISAM;

create table cars
(
    id              bigint         not null
        primary key,
    car_manufacture varchar(255)   null,
    daily_cost      decimal(19, 2) null,
    fuel_type       int            null,
    mileage         int            null,
    model           varchar(255)   null,
    production_year int            null,
    status          int            null,
    vehicle_class   int            null,
    vin_number      varchar(255)   null
)
    engine = MyISAM;

INSERT INTO car_rental.cars (id, car_manufacture, daily_cost, fuel_type, mileage, model, production_year, status, vehicle_class, vin_number) VALUES (134, 'ASTON MARTIN', 212.00, 0, 122, 'DBX', 2021, 0, 11, 'SHHFK8G75MU203705');
INSERT INTO car_rental.cars (id, car_manufacture, daily_cost, fuel_type, mileage, model, production_year, status, vehicle_class, vin_number) VALUES (133, 'HONDA', 2.00, 0, 12, 'Civic', 2021, 1, 2, 'SHHFK8G75MU203705');
INSERT INTO car_rental.cars (id, car_manufacture, daily_cost, fuel_type, mileage, model, production_year, status, vehicle_class, vin_number) VALUES (136, 'CADILLAC', 2112.00, 0, 1222, 'Escalade', 2021, 0, 11, '1GYS4GKL8MR373134');
INSERT INTO car_rental.cars (id, car_manufacture, daily_cost, fuel_type, mileage, model, production_year, status, vehicle_class, vin_number) VALUES (137, 'LINCOLN', 2112.00, 0, 1222, 'Aviator', 2021, 0, 11, '5LM5J7XC0MGL04902');
INSERT INTO car_rental.cars (id, car_manufacture, daily_cost, fuel_type, mileage, model, production_year, status, vehicle_class, vin_number) VALUES (138, 'TOYOTA', 22.00, 0, 12222, 'Corolla', 2019, 0, 1, '2T1BURHE4KC242302');
INSERT INTO car_rental.cars (id, car_manufacture, daily_cost, fuel_type, mileage, model, production_year, status, vehicle_class, vin_number) VALUES (139, 'PORSCHE', 22.00, 0, 12222, '718', 2018, 1, 11, 'WP0CB2A80JS228531');
INSERT INTO car_rental.cars (id, car_manufacture, daily_cost, fuel_type, mileage, model, production_year, status, vehicle_class, vin_number) VALUES (140, 'LAND ROVER', 22.00, 0, 12222, 'Range Rover', 2008, 1, 11, 'SALMF13498A292458');
INSERT INTO car_rental.cars (id, car_manufacture, daily_cost, fuel_type, mileage, model, production_year, status, vehicle_class, vin_number) VALUES (141, 'VOLKSWAGEN', 221.00, 0, 1252, 'Atlas Cross Sport', 2020, 1, 11, '1V2MC2CA3LC205404');
INSERT INTO car_rental.cars (id, car_manufacture, daily_cost, fuel_type, mileage, model, production_year, status, vehicle_class, vin_number) VALUES (142, 'ALFA ROMEO', 251.00, 0, 12542, 'Stelvio', 2020, 1, 11, 'ZASPAKBN5L7C86141');
INSERT INTO car_rental.cars (id, car_manufacture, daily_cost, fuel_type, mileage, model, production_year, status, vehicle_class, vin_number) VALUES (143, 'FORD', 151.00, 0, 125432, 'Edge', 2019, 1, 11, '2FMPK4AP7KBB46694');

create table hibernate_sequence
(
    next_val bigint null
)
    engine = MyISAM;

INSERT INTO car_rental.hibernate_sequence (next_val) VALUES (149);

create table logins
(
    id       bigint       not null
        primary key,
    email    varchar(255) null,
    password varchar(255) null
)
    engine = MyISAM;

INSERT INTO car_rental.logins (id, email, password) VALUES (117, 'kontakt@ksykiewicz.pl', 'string');
INSERT INTO car_rental.logins (id, email, password) VALUES (103, 'kontakt@ksykiewicz.pl', 'string');
INSERT INTO car_rental.logins (id, email, password) VALUES (105, 'kksykiewicz@hotmail.com', 'string');
INSERT INTO car_rental.logins (id, email, password) VALUES (119, 'kontakt@ksykiewicz.pl', 'string');
INSERT INTO car_rental.logins (id, email, password) VALUES (121, 'kontakt@ksykiewicz.pl', 'string');
INSERT INTO car_rental.logins (id, email, password) VALUES (123, 'kontakt@ksykiewicz.pl', 'string');
INSERT INTO car_rental.logins (id, email, password) VALUES (125, 'kontakt@ksykiewicz.pl', 'string');
INSERT INTO car_rental.logins (id, email, password) VALUES (127, 'kontakt@ksykiewicz.pl', 'string');
INSERT INTO car_rental.logins (id, email, password) VALUES (129, 'kontakt@ksykiewicz.pl', 'string');
INSERT INTO car_rental.logins (id, email, password) VALUES (131, 'kontakt@ksykiewicz.pl', 'string');

create table rentals
(
    id          bigint         not null
        primary key,
    cost        decimal(19, 2) null,
    duration    bigint         null,
    rented_from date           null,
    rented_to   date           null,
    car_id      bigint         null,
    user_id     bigint         null
)
    engine = MyISAM;

create index FKb3vpbdnk78p1epicm7a7urvfh
    on rentals (car_id);

create index FKtnhd1objf2mlb6ag6k726u269
    on rentals (user_id);

INSERT INTO car_rental.rentals (id, cost, duration, rented_from, rented_to, car_id, user_id) VALUES (145, 29568.00, 14, '2022-01-11', '2022-01-25', 136, 130);
INSERT INTO car_rental.rentals (id, cost, duration, rented_from, rented_to, car_id, user_id) VALUES (144, 1908.00, 9, '2022-01-21', '2022-01-30', 134, 104);
INSERT INTO car_rental.rentals (id, cost, duration, rented_from, rented_to, car_id, user_id) VALUES (146, 528.00, 24, '2022-06-01', '2022-06-25', 138, 122);
INSERT INTO car_rental.rentals (id, cost, duration, rented_from, rented_to, car_id, user_id) VALUES (148, 29568.00, 14, '2022-06-01', '2022-06-15', 137, 106);

create table users
(
    id                    bigint       not null
        primary key,
    account_creation_date date         null,
    email_address         varchar(255) null,
    first_name            varchar(255) null,
    last_name             varchar(255) null,
    password              varchar(255) null,
    phone_number          int          null,
    constraint UK_9q63snka3mdh91as4io72espi
        unique (phone_number)
)
    engine = MyISAM;

INSERT INTO car_rental.users (id, account_creation_date, email_address, first_name, last_name, password, phone_number) VALUES (104, '2022-05-29', 'kontakt@ksykiewicz.pl', 'Mike', 'John', 'string', 12364);
INSERT INTO car_rental.users (id, account_creation_date, email_address, first_name, last_name, password, phone_number) VALUES (106, '2022-05-29', 'kksykiewicz@hotmail.com', 'John', 'Doe', 'string', 123641);
INSERT INTO car_rental.users (id, account_creation_date, email_address, first_name, last_name, password, phone_number) VALUES (118, '2022-05-30', 'kontakt@ksykiewicz.pl', 'Mike', 'Bike', 'string', 12342);
INSERT INTO car_rental.users (id, account_creation_date, email_address, first_name, last_name, password, phone_number) VALUES (120, '2022-05-30', 'kontakt@ksykiewicz.pl', 'Joe', 'Moe', 'string', 123242);
INSERT INTO car_rental.users (id, account_creation_date, email_address, first_name, last_name, password, phone_number) VALUES (122, '2022-05-30', 'kontakt@ksykiewicz.pl', 'Sasha', 'Sole', 'string', 113242);
INSERT INTO car_rental.users (id, account_creation_date, email_address, first_name, last_name, password, phone_number) VALUES (124, '2022-05-30', 'kontakt@ksykiewicz.pl', 'Mika', 'Wika', 'string', 1133242);
INSERT INTO car_rental.users (id, account_creation_date, email_address, first_name, last_name, password, phone_number) VALUES (126, '2022-05-30', 'kontakt@ksykiewicz.pl', 'Nick', 'Legend', 'string', 11332242);
INSERT INTO car_rental.users (id, account_creation_date, email_address, first_name, last_name, password, phone_number) VALUES (128, '2022-05-30', 'kontakt@ksykiewicz.pl', 'Brian', 'Slik', 'string', 112332242);
INSERT INTO car_rental.users (id, account_creation_date, email_address, first_name, last_name, password, phone_number) VALUES (130, '2022-05-30', 'kontakt@ksykiewicz.pl', 'Alexander', 'James', 'string', 11232);
INSERT INTO car_rental.users (id, account_creation_date, email_address, first_name, last_name, password, phone_number) VALUES (132, '2022-05-30', 'kontakt@ksykiewicz.pl', 'Justin', 'Black', 'string', 112372);
