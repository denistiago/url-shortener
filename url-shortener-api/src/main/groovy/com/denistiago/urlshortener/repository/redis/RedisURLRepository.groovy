package com.denistiago.urlshortener.repository.redis

import com.denistiago.urlshortener.model.URLData
import com.denistiago.urlshortener.repository.URLRepository
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisCallback
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.support.atomic.RedisAtomicLong
import org.springframework.stereotype.Repository

import static com.denistiago.urlshortener.repository.Base62.decode
import static com.denistiago.urlshortener.repository.Base62.encode

@Repository
@Profile('RedisStorage')
class RedisURLRepository implements URLRepository {

    private static final String URL_DATA_BY_ID_KEY = 'id_to_url_data:'
    private static final String ID_BY_URL_KEY = 'url_to_id:'
    private static final String ID_KEY = "id:"

    RedisTemplate<String, URLData> redisTemplate
    RedisAtomicLong redisAtomicLong

    RedisURLRepository(RedisConnectionFactory connectionFactory) {
        this.redisTemplate = newRedisTemplate(connectionFactory)
        this.redisAtomicLong = new RedisAtomicLong(ID_KEY, connectionFactory)
    }

    private RedisTemplate<String, URLData> newRedisTemplate(RedisConnectionFactory connectionFactory) {
        def redisTemplate = new RedisTemplate<>()
        redisTemplate.valueSerializer  = new GenericJackson2JsonRedisSerializer()
        redisTemplate.connectionFactory = connectionFactory
        redisTemplate.afterPropertiesSet()
        redisTemplate
    }

    @Override
    Optional<URLData> get(String hash) {
        long id = decode(hash)
        Optional.ofNullable(redisTemplate.opsForValue()
                .get(urlDataKey(id)))
    }

    @Override
    URLData save(URLData urlData) {

        def id = Optional.ofNullable(getIdByUrl(urlData.url))
                .orElseGet( {
            def newId = redisAtomicLong.incrementAndGet()
            urlData.hash = encode(newId)
            redisTemplate.opsForValue().set(urlDataKey(newId), urlData)
            redisTemplate.opsForHash().put(ID_BY_URL_KEY, urlData.url, newId)
            newId

        })

        urlData.with {
            hash = encode(id)
            it
        }
    }

    @Override
    long count() {
        return redisTemplate.opsForHash().size(ID_BY_URL_KEY)
    }

    @Override
    void clear() {
        redisTemplate.execute( { connection ->
            connection.flushDb()
        } as RedisCallback<Void>)
    }

    private Long getIdByUrl(String url) {
        redisTemplate.opsForHash()
                .get(ID_BY_URL_KEY, url) as Long
    }

    private String urlDataKey(long id) {
        URL_DATA_BY_ID_KEY + id
    }
}
