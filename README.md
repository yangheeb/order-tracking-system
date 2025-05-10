# order-tracking-system
2025 바이브코딩 워크숍

## 개요
이 프로젝트는 취업을 위해서 만든 학습 프로젝트입니다.
목표로 하는 포지션(Job Description)은 다음과 같습니다.

https://www.catch.co.kr/NCS/RecruitInfoDetails/312003

기획은 Grok3를 사용하였고, Cursor를 이용한 바이브 코딩으로 구현하였습니다.

## 프로젝트 상세

## 실시간 주문 처리 및 물류 추적 시스템
실시간으로 물류의 이동 상태를 추적하고 관리하는 백엔드 시스템을 구축합니다. 이 시스템은 주문 접수, 창고 처리, 배송 상태 업데이트, 고객 알림 기능을 포함합니다. RESTful API를 설계하고, 데이터베이스에 물류 데이터를 저장하며, 확장 가능한 아키텍처를 구현합니다.



**기술 스택** : Spring Boot (Java), MySQL/PostgreSQL, Redis (캐싱), Docker (컨테이너화).

**주요 기능**:
- 주문 생성 및 상태 업데이트 API (예: POST /orders, PATCH /orders/{id}/status).
- 배송 경로 조회 API (GET /shipments/{id}/track).
물류 상태 변경 시 고객에게 알림을 보내는 비동기 이벤트 처리 (Kafka 또는 RabbitMQ 사용).
- 대량 데이터 처리 최적화 (예: 배치 처리로 물류 데이터 업데이트).


**성과물**:
- API 문서 (Swagger/Postman).
- 물류 데이터의 CRUD 작업을 위한 데이터베이스 스키마.
- 성능 테스트 결과 (예: JMeter로 API 응답 시간 측정).

**증명 가능한 역량**:
- 물류 시스템의 핵심 비즈니스 로직 이해 및 구현 능력.
- RESTful API 설계 및 데이터베이스 관리 능력.
- 비동기 처리와 확장 가능한 시스템 설계 역량.
- 이커머스 환경에서 요구되는 실시간 데이터 처리 능력.