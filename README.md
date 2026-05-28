## Clean Architecture

본 프로젝트는 비즈니스 로직과 외부 기술 의존성을 분리하기 위해 Clean Architecture를 적용했습니다.

### Flow

```text
Controller / Consumer
        ↓
UseCase
        ↓
Application Service
        ↓
Port
        ↓
Adapter
        ↓
DB
```
### Package Structure
```text
src/main/java/com/example/item
├── domain
│   ├── model          # 도메인 모델
│   └── exception      # 도메인 예외
│
├── application
│   ├── port
│   │   ├── in         # UseCase 인터페이스
│   │   └── out        # 외부 시스템 접근 Port
│   ├── service        # UseCase 구현체
│   └── dto
│       ├── command    # UseCase 입력 DTO
│       └── result     # UseCase 결과 DTO
│
├── adapter
│   ├── in
│   │   ├── web        # Controller
│   │   └── kafka      # Kafka Consumer
│   └── out
│       ├── persistence # JPA 구현
│       ├── kafka       # Kafka Producer
│       └── redis       # Redis 구현
│
├── config             # 설정 클래스
└── global             # 공통 응답, 예외 처리
```

### Rule
* Controller와 Consumer는 UseCase를 호출한다.
* Application Service는 UseCase를 구현한다.
* Service는 Repository가 아닌 Port에 의존한다.
* JPA, Kafka, Redis 구현은 Adapter 계층에서 처리한다.
* Domain은 Spring, JPA 등 외부 기술에 의존하지 않는다. dddd
