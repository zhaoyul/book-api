-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass)
VALUES (:id, :first_name, :last_name, :email, :pass)

-- :name update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieves a user record given the id
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc deletes a user record given the id
DELETE FROM users
WHERE id = :id


-- :name get-books :? :*
-- :doc 获得所有图书列表
select rowid as id, name as title, sequence, `buy-time`, desc from books


-- :name get-historys :? :*
-- :doc 获得全部图书的借阅历史
select rowid,
       `book-id` as `book-id`,
       `borrower-name` as `借书人`,
       `start-time` as `借书日期`,
       `end-time`  as `还书日期`,
       `comments`  as `评价`,
       `returned`  as `已经归还`
from history


-- :name get-last-history :? :1
-- :doc 获得最后借出去的那条记录
select rowid,
       `book-id` as `book-id`,
       `borrower-name` as `借书人`,
       `start-time` as `借书日期`,
       `end-time`  as `还书日期`,
       `comments`  as `评价`,
       `returned`  as `已经归还`
from history
where `book-id` = :book-id
  and `returned`= false


-- :name insert-history :! :n
-- :doc 增加一条新的借阅记录
insert into history
(`book-id`, `borrower-name`, `start-time`, `end-time`, `comments`, `returned`)
values
(:book-id, :borrower-name, :start-time, :end-time, :comments, false)


-- :name udpate-history :! :n
-- :doc 还书呀！！！！！！！！！！！
update history
set `returned` = true
where rowid = :rowid
