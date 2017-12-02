package com.denistiago.urlshortener.repository.redis

import com.denistiago.urlshortener.repository.URLRepository
import com.denistiago.urlshortener.repository.URLRepositorySpecification
import groovy.util.logging.Slf4j
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import redis.embedded.RedisServer
import spock.lang.Shared

@Slf4j
class RedisURLRepositoryTest extends URLRepositorySpecification {

    static final int REDIS_PORT = 6390

    @Shared redisServer = new RedisServer(REDIS_PORT)

    def setupSpec() {
        redisServer.start()
    }

    @Override
    URLRepository getRepository() {
        RedisConnectionFactory connectionFactory = new JedisConnectionFactory(port: REDIS_PORT)
        connectionFactory.afterPropertiesSet()
        return new RedisURLRepository(connectionFactory)
    }

    def cleanupSpec() {
        redisServer.stop()
    }

}
