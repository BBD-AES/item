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
src/main/java/com/bbd/item
├── ItemApplication.java
│
├── domain
│   ├── model
│   │   └── Item.java
│   └── exception
│       ├── ItemDomainException.java
│       └── ItemOutOfStockException.java
│
├── application
│   ├── port
│   │   ├── in
│   │   │   ├── CreateItemUseCase.java
│   │   │   ├── GetItemUseCase.java
│   │   │   ├── DecreaseStockUseCase.java
│   │   │   └── IncreaseStockUseCase.java
│   │   │
│   │   └── out
│   │       ├── SaveItemPort.java
│   │       ├── LoadItemPort.java
│   │       └── PublishItemEventPort.java
│   │
│   ├── service
│   │   └── ItemService.java
│   │
│   └── dto
│       ├── command
│       │   ├── CreateItemCommand.java
│       │   └── DecreaseStockCommand.java
│       │
│       └── result
│           └── ItemResult.java
│
├── adapter
│   ├── in
│   │   ├── web
│   │   │   ├── ItemController.java
│   │   │   └── dto
│   │   │       ├── CreateItemRequest.java
│   │   │       └── ItemResponse.java
│   │   │
│   │   └── kafka
│   │       ├── StockEventConsumer.java
│   │       └── dto
│   │           └── StockDecreaseMessage.java
│   │
│   └── out
│       ├── persistence
│       │   ├── ItemJpaEntity.java
│       │   ├── ItemJpaRepository.java
│       │   ├── ItemPersistenceAdapter.java
│       │   └── ItemPersistenceMapper.java
│       │
│       └── kafka
│           ├── ItemKafkaProducer.java
│           ├── ItemEventPublisherAdapter.java
│           └── dto
│               └── ItemStockChangedMessage.java
│
├── config
│   ├── SwaggerConfig.java
│   ├── JpaConfig.java
│   └── KafkaConfig.java
│
└── global
    ├── error
    │   ├── GlobalExceptionHandler.java
    │   ├── ApiException.java
    │   └── ErrorResponse.java
    │
    └── response
        └── ApiResponse.java
```

### Rule
* Controller와 Consumer는 UseCase를 호출한다.
* Application Service는 UseCase를 구현한다.
* Service는 Repository가 아닌 Port에 의존한다.
* JPA, Kafka, Redis 구현은 Adapter 계층에서 처리한다.
* Domain은 Spring, JPA 등 외부 기술에 의존하지 않는다.
