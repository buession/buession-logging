 Buession Logging Changelog
===========================


## [1.0.0](https://github.com/buession/buession-logging/releases/tag/v1.0.0) (2023-xx-xx)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.3.0)


### 🔔 变化

- **buession-core：** 废弃 com.buession.core.serializer.type.TypeReference 使用 com.buession.core.type.TypeReference【3.0.0 版本删除】
- **buession-core：** 将序列化类，拆分成序列化和反序列化
- **buession-dao：** 废弃 DefaultEnumTypeHandler 使用 mybatis 原生 EnumTypeHandler【3.0.0 版本删除】
- **buession-dao：** masterSqlSessionTemplate、slaveSqlSessionTemplates、masterMongoTemplate、slaveMongoTemplates 异常注入注解 @Resource，使用 setter 注入
- **buession-httpclient：** 废弃 Request.setUrl(String url) 使用 Request.setUri(URI uri) 替代【3.0.0 版本删除】
- **buession-web：** AbstractRestController 添加主键类型、数据传输对象类型、数据输出对象类型
- **buession-redis：** 废弃 bitfield 通过可变参数传参


### ⭐ 新特性

- **buession-core：** 新增类型引用类 com.buession.core.type.TypeReference
- **buession-core：** 新增配置器接口 Configurer
- **buession-core：** 新增线程池配置类 ThreadPoolConfiguration
- **buession-httpclient：** 新增实验性 HTTP 异步请求客户端
- **buession-httpclient：** 请求方法支持传 URI
- **buession-httpclient：** 支持为每次请求单独配置 readTimeout
- **buession-redis：** bitfield API 支持通过 BitFieldArgument 传参
- **buession-net：** SslConfiguration 增加 sslContext 属性


### 🐞 Bug 修复

- **buession-redis：** 修复 Jedis StringCommands.SetArgument 设置过期时间戳，处理成过期时间的 BUG
- **buession-redis：** 修复 Client 对象返回的 cmd 类型错误的 BUG
- **buession-jdbc：** 修复 DataSource 未设置 PoolConfiguration 创建原生 DataSource 空指针 BUG
- **buession-httpclient：** 修复 HttpClient request 方法，无法发送 report、proppatch 请求 BUG
- **buession-httpclient：** 修复 OkHttpClientConnectionManager 中错误设置 IdleConnectionTime 的 BUG
- **buession-web：** 修复 AbstractBasicRestController 无法调用重写 pageNotFound(final String uri) 方法 BUG
- **buession-web：** 修复 ServerInfoFilter 通过 setHeaderName 方法设置响应头名称无效的 BUG
- **buession-web：** 修复 ServerInfoFilter 通过构造函数设置响应头名称未进行有效性验证的 BUG


### ⏪ 优化

- **buession-httpclient：** 内部优化
- 其它优化