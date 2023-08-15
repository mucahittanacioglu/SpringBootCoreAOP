# Core package documentation

# Table of Contents
- [Caching](#Caching)
- [Entities](#section-2)
- [Exceptions](#section-2)
- [Logging](#section-2)
- [Repository](#section-2)
- [Security](#section-2)
- [Validation](#section-2)

# Caching
1. [ICacheConfiguration](src/main/java/com/ts/core/caching/ICacheConfiguration.java) interface'ini implement eden bir configuration class'ı oluşturulur. Örnek:
   - [RedisConfiguration](src/main/java/com/ts/core/caching/examplecacheconfig/RedisConfiguration.java)
   - [CaffeineConfiguration](src/main/java/com/ts/core/caching/examplecacheconfig/CaffeineConfiguration.java)
2. application.properties dosyasında şu satır eklenir:
```
caching.enabled=true
```
3. Mevcut paketteki Redis ya da Caffeine kullanılmak istenirse, application.properties dosyasında şu satırlar eklenir:
```
#Caching configs
redis.host=localhost
redis.port=6379
cache.ttl=600
cache.profile=redis
#cache.profile=caffeine
```

## Subsection 1.1
Content for subsection 1.1
## Subsection 1.2
Content for subsection 1.2

# Section 2
## Subsection 2.1
Content for subsection 2.1