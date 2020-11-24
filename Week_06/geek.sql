
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
id BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT '主键',
consumer_id VARCHAR ( 128 ) NOT NULL COMMENT '用户ID',
user_name VARCHAR ( 64 ) COMMENT '姓名',
pass_word VARCHAR ( 255 ) COMMENT '密码',
email VARCHAR ( 128 ) COMMENT '邮箱',
card_no VARCHAR ( 64 ) COMMENT '证件号',
mobile VARCHAR ( 32 ) COMMENT '手机号',
state　 VARCHAR ( 2 ) COMMENT '状态(T-启用;U-注销)',
gender TINYINT ( 1 ) COMMENT '性别(0-男;1-女)',
create_time datetime NOT NULL COMMENT '创建时间',
update_time datetime NOT NULL COMMENT '修改时间',
PRIMARY KEY ( id ) USING BTREE ,
UNIQUE INDEX idx_consumer_id(consumer_id) USING BTREE
) ENGINE = INNODB COMMENT '用户表';


DROP TABLE IF EXISTS t_goods;
CREATE TABLE t_goods (
id BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT '主键',
goods_id VARCHAR ( 128 ) NOT NULL COMMENT '商品ID',
goods_name VARCHAR ( 255 ) COMMENT '商品名称',
goods_type VARCHAR ( 64 ) COMMENT '商品类型',
goods_desc VARCHAR ( 512 ) COMMENT '商品描述',
goods_price decimal(20) COMMENT '商品价格',
goods_number BIGINT(20) COMMENT '商品数量',
state　 VARCHAR ( 2 ) COMMENT '状态(T-有效;U-无效)',
create_time datetime NOT NULL COMMENT '创建时间',
update_time datetime NOT NULL COMMENT '修改时间',
PRIMARY KEY ( id ) USING BTREE,
UNIQUE INDEX idx_goods_id(goods_id) USING BTREE
) ENGINE = INNODB COMMENT '商品表';


DROP TABLE IF EXISTS t_order;
CREATE TABLE t_order (
id BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT '主键',
consumer_id VARCHAR ( 128 ) NOT NULL COMMENT '用户ID',
order_id VARCHAR ( 128 ) NOT NULL COMMENT '订单ID',
goods_id VARCHAR ( 128 ) NOT NULL COMMENT '商品ID',
status VARCHAR ( 2 ) NOT NULL COMMENT '订单状态',
pay_status VARCHAR ( 2 )  COMMENT '支付状态',
pay_amount decimal(20) COMMENT '支付金额',
pay_type VARCHAR ( 2 ) COMMENT '支付方式',
pay_time datetime  COMMENT '支付时间',
address VARCHAR ( 255 )  COMMENT '收货地址',
total_amount decimal ( 20 ) COMMENT '订单金额',
transaction_no VARCHAR ( 128  ) COMMENT '交易单号',
create_time datetime NOT NULL COMMENT '创建时间',
update_time datetime   NOT NULL COMMENT '修改时间',
PRIMARY KEY ( id ) USING BTREE,
UNIQUE INDEX idx_order_id(order_id) USING BTREE,
INDEX idx_consumer_id(consumer_id) USING BTREE
) ENGINE = INNODB COMMENT '订单表';

