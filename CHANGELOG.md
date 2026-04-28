 Buession Logging Changelog
===========================

## [2.0.0](https://github.com/buession/buession-logging/releases/tag/v2.0.0) (2025-xx-xx)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v4.0.0)


### ⭐ 新特性

- **buession-logging-support-rocketmq：** 新增 RocketMQ 日志处理


---


## [1.0.1](https://github.com/buession/buession-logging/releases/tag/v1.0.1) (2025-05-20)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v3.0.1)


---


## [1.0.0](https://github.com/buession/buession-logging/releases/tag/v1.0.0) (2024-11-07)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v3.0.0)


---


## [0.0.4](https://github.com/buession/buession-logging/releases/tag/v0.0.4) (2024-05-06)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.3.3)


### ⭐ 新特性

- **buession-logging-support-console：** 新增控制台日志处理


### 🔔 变化

- **buession-logging-support-jdbc：** 新增 LogDataConverter 实现数据和字段映射


### 🐞 Bug 修复

- **ALL：** 修复 ObjectProvider.ifUnique 的使用，应使用 ObjectProvider.ifAvailable


---


## [0.0.3](https://github.com/buession/buession-logging/releases/tag/v0.0.3) (2023-12-27)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.3.2)


### 🐞 Bug 修复

- **buession-logging-support-mongodb：** 修改错误的类名 MongoHandlerFactoryBean 为 MongoLogHandlerFactoryBean


### ⏪ 优化

- **buession-logging-spring：** 优化 LogManagerFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 logManager
- **buession-logging-support-elasticsearch：** 优化 ElasticsearchLogHandlerFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 logHandler
- **buession-logging-support-elasticsearch：** 优化 ElasticsearchRestTemplateFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 restTemplate
- **buession-logging-support-elasticsearch：** 优化 RestHighLevelClientFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 client
- **buession-logging-support-file：** 优化 FileLogHandlerFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 logHandler
- **buession-logging-support-jdbc：** 优化 JdbcLogHandlerFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 logHandler
- **buession-logging-support-jdbc：** 优化 JdbcTemplateFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 jdbcTemplate
- **buession-logging-support-kafka：** 优化 KafkaLogHandlerFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 logHandler
- **buession-logging-support-kafka：** 优化 KafkaTemplateFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 kafkaTemplate
- **buession-logging-support-kafka：** 优化 ProducerFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 producerFactory
- **buession-logging-support-mongodb：** 优化 MongoLogHandlerFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 logHandler
- **buession-logging-support-mongodb：** 优化 MongoDatabaseFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 mongoDatabaseFactory
- **buession-logging-support-mongodb：** 优化 MongoMappingContextFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 mappingContext
- **buession-logging-support-mongodb：** 优化 MongoTemplateFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 mongoTemplate
- **buession-logging-support-rabbitmq：** 优化 RabbitLogHandlerFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 logHandler
- **buession-logging-support-rabbitmq：** 优化 ConnectionFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 connectionFactory
- **buession-logging-support-rabbitmq：** 优化 RabbitTemplateFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 rabbitTemplate
- **buession-logging-support-rest：** 优化 RestLogHandlerFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 logHandler


---


## [0.0.2](https://github.com/buession/buession-logging/releases/tag/v0.0.2) (2023-11-19)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.3.1)


---


## [0.0.1](https://github.com/buession/buession-logging/releases/tag/v0.0.1) (2023-08-17)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.3.0)


### ⭐ 新特性

- **buession-logging-support-elasticsearch：** Elasticsearch 日志处理器
- **buession-logging-support-file：** 文本日志处理器
- **buession-logging-support-jdbc：** JDBC 日志处理器
- **buession-logging-support-kafka：** Kafka 日志处理器
- **buession-logging-support-mongodb：** MongoDB 日志处理器
- **buession-logging-support-rabbitmq：** RabbitMQ 日志处理器
- **buession-logging-support-rest：** Rest 日志处理器
- **buession-logging-annotations：** 注解
- **buession-logging-aspectj：** aspectj