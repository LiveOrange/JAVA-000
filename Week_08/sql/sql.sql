CREATE TABLE t_order0 (
id BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT '主键',
consumer_id VARCHAR ( 128 ) NOT NULL COMMENT '用户ID',
order_id VARCHAR ( 128 ) NOT NULL COMMENT '订单ID',
status VARCHAR ( 2 ) NOT NULL COMMENT '订单状态',
pay_status VARCHAR ( 2 )  COMMENT '支付状态',
pay_amount decimal(20) COMMENT '支付金额',
pay_time datetime  COMMENT '支付时间',
create_time datetime NOT NULL COMMENT '创建时间',
update_time datetime   NOT NULL COMMENT '修改时间',
PRIMARY KEY ( id ) USING BTREE,
UNIQUE INDEX idx_order_id(order_id) USING BTREE,
INDEX idx_consumer_id(consumer_id) USING BTREE
) ENGINE = INNODB COMMENT '订单表' CHARSET=utf8mb4;


CREATE TABLE t_order1 (
id BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT '主键',
consumer_id VARCHAR ( 128 ) NOT NULL COMMENT '用户ID',
order_id VARCHAR ( 128 ) NOT NULL COMMENT '订单ID',
status VARCHAR ( 2 ) NOT NULL COMMENT '订单状态',
pay_status VARCHAR ( 2 )  COMMENT '支付状态',
pay_amount decimal(20) COMMENT '支付金额',
pay_time datetime  COMMENT '支付时间',
create_time datetime NOT NULL COMMENT '创建时间',
update_time datetime   NOT NULL COMMENT '修改时间',
PRIMARY KEY ( id ) USING BTREE,
UNIQUE INDEX idx_order_id(order_id) USING BTREE,
INDEX idx_consumer_id(consumer_id) USING BTREE
) ENGINE = INNODB COMMENT '订单表' CHARSET=utf8mb4;


CREATE TABLE t_order2 (
id BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT '主键',
consumer_id VARCHAR ( 128 ) NOT NULL COMMENT '用户ID',
order_id VARCHAR ( 128 ) NOT NULL COMMENT '订单ID',
status VARCHAR ( 2 ) NOT NULL COMMENT '订单状态',
pay_status VARCHAR ( 2 )  COMMENT '支付状态',
pay_amount decimal(20) COMMENT '支付金额',
pay_time datetime  COMMENT '支付时间',
create_time datetime NOT NULL COMMENT '创建时间',
update_time datetime   NOT NULL COMMENT '修改时间',
PRIMARY KEY ( id ) USING BTREE,
UNIQUE INDEX idx_order_id(order_id) USING BTREE,
INDEX idx_consumer_id(consumer_id) USING BTREE
) ENGINE = INNODB COMMENT '订单表' CHARSET=utf8mb4;


CREATE TABLE t_user0 (
id BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT '主键',
consumer_id VARCHAR ( 128 ) NOT NULL COMMENT '用户ID',
user_name VARCHAR ( 64 ) COMMENT '姓名',
email VARCHAR ( 128 ) COMMENT '邮箱',
card_no VARCHAR ( 64 ) COMMENT '证件号',
mobile VARCHAR ( 32 ) COMMENT '手机号',
state VARCHAR ( 2 ) COMMENT '状态(T-启用;U-注销)',
gender TINYINT ( 1 ) COMMENT '性别(0-男;1-女)',
create_time datetime NOT NULL COMMENT '创建时间',
update_time datetime NOT NULL COMMENT '修改时间',
PRIMARY KEY ( id ) USING BTREE ,
UNIQUE INDEX idx_consumer_id(consumer_id) USING BTREE
) ENGINE = INNODB COMMENT '用户表' CHARSET=utf8mb4;

CREATE TABLE t_user1 (
id BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT '主键',
consumer_id VARCHAR ( 128 ) NOT NULL COMMENT '用户ID',
user_name VARCHAR ( 64 ) COMMENT '姓名',
email VARCHAR ( 128 ) COMMENT '邮箱',
card_no VARCHAR ( 64 ) COMMENT '证件号',
mobile VARCHAR ( 32 ) COMMENT '手机号',
state VARCHAR ( 2 ) COMMENT '状态(T-启用;U-注销)',
gender TINYINT ( 1 ) COMMENT '性别(0-男;1-女)',
create_time datetime NOT NULL COMMENT '创建时间',
update_time datetime NOT NULL COMMENT '修改时间',
PRIMARY KEY ( id ) USING BTREE ,
UNIQUE INDEX idx_consumer_id(consumer_id) USING BTREE
) ENGINE = INNODB COMMENT '用户表' CHARSET=utf8mb4;

CREATE TABLE t_user2 (
id BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT '主键',
consumer_id VARCHAR ( 128 ) NOT NULL COMMENT '用户ID',
user_name VARCHAR ( 64 ) COMMENT '姓名',
email VARCHAR ( 128 ) COMMENT '邮箱',
card_no VARCHAR ( 64 ) COMMENT '证件号',
mobile VARCHAR ( 32 ) COMMENT '手机号',
state VARCHAR ( 2 ) COMMENT '状态(T-启用;U-注销)',
gender TINYINT ( 1 ) COMMENT '性别(0-男;1-女)',
create_time datetime NOT NULL COMMENT '创建时间',
update_time datetime NOT NULL COMMENT '修改时间',
PRIMARY KEY ( id ) USING BTREE ,
UNIQUE INDEX idx_consumer_id(consumer_id) USING BTREE
) ENGINE = INNODB COMMENT '用户表' CHARSET=utf8mb4;