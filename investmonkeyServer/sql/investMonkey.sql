drop table balance;
drop table trade;
drop table invest_order;

drop table holding;
drop table client_identification;
drop table client_pref;
drop table client;

 

 

CREATE TABLE client(
	ClientId VARCHAR(255) PRIMARY KEY,
	FirstName VARCHAR(50) NOT NULL,
	LastName VARCHAR(50) NOT NULL,
	Phone VARCHAR(15) NOT NULL,
	Email VARCHAR(50) UNIQUE NOT NULL,
	DateOfBirth DATE NOT NULL,
	Country VARCHAR(30) NOT NULL,
	PostalCode VARCHAR(30) NOT NULL,
	Password VARCHAR(30) NOT NULL
);

 

CREATE TABLE client_identification(
	ClientId Varchar(255),
	IdentificationType VARCHAR(20),
	IdentificationValue VARCHAR(20),
	FOREIGN KEY (ClientId) REFERENCES client(ClientId)
);
INSERT INTO client VALUES('123abc','Jitin','Dodeja','123456789','jd@gmail.com',TO_DATE('1989-12-09','YYYY-MM-DD'),'India','570011','jd123');INSERT INTO client_identification VALUES('123abc','Aadhar','1234567');
INSERT INTO client VALUES('456abc','Vishakha','V','6361724765','vishakhav965@gmail.com',TO_DATE('2000-10-09','YYYY-MM-DD'),'India','573201','vis123');INSERT INTO client_identification VALUES('456abc','Aadhar','872357934426');

 

CREATE TABLE balance (
    clientId VARCHAR(50) UNIQUE,
    balance NUMBER(10,0),
    FOREIGN KEY (clientId) REFERENCES client(clientId)
);

 

INSERT INTO balance (clientId, balance) VALUES ('456abc', 1000000);

 CREATE TABLE CLIENT_PREF (
    ClientId VARCHAR(255) PRIMARY KEY,
	INVESTMENT_PURPOSE VARCHAR(255),
	INCOME_CATEGORY VARCHAR(255),
	RISK_TOLERANCE VARCHAR(255),
	LENGTH_OF_INVESTMENT NUMBER,
    FOREIGN KEY (ClientId) REFERENCES client(ClientId)
   );

   INSERT INTO CLIENT_PREF (ClientId, INVESTMENT_PURPOSE, INCOME_CATEGORY, RISK_TOLERANCE, LENGTH_OF_INVESTMENT)
VALUES ('456abc', 'Retirement', 'CATEGORY_1_10000', 'LOW', 10);

CREATE TABLE invest_order (
    OrderId VARCHAR(255) PRIMARY KEY,
    InstrumentId VARCHAR(255),
    Quantity NUMBER(6,0),
    TargetPrice NUMBER(10,0),
    Direction VARCHAR(50),
    ClientId VARCHAR(255),
    FOREIGN KEY (ClientId) REFERENCES client(ClientId)
);

 

 

CREATE TABLE trade (
    TradeId VARCHAR(255) DEFAULT holdingId_seq.nextval NOT NULL,
    Quantity NUMBER(6,0),
    ExecutionPrice NUMBER(10,0),
    Direction VARCHAR(50),
    OrderId VARCHAR(255) UNIQUE,
    CashValue NUMBER(10,0),
    ClientId VARCHAR(255),
    InstrumentId VARCHAR(255),
    FOREIGN KEY (OrderId) REFERENCES invest_order(OrderId),
    FOREIGN KEY (ClientId) REFERENCES client(ClientId)
);

 

INSERT INTO invest_order (OrderId, InstrumentId, Quantity, TargetPrice, Direction, ClientId)
VALUES ('order1', 'instrument1', 100, 150, 'Buy', '456abc');

 

 

INSERT INTO invest_order (OrderId, InstrumentId, Quantity, TargetPrice, Direction, ClientId)
VALUES ('order2', 'instrument2', 200, 200, 'Sell', '456abc');

 

 

INSERT INTO invest_order (OrderId, InstrumentId, Quantity, TargetPrice, Direction, ClientId)
VALUES ('order3', 'instrument3', 150, 180, 'Buy', '456abc');

 

 

 

 

 

INSERT INTO trade (TradeId, Quantity, ExecutionPrice, Direction, OrderId, CashValue, ClientId, InstrumentId)
VALUES ('trade1', 100, 155, 'Buy', 'order1', 15500, '456abc', 'instrument1');

 

 

INSERT INTO trade (TradeId, Quantity, ExecutionPrice, Direction, OrderId, CashValue, ClientId, InstrumentId)
VALUES ('trade2', 200, 198, 'Sell', 'order2', 39600, '456abc', 'instrument2');

 

 

INSERT INTO trade (TradeId, Quantity, ExecutionPrice, Direction, OrderId, CashValue, ClientId, InstrumentId)
VALUES ('trade3', 150, 185, 'Buy', 'order3', 27750, '456abc', 'instrument3');

 

ALTER TABLE trade ADD( 
CONSTRAINT tradeId_pk PRIMARY KEY (TradeId)
);

 

CREATE SEQUENCE holdingId_seq START WITH 1;
CREATE TABLE holding (
    HoldingId NUMBER(6,0) DEFAULT holdingId_seq.nextval NOT NULL,
    InstrumentId VARCHAR(255),
    InstrumentDescription VARCHAR(255),
    CategoryId VARCHAR(255),
    AskNumber NUMBER(10,0),
    Quantity NUMBER(6,0),
    TotalPrice NUMBER(10,0),
    ClientId VARCHAR(255),
    FOREIGN KEY (ClientId) REFERENCES client(ClientId)
);

 

 

INSERT INTO holding (InstrumentId, InstrumentDescription, CategoryId, AskNumber, Quantity, TotalPrice, ClientId)
VALUES ('instrument1', 'Description 1', 'category1', 200, 100, 15000, '456abc');

 

INSERT INTO holding (InstrumentId, InstrumentDescription, CategoryId, AskNumber, Quantity, TotalPrice, ClientId)
VALUES ('instrument2', 'Description 2', 'category2', 300, 150, 22500, '456abc');

 

INSERT INTO holding (InstrumentId, InstrumentDescription, CategoryId, AskNumber, Quantity, TotalPrice, ClientId)
VALUES ('instrument3', 'Description 3', 'category3', 250, 120, 18000, '456abc');

 

 

ALTER TABLE holding ADD (
  CONSTRAINT holdingId_pk PRIMARY KEY (HoldingId));

 

 

commit;