# Core package documentation

# Table of Contents
- [Caching](#Caching)
- [Entities](#section-2)
- [Exceptions](#section-2)
- [Logging](#section-2)
- [Repository](#section-2)
- [Security](#section-2)
- [Validation](#Validation)

# Caching
1. [ICacheConfiguration](src/main/java/com/ts/core/caching/ICacheConfiguration.java) interface'ini implement eden bir configuration class'ı oluşturulur. Örnek:
   - [RedisConfiguration](src/main/java/com/ts/core/caching/examplecacheconfig/RedisConfiguration.java)
   - [CaffeineConfiguration](src/main/java/com/ts/core/caching/examplecacheconfig/CaffeineConfiguration.java)
2. application.properties dosyasında şu satır eklenir:
``` properties
caching.enabled=true
```
3. Mevcut paketteki Redis ya da Caffeine kullanılmak istenirse, application.properties dosyasında şu satırlar eklenir:
``` properties
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

# Validation
1. [IValidator](src/main/java/com/ts/core/validation/IValidator.java) interface'i validation gereken entityler için implement edilir.
- Örnek:
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
2. [Validate](src/main/java/com/ts/core/validation/Validate.java) anatosyonu kullanılarak validasyon işlemi yapılır. 
- Örnek:
``` Java
  @Validate(CityValidator.class)
  public void add(City city) {
      this.cityDal.add(city);
  }
```