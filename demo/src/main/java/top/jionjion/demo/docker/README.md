配合Docker容器启动


## MySQL
docker pull mysql

启动MySQL,指定名称,端口映射,并指定密码,
docker run --name mysql01 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql

查看实例的日志
docker logs XX_id


