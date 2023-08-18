# Core package documentation

# Table of Contents
- [Caching](#Caching)
- [Logging](#Logging)
- [Validation](#Validation)
- [Authentication & Authorization](#Authentication&Authorization)

# Caching
## 1-Config 
[ICacheConfiguration](src/main/java/com/ts/core/caching/ICacheConfiguration.java) should be implemented to use caching aspect.

   Example:
   - [RedisConfiguration](src/main/java/com/ts/core/caching/examplecacheconfig/RedisConfiguration.java)
   - [CaffeineConfiguration](src/main/java/com/ts/core/caching/examplecacheconfig/CaffeineConfiguration.java)

To use built in configs mentioned above add followings to ***application.properties*** file.


``` properties
# Caching configs
caching.enabled=true
cache.ttl=600

# Define cache config for redis add service address and port
cache.profile=redis
#cache.profile=caffeine

redis.host=localhost
redis.port=6379

```
## 2- Usage
Using [Cachable](src/main/java/com/ts/core/caching/Cacheable.java) annotation with cache name,key or keyExpression(SpEl) and cache action.
```java
    @Cacheable(cacheName = "first",key="ALL_CITIES",action = CacheAction.READ)
    public List<City> getAll() {
        return this.cityDal.getAll();
    }
    
   @Cacheable(cacheName = "second",keyExpression="#id",action = CacheAction.READ)
   public City getById(int id) {
           return this.cityDal.getById(id);
   }
```
# Logging
## 1- Config
[ILoggerConfiguration](src/main/java/com/ts/core/logging/ILoggerConfiguration.java) interface and [ILogging](src/main/java/com/ts/core/logging/ILogging.java) abstract class should be implemented to use logging aspect.
For different log logic new [ILogging](src/main/java/com/ts/core/logging/ILogging.java) implementation should be done. 

Log actions defined as :
```java
public enum LogAction {
    ERROR, INFO, WARNING, DEBUG
}
```
[ILogging](src/main/java/com/ts/core/logging/ILogging.java) have following methods.
```java
    public abstract String debug();
    public abstract String info();
    public abstract String warn();
    public abstract String error(Throwable throwable);
```
Since there is no distinct methods for before and after execution, following fields can be used to achieve this.<br> If a method need to executed only one of {beforeExecution,afterExecution}, for other part it should return null.  
```java
    ProceedingJoinPoint joinPoint = null;
    Object result = null;
    boolean isExecuted = false;
```
**joinPoint** can be used to get method signature.

**result** can be used to get result after execution of method.

**isExecuted** can be used to define log logic before and after execution.

Below example of Logging implementation.
```java
@Component
public class CityLoggingConfig extends ILogging {
    public CityLoggingConfig() {
        super();
    }

    @Override
    public String debug() {
        return super.getIsExecuted() ? "This is before method execution":"This is after method execution";
    }

    @Override
    public String info() {
        ProceedingJoinPoint joinPoint = getJoinPoint();// We can access methods parameters with this object.
        var method = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();

        return super.getIsExecuted() ?
                "Starting execution of method: "+methodName+" with parameters: "+ Arrays.stream(method).map(o->o.toString()+" ").toString()
                : "Finishing execution of method: "+ methodName + (getResult()==null ? "":" with result: "+ getResult());
    }

    @Override
    public String warn() {
        return getIsExecuted() ? "Warning before execution":"Warning after execution";
    }

    @Override
    public String error(Throwable throwable) {
        return "ERROR in "+getJoinPoint().getSignature().toString()+" Exception details: "+throwable.toString();
    }
}
```
## 2-Usage
Example implementation for [ILoggerConfiguration](src/main/java/com/ts/core/logging/ILoggerConfiguration.java):<br>
   - [JavaUtilLoggerConfiguration](src/main/java/com/ts/core/logging/exampleconfigs/JavaUtilLoggerConfiguration.java)
   - [Log4j2LoggerConfiguration](src/main/java/com/ts/core/logging/exampleconfigs/Log4j2LoggerConfiguration.java)

To use built in configs mentioned above add followings to application.properties file.
```properties
# Logging config
logging.enabled=true

# Logger profile
logger.profile=log4j
#logger.profile=util

```
Additionally Log4j need log4j2.xml file under resources folder.

[Log](src/main/java/com/ts/core/logging/Log.java) annotation used with [LogAction](src/main/java/com/ts/core/logging/LogAction.java) and logging config:
```java
@Log(log= CityLoggingConfig.class, action = {LogAction.ERROR, LogAction.INFO,LogAction.DEBUG,LogAction.WARNING})
public void add(City city) {
    this.cityDal.add(city);
}
```
# Validation
## 1- Config
[IValidator](src/main/java/com/ts/core/validation/IValidator.java) should be implemented for entity.
``` Java
@Component
public class CityValidator implements IValidator<City> {

    @Override
    public void validate(City target) throws ValidationException {
       
        // Validation code
        if (city.getName() == null || city.getName().length() < 3) {
            throw new ValidationException("User name cannot be null or empty!");
        }
    }
}
``` 
## 2-Usage 
[Validate](src/main/java/com/ts/core/validation/Validate.java) annotation used with validator implementation. 

``` Java
  @Validate(CityValidator.class)
  public void add(City city) {
      this.cityDal.add(city);
  }
```
# Authentication & Authorization
To be able to use Authentication & Authorization your User entity need to inherit from [IUser](src/main/java/com/ts/core/entities/IUser.java) abstract class.
## 1- Authentication
Authentication details in [SecurityConfiguration](src/main/java/com/ts/core/security/config/SecurityConfiguration.java)
with default settings public request accepted only under "api/auth" path, other request need authentication.
Authentication details defined in [config](src/main/java/com/ts/core/security/config) package.
## 2- Authorization
Package contains role based Authorization implementation. [Roles](src/main/java/com/ts/core/security/Role.java) are currently defined in enum and can be edited directly in enum.
### 1- Usage
[RequiredRoles](src/main/java/com/ts/core/security/RequiredRoles.java) annotation used for Authorization.<br>
Example:
```java
@RequiredRoles({Role.ADD})
public void add(City city) {
    this.cityDal.add(city);
    eventBus.publish(new CityCreatedIntegrationEvent(city));
}
```
