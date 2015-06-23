# Redis Examples
The idea of this repo is to collect several examples of redis development, by using drivers like [Jedis](https://github.com/xetorthio/jedis) and frameworks like Spring and [Spring Data Redis](http://projects.spring.io/spring-data-redis/).

##redis-demo

This project contains several basic examples, nothing too complicated yet. Every example use the basic set/get from Redis. The dependencies are based on Spring Boot starters poms, so you don't need to add any other dependency. This project is based on Maven:

_It is recommendable to use a IDE (example STS for easy execution of the individual files)_ .

* **RedisSpringBootApplication.java**

This app shows the simple way to use the spring-data-redis with Spriong Boot. If you don't have any IDE then you can run it with:
```
$ mvn spring-boot:run
```
_NOTES: if you want to use a different host:port you can change it in the src/main/resources/application.properties file_

* **RedisSpringApplication.java**

This app shows a regular usage of Spring with XML configuration file. If you don't have any IDE then you can run it with:
```
$ mvn exec:java -Dexec.mainClass="io.redis.demo.RedisSpringApplication"
```

* **RedisSpringConfigApplication.java**

This app shows the Spring with Java Config class. If you don't have any IDE then you can run it with:
```
$ mvn exec:java -Dexec.mainClass="io.redis.demo.RedisSpringConfigApplication"
```

* **RedisJedisApplication.java**

This app shows the usage of the [Jedis](https://github.com/xetorthio/jedis) driver for Redis. If you don't have any IDE then you can run it with:
```
$ mvn exec:java -Dexec.mainClass="io.redis.demo.RedisJedisApplication"
```

**NOTES**:
All the examples have a Sentinel/Cluster code hasn't been tested yet.


##TO-DO
* Add Messaging Examples
* Add Cluster/Sentinel Examples
* Add .NET Examples
 
