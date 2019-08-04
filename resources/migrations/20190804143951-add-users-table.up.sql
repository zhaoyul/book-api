CREATE TABLE books
(name VARCHAR(50),
`sequence` INT,
`buy-time` TIMESTAMP,
desc VARCHAR(5000)
);

--;;

insert into books (name, `sequence`, `buy-time`, desc) values
('黑客与画家',1, '2018-08-01', '一本好书'),
('黑客与画家',2, '2018-08-01', '一本好书');

--;;
create table users
(name varchar(50),
 password varchar(50)
);

--;;

insert into users(name, password) values
('kevin', 'kevin');

--;;

create table history
(`book-id` int,
 `borrower-name` varchar(50),
 `start-time` timestamp,
 `end-time` timestamp,
 `returned` boolean,
 comments text);


--;;

insert into history (`book-id`,`borrower-name`, `start-time`, `end-time`,`returned`, comments) values
(1, 'Kevin', '2018-08-01', '2018-08-04', true, "很好的Lisp宣传册"),
(1, 'Andy', '2018-08-01', '2018-08-04', true, "很好的Lisp宣传册");
