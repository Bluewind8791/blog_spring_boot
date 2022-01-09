# Memo

## Service

- Transaction 관리

- 서비스의 의미:  
은행 서비스에서 A가 B에게 송금한다고 하면 양쪽의 repo를 update 성공해야 commit 해야 하고, 한쪽이라도 실패했을때 rollback을 해야한다. 이것을 한꺼번에 묶어서 관리하는것이 service.

## DB 격리수준

## 트랜잭션

일이 처리되기 위한 가장 작은 단위.

1. Request
2. Web.xml
  - DB 연결 session 생성
  - transaction 시작
3. Filter
4. Controller가 요청을 받고 요청분기
5. Service 시작
6. Repository Select하여
7. 영속성 컨텍스트에 집어넣음
8. Update
9. Controller에서 트랜잭션 종료
10. 변경감지 (flush)
11. DB 연결 세션 종료
