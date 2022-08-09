create database kurs_work
create table details( 								
kod_det int not null primary key identity(201, 1),
type_det varchar(1) not null check(type_det = 'p' or type_det = 'o'),
name_det varchar(20),
ed_izm varchar(5) default('ps'),
plan_cost decimal(14, 2)
)

create table buyers(
kod_buyers int not null primary key identity(101,1),
name_buyers varchar(20) not null,
address_buyers varchar(50) default('')
)

create table storage( 		
id_storage int not null,
id_document int not null,
kod_done_product int not null,  					
kod_buyers int not null,							
ed_izm varchar(5) not null default('ps'), 
kolvo int default(0),
date_ship varchar(20) not null,
constraint pk_storage primary key(id_storage, id_document),
constraint fk1 foreign key(kod_done_product) references 
details(kod_det) on delete cascade on update cascade,
constraint fk2 foreign key(kod_buyers) 
references buyers(kod_buyers)
)

CREATE or alter TRIGGER trigger_delete_1 ON buyers
INSTEAD OF DELETE 
AS
BEGIN
	DELETE FROM storage
	WHERE id_document IN (SELECT kod_buyers FROM DELETED)  
	DELETE FROM buyers
	WHERE kod_buyers IN (SELECT kod_buyers FROM DELETED)
END

DELETE FROM buyers
WHERE kod_buyers = 103 AND name_buyers = 'Regina';

SELECT * FROM buyers;
SELECT * FROM storage;

create or alter function Get_name_det(@kod_det int)
returns table
	return select p.kod_det, p.name_det, p.plan_cost
	from details p
	where p.kod_det = @kod_det

select * from Get_name_det(202)

create or alter procedure Procedure_1 
@type_det varchar(3) output, @plan_costs decimal(14,2) output
as
begin
	select @plan_costs = min(plan_cost) from details
	where  type_det = @type_det 
end

declare @myvar decimal(14,2)

execute Procedure_1 'p', @myvar output
select @myvar

delete from storage;
delete from buyers;
delete from details;


set identity_insert details on
insert into details(kod_det, type_det, name_det, ed_izm, plan_cost)
	values 	(201, 'p', 'provod', 'm', 0.5),
			(202, 'o', 'bolt', 'g', 3.2),
			(203, 'p', 'muka', 'kg', 10.5),
			(204, 'p', 'apple', 'ps', 10),
			(205, 'o', 'kvas', 'l', 3),
			(206, 'p', 'bread', 'ps', 10),
			(207, 'o', 'gaika', 'l', 3)
set identity_insert details off

set identity_insert buyers on
insert into buyers(kod_buyers, name_buyers, address_buyers)
	values 	(101, 'Darya', 'Kazan, Pushkina 32'),
			(102, 'Nikita', 'Kazan, Riharda Zorge 23'),
			(103, 'Regina', 'Kazan, Karla Marksa 11'),
			(104, 'Marina', 'Kazan, Prospekt Pobedy 128'),
			(105, 'Roman', 'Kazan, Kremlevskaya 35'),
			(107, 'Vladimir', 'Kazan, Pr Pob 35'),
			(108, 'Regina', 'Moscow, Karla Marksa 11'),
			(109, 'Marina', 'Moscow, Prospekt Pobedy 128'),
			(106, 'Roman', 'Vyatskie Polyany, Kremlevskaya 35'),
			(110, 'Vladimir', 'Kirov, Pr Pob 35')
set identity_insert buyers off

insert into storage(id_storage, id_document, kod_done_product, kod_buyers, ed_izm, kolvo, date_ship)
	values 	(201, 101, 201, 101, 'kg', 5, '21_05_2001'),
			(202, 102, 202, 102, 'l', 1, '07_09_1999'),
			(203, 103, 203, 103, 'm', 3, '04_08_2001'),
			(204, 104, 204, 104, 'g',17, '28_09_1999'),
			(205, 105, 205, 105, 'ps', 9, '21_08_1999'),
			(201, 103, 201, 101, 'kg', 7, '21_05_2001'),
			(201, 171, 201, 107, 'kg', 5, '21_05_2001'),
			(202, 105, 205, 108, 'ps', 9, '21_08_1999'),
			(207, 103, 207, 107, 'kg', 7, '21_05_2001'),
			(201, 131, 201, 107, 'kg', 5, '21_05_2001'),
			(201, 143, 207, 109, 'kg', 7, '21_05_2001'),
			(207, 114, 207, 109, 'kg', 5, '21_05_2001'),
			(205, 194, 201, 102, 'kg', 5, '21_05_2001'),
			(205, 123, 206, 101, 'kg', 5, '21_05_2001'),
			(205, 174, 206, 104, 'kg', 5, '21_05_2001'),
			(208, 1554, 206, 101, 'kg', 5, '21_05_2001')

			

select * from buyers
select * from details
select * from storage
