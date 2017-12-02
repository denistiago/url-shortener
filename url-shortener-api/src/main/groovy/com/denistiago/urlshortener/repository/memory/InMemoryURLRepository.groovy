package com.denistiago.urlshortener.repository.memory

import com.denistiago.urlshortener.model.URLData
import com.denistiago.urlshortener.repository.Base62
import com.denistiago.urlshortener.repository.URLRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

import static com.denistiago.urlshortener.repository.Base62.decode
import static com.denistiago.urlshortener.repository.Base62.encode

@Repository
@Profile('InMemoryStorage')
class InMemoryURLRepository implements URLRepository {

    private static final AtomicLong ID_GENERATOR = new AtomicLong()
    private static final Map<Long, URLData> URL_BY_ID = new ConcurrentHashMap<>()
    private static final Map<String, Long> ID_BY_URL = new ConcurrentHashMap<>()

    @Override
    Optional<URLData> get(String hash) {
        Optional.ofNullable(URL_BY_ID.get(decode(hash)))
    }

    @Override
    URLData save(URLData urlData) {

        def id = ID_BY_URL.computeIfAbsent(urlData.url, { _ ->
            def id = ID_GENERATOR.incrementAndGet()
            urlData.hash = encode(id)
            URL_BY_ID.put(id, urlData)
            id
        })

        urlData.with {
            hash =  encode(id)
            it
        }
    }

    @Override
    long count() {
        return URL_BY_ID.size()
    }

    @Override
    void clear() {
        ID_BY_URL.clear()
        URL_BY_ID.clear()
        ID_GENERATOR.set(0)
    }
}
