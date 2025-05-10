
# 📦 실시간 주문 및 물류 추적 시스템 PRD

## 1. 개요

**프로젝트명**: 실시간 주문 및 물류 추적 시스템  
**목표**:  
고객 주문을 접수하고, 물류 상태(주문 접수 → 포장 → 배송 중 → 배송 완료)를 실시간으로 추적할 수 있는 백엔드 시스템을 Java 기반으로 구축합니다.  
ERP의 주문 관리(Order Management) 및 물류 관리(Logistics Management) 모듈을 반영하여 주문 처리 및 배송 프로세스를 모델링합니다.

---

## 2. 기술 스택

- Java 17  
- Spring Boot  
- Spring Data JPA  
- PostgreSQL  
- Redis  
- Spring AMQP (RabbitMQ)  
- Spring Boot Actuator  
- Swagger / OpenAPI  

---

## 3. 기능 상세

### 3.1 주문 생성 API

- **Method**: `POST`  
- **URL**: `/api/orders`  
- **기능**: 고객 정보 및 상품 목록을 받아 주문을 DB에 저장  
- **Request Body**:
```json
{
  "customerId": 1001,
  "items": [
    { "productId": 2001, "quantity": 2 },
    { "productId": 2003, "quantity": 1 }
  ],
  "deliveryAddress": "서울특별시 송파구 ..."
}
```

---

### 3.2 주문 상태 변경 API

- **Method**: `PATCH`  
- **URL**: `/api/orders/{id}/status`  
- **기능**: 주문 상태를 `RECEIVED`, `PACKING`, `SHIPPING`, `DELIVERED` 중 하나로 갱신하고, Redis에 캐싱  
- **Request Body**:
```json
{
  "status": "SHIPPING"
}
```

---

### 3.3 배송 상태 조회 API

- **Method**: `GET`  
- **URL**: `/api/orders/{id}/track`  
- **기능**: 주문 ID 기준으로 현재 배송 상태 및 이력 반환  
- **Response 예시**:
```json
{
  "orderId": 789,
  "status": "SHIPPING",
  "history": [
    { "status": "RECEIVED", "timestamp": "2025-05-10T09:00:00Z" },
    { "status": "PACKING", "timestamp": "2025-05-10T10:00:00Z" }
  ]
}
```

---

### 3.4 비동기 알림 (RabbitMQ)

- 주문 상태가 변경되면 RabbitMQ를 통해 이벤트를 발행하여 알림 시스템 혹은 관리자 시스템과 연동

---

## 4. 성과물

- Swagger(OpenAPI) 기반 API 문서
- JPA 기반의 ERD 설계
- JMeter를 활용한 성능 테스트 리포트 (동시 500명 요청 시 처리량/지연 분석 포함)

---

## 5. 구현 세부사항

### 5.1 주문 데이터 처리

- Spring Data JPA 사용  
- 트랜잭션은 `@Transactional`로 처리  
- 주문, 고객, 상품, 배송 상태에 대한 Entity 설계

### 5.2 Redis 캐싱 전략

- RedisTemplate 활용  
- 주문 상태는 TTL 기반으로 단기 캐싱  
- 자주 조회되는 상태에 대한 빠른 응답을 보장

### 5.3 API 모니터링

- Spring Boot Actuator로 운영 상태 및 메트릭 제공  
- `/actuator/health`, `/actuator/metrics` 등 엔드포인트 활성화  



## 6. 기대 효과 및 역량 증명

- Spring Boot + JPA 기반 REST API 설계 및 구현 능력  
- Redis + RabbitMQ 활용한 고성능, 비동기 백엔드 설계 경험  
- ERP 시스템에 준하는 주문/물류 흐름 구현 경험  
- 컬리와 같은 커머스 시스템 요구사항 대응 가능

