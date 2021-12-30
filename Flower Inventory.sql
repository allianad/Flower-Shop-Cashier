DROP DATABASE IF EXISTS FLOWER_INVENTORY;
CREATE DATABASE FLOWER_INVENTORY;
USE FLOWER_INVENTORY;

DROP TABLE IF EXISTS FLOWER;
CREATE TABLE FLOWER (
		FlowerID		char(4) not null,
		Name			varchar(25),
        Colour			varchar(15),
        Price			double,
        Quantity		integer,
        Primary key		(FlowerID)
);

INSERT INTO FLOWER (FlowerID, Name, Colour, Price, Quantity)
VALUES
	('F001',	'Rose',				'Red',			2.00,	60),
	('F002',	'Rose',				'Pink',			2.00,	30),
	('F003',	'Rose',				'Yellow',		2.00,	30),
    ('F004',	'Rose',				'White',		2.00,	30),
    ('F005',	'Tulip',			'Orange',		1.50,	50),
    ('F006',	'Carnation',		'Pink',			1.75,	30),
    ('F007',	'Carnation',		'Red',			1.75,	30),
    ('F008',	'Carnation',		'Purple',		1.75,	30),
    ('F009',	'Chrysanthemum',	'Red',			1.75,	40),
    ('F010',	'Chrysanthemum',	'Pink',			1.75,	40),
    ('F011',	'Daisy',			'White',		1.50,	50),
    ('F012',	'Gerbera',			'Pink',			1.75,	25),
    ('F013',	'Gerbera',			'Yellow',		1.75,	25),
    ('F014',	'Gerbera',			'Red',			1.75,	25),
    ('F015',	'Hydrangea',		'Blue',			1.75,	20),
    ('F016',	'Lily',				'White',		2.50,	20),
    ('F017',	'Gypsophila',		'White',		1.50,	30),
    ('F018',	'Gypsophila',		'Pink',			1.50,	40),
    ('F019',	'Orchid',			'White',		2.50,	20),
    ('F020',	'Sunflower',		'Yellow',		2.00,	30);
    
DROP TABLE IF EXISTS PACKAGING;
CREATE TABLE PACKAGING(
		PackID		char(4) not null,
        Type		varchar(25),
        Colour		varchar(25),
        Size		varchar(25),
        Price		double,
        Quantity	integer,
        primary key (PackID)
);

INSERT INTO PACKAGING (PackID, Type, Colour, Size, Price, Quantity)
VALUES
		('P001', 'Basket',				'Brown',	'Small',	12.99,	20),
        ('P002', 'Basket',				'Brown',	'Medium',	18.99,	30),
        ('P003', 'Basket',				'Brown',	'Large',	32.99,	25),
        ('P004', 'Rectangle Vase',		'Clear',	'Small',	8.99,	15),
        ('P005', 'Rectangle Vase',		'Clear',	'Medium',	19.99,	30),
        ('P006', 'Rectangle Vase',		'Clear',	'Large',	29.99,	10),
        ('P007', 'Circular Vase',		'Clear',	'Small',	8.99,	10),
        ('P008', 'Cylinder Vase',		'Clear',	'Medium',	19.99,	30),
        ('P009', 'Circular Vase',		'Clear',	'Large',	29.99,	15);

        