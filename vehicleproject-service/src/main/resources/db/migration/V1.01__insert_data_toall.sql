insert into brand (name, nationality, description) values
('Cadillac', 'USA', 'American Luxury Brand'),
('Lexus', 'Japan', 'Luxury Brand in Toyota'),
('Mercedes-Benz', 'Germany', 'Famous Luxury Brand Among The World')
;
commit;

insert into model(name,type,description,brand_id) values
('CT6','Sedan','Highest Hierachy Sedan in Cadillac',1),
('LS300','Sedan','Basic Sedan',2),
('AMG GLE 63','SUV','Powerful SUV',3),
('Escalade','SUV','Powerful SUV',1)
;
commit;

insert into config(name,key_feature,"model_id") values
('3.0 Twin Turbo Engine','Highest level in this brand',1),
('3.6LEngine','Medium level in this brand',1),
('3.0L Engine','Primary level in this brand',1)
;
commit;

insert into orders(order_number,price,requirement,config_id) values
('10000',50000.00,'black exterior',1),
('10000',49800.00,'bronze exterior',1),
('10000',43000.00,'wrap windows',2),
('10000',41000.00,'manual transmission',3)
;
commit;

insert into customer(name,cell_number,email,information,order_id) values
('马云','88888888','yun@taobao.com','richman',1),
('马化腾','66666666','teng@qq.com','richman',2),
('李彦宏','99999999','hong@baidu.com','richman',3),
('扎克伯格','22222222','boge@facebook.com','richman',4)
;
commit;