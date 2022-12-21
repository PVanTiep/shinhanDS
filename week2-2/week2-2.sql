-- Table CAMPUS
CREATE TABLE CAMPUS(
CampusID number(20) not null primary key,
CampusName varchar2(100) not null,
Street varchar2(100) not null,
City varchar2(100) not null,
State varchar2(100) not null,
Zip varchar2(100),
Phone varchar2(100),
CampusDiscount decimal (2,2)
)
-- Table POSITION
CREATE TABLE POSITION (
PositionID number(20) NOT NULL PRIMARY KEY,
POSITION varchar2(100),
YearlyMembershipFee Decimal (7,2)
)

-- Table MEMBERS
CREATE TABLE MEMBERS(
MemberID number(20) not null primary key,
LastName varchar2(100) not null,
FirstName varchar2(100) not null,
CampusAddress varchar2(100) not null,
CampusPhone varchar2(100) not null,
CampusID number(20) not null,
PositionID number(20) not null,
ContactDuration integer not null,
CONSTRAINT MEMBERS_POSITION   FOREIGN KEY (PositionID) REFERENCES POSITION(PositionID),
CONSTRAINT MEMBERS_CAMPUS   FOREIGN KEY (CampusID) REFERENCES CAMPUS(CampusID)
)
--ALTER TABLE MEMBERS
--ADD (CONSTRAINT fk_memberposition
--  FOREIGN KEY (PositionID)
--  REFERENCES POSITION(PositionID),
-- CONSTRAINT fk_campusid
--  FOREIGN KEY (CampusID)
--  REFERENCES CAMPUS(CampusID))
--  
-- Table PRICES
CREATE TABLE PRICES(
FoodItemTypeID number(20) not null primary key,
MealType varchar2(100) not null,
MealPrice DECIMAL (7,2) NOT NULL
)
-- Table FoodItems
CREATE TABLE FoodItems(
FoodItemsID number(20) not null primary key ,
FoodItemName varchar2(100) not null,
FoodItemTypeID number(20) not null
--CONSTRAINT FoodItems_PRICES FOREIGN KEY (FoodItemTypeID) REFERENCES PRICES(FoodItemTypeID)
)
ALTER TABLE FoodItems
ADD (CONSTRAINT fk_fooditemtype
  FOREIGN KEY (FoodItemTypeID)
  REFERENCES PRICES(FoodItemTypeID))
drop table fooditems
-- Table Orders
CREATE TABLE Orders
( OrderID number(20) not null primary key,
MemberID number(20) not null ,
OrderDate varchar2(25) not null,
CONSTRAINT Orders_MEMBERS FOREIGN KEY (MemberID) REFERENCES MEMBERS(MemberID)
)
--ALTER TABLE Orders
--ADD (CONSTRAINT fk_memberid
--  FOREIGN KEY (MemberID)
--  REFERENCES MEMBERS(MemberID))

-- Table OrderLine
CREATE TABLE OrderLine(
OrderID number(20) not null,
FoodItemsID number(20) not null,
Quantity integer not null,
CONSTRAINT tb_PK PRIMARY KEY (OrderID, FoodItemsID),
CONSTRAINT OrderLine_Order FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
CONSTRAINT OrderLine_FoodItemsID FOREIGN KEY (FoodItemsID) REFERENCES FoodItems(FoodItemsID)
)
--ALTER TABLE OrderLine
--ADD (CONSTRAINT fk_orderid
--  FOREIGN KEY (OrderID)
--  REFERENCES Orders(OrderID),
-- CONSTRAINT fk_fooditemid
--  FOREIGN KEY (FoodItemsID)
--  REFERENCES FoodItems(FoodItemsID))
  
  
create sequence Prices_FoodItemTypeID_SEQ;
--bai 1
insert into campus values(Prices_FoodItemTypeID_SEQ.nextval,'IUPUI','425 University Blvd.','Indianapolis', 'IN','46202', '317-274-4591',.08);
insert into campus values(Prices_FoodItemTypeID_SEQ.nextval,'Indiana University','107 S. Indiana Ave.','Bloomington', 'IN','47405', '812-855-
4848',.07);
insert into campus values(Prices_FoodItemTypeID_SEQ.nextval,'Purdue University','475 Stadium Mall Drive','West Lafayette', 'IN','47907', '765-
494-1776',.06);

--drop sequence fooditems_seq;
--delete from  FOODITEMS;

create sequence position_seq;
insert into position values(position_seq.nextval,'Lecturer', 1050.50);
insert into position values(position_seq.nextval,'Associate Professor', 900.50);
insert into position values(position_seq.nextval,'Assistant Professor', 875.50);
insert into position values(position_seq.nextval,'Professor', 700.75);
insert into position values(position_seq.nextval,'Full Professor', 500.50);

create sequence member_seq;
insert into members values(member_seq.nextval,'Ellen','Monk','009 Purnell', '812-123-1234', '2', '5', 12);
insert into members values(member_seq.nextval,'Joe','Brady','008 Statford Hall', '765-234-2345', '3', '2', 10);
insert into members values(member_seq.nextval,'Dave','Davidson','007 Purnell', '812-345-3456', '2', '3', 10);
insert into members values(member_seq.nextval,'Sebastian','Cole','210 Rutherford Hall', '765-234-2345', '3', '5', 10);
insert into members values(member_seq.nextval,'Michael','Doo','66C Peobody', '812-548-8956', '2', '1', 10);
insert into members values(member_seq.nextval,'Jerome','Clark','SL 220', '317-274-9766', '1', '1', 12);
insert into members values(member_seq.nextval,'Bob','House','ET 329', '317-278-9098', '1', '4', 10);
insert into members values(member_seq.nextval,'Bridget','Stanley','SI 234', '317-274-5678', '1', '1', 12);
insert into members values(member_seq.nextval,'Bradley','Wilson','334 Statford Hall', '765-258-2567', '3', '2', 10);

create sequence price_seq;
insert into prices values(price_seq.nextval,'Beer/Wine', 5.50);
insert into prices values(price_seq.nextval,'Dessert', 2.75);
insert into prices values(price_seq.nextval,'Dinner', 15.50);
insert into prices values(price_seq.nextval,'Soft Drink', 2.50);
insert into prices values(price_seq.nextval,'Lunch', 7.25);

create sequence fooditems_seq start with 10001;
insert into FOODITEMS values(fooditems_seq.nextval,'Lager', '1');
insert into FOODITEMS values(fooditems_seq.nextval,'Red Wine', '1');
insert into FOODITEMS values(fooditems_seq.nextval,'White Wine', '1');
insert into FOODITEMS values(fooditems_seq.nextval,'Coke', '4');
insert into FOODITEMS values(fooditems_seq.nextval,'Coffee', '4');
insert into FOODITEMS values(fooditems_seq.nextval,'Chicken a la King', '3');
insert into FOODITEMS values(fooditems_seq.nextval,'Rib Steak', '3');
insert into FOODITEMS values(fooditems_seq.nextval,'Fish and Chips', '3');
insert into FOODITEMS values(fooditems_seq.nextval,'Veggie Delight', '3');
insert into FOODITEMS values(fooditems_seq.nextval,'Chocolate Mousse', '2');
insert into FOODITEMS values(fooditems_seq.nextval,'Carrot Cake', '2');
insert into FOODITEMS values(fooditems_seq.nextval,'Fruit Cup', '2');
insert into FOODITEMS values(fooditems_seq.nextval,'Fish and Chips', '5');
insert into FOODITEMS values(fooditems_seq.nextval,'Angus Beef Burger', '5');
insert into FOODITEMS values(fooditems_seq.nextval,'Cobb Salad', '5');

create sequence orders_seq;
insert into ORDERS values(orders_seq.nextval,'9', 'March 5, 2005');
insert into ORDERS values(orders_seq.nextval,'8', 'March 5, 2005');
insert into ORDERS values(orders_seq.nextval,'7', 'March 5, 2005');
insert into ORDERS values(orders_seq.nextval,'6', 'March 7, 2005');
insert into ORDERS values(orders_seq.nextval,'5', 'March 7, 2005');
insert into ORDERS values(orders_seq.nextval,'4', 'March 10, 2005');
insert into ORDERS values(orders_seq.nextval,'3', 'March 11, 2005');
insert into ORDERS values(orders_seq.nextval,'2', 'March 12, 2005');
insert into ORDERS values(orders_seq.nextval,'1', 'March 13, 2005');
delete from ORDERLINE;
insert into ORDERLINE values('1','10001',1);
insert into ORDERLINE values('1','10006',1);
insert into ORDERLINE values('1','10012',1);
insert into ORDERLINE values('2','10004',2);
insert into ORDERLINE values('2','10013',1);
insert into ORDERLINE values('2','10014',1);
insert into ORDERLINE values('3','10005',1);
insert into ORDERLINE values('3','10011',1);
insert into ORDERLINE values('4','10005',2);
insert into ORDERLINE values('4','10004',2);
insert into ORDERLINE values('4','10006',1);
insert into ORDERLINE values('4','10007',1);
insert into ORDERLINE values('4','10010',2);
insert into ORDERLINE values('5','10003',1);
insert into ORDERLINE values('6','10002',2);
insert into ORDERLINE values('7','10005',2);
insert into ORDERLINE values('8','10005',1);
insert into ORDERLINE values('8','10011',1);
insert into ORDERLINE values('9','10001',1);


-- cau 1
SELECT *
  FROM user_cons_columns
WHERE owner = 'HR' AND table_name IN('CAMPUS','ORDERLINE','ORDERS','FOODITEMS','PRICES','MEMBERS','POSITION') AND POSITION IS NOT NULL;
-- cau 2
select  table_name from user_tables;
-- cau 3
select * from user_sequences;
-- cau 4
select firstname, lastname, position, campusname,(yearlymembershipfee/12) as Monthly_Dues 
from members mb,campus c, position p
where
mb.campusid= c.campusid and mb.positionid=p.positionid
order by campusname DESC, lastname asc;