### 개요

### 컴포즈 실행 
```
docker-compose up -d
```

### replication용 계정 생성

SOURCE 접속

```bash
docker exec -it mysql-source mysql -u root -p
```

user 계정에 REPLICATION SLAVE 권한을 추가. 

```mysql
GRANT REPLICATION SLAVE ON *.* TO 'user'@'%';
FLUSH PRIVILEGES;
```

### SOURCE DB 정보 확인

```mysql
SHOW MASTER STATUS;
```

확인한 File(SOURCE_LOG_FILE)과 Position(SOURCE_LOG_POS) 값은 replica 설정에서 사용한다.

### SOURCE ip 주소 확인

```bash
docker inspect -f "{{ .NetworkSettings.Networks.db_mysql_network.IPAddress }}" mysql-source
```

확인한 IP주소(SOURCE_HOST) 값은 replica 설정에서 사용한다.

### replica mysql 접속

```bash
docker exec -it mysql-replica mysql -u root -p
```

### replica 설정

SOURCE_HOST, SOURCE_LOG_FILE, SOURCE_LOG_POS 를 적절히 변경한다.

```mysql
STOP REPLICA;

CHANGE REPLICATION SOURCE TO 
SOURCE_HOST='172.29.0.2', 
SOURCE_USER='user', 
SOURCE_PASSWORD='password', 
SOURCE_LOG_FILE='mysql-bin.000001',
SOURCE_LOG_POS=0, 
GET_SOURCE_PUBLIC_KEY=1;

START REPLICA;
```

### 설정 확인

```mysql
SHOW REPLICA STATUS;
```

### 참고 자료
16장 복제, Real MySQL 8.0 - 백은빈, 이성욱  
[Spring 레플리케이션 트랜잭션 처리 방식](https://cheese10yun.github.io/spring-transaction/)  
[replication-datasource](https://github.com/kwon37xi/replication-datasource)  
[Simplified Guide to MySQL Replication with Docker Compose](https://www.linkedin.com/pulse/simplified-guide-mysql-replication-docker-compose-rakesh-shekhawat/)  
[Dockerfile에서 자주 쓰이는 명령어](https://www.daleseo.com/dockerfile/)  
[CHANGE REPLICATION SOURCE TO Statement](https://dev.mysql.com/doc/refman/8.1/en/change-replication-source-to.html)  
