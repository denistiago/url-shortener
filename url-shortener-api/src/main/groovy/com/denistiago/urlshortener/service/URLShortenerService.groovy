package com.denistiago.urlshortener.service

import com.denistiago.urlshortener.model.URLData
import com.denistiago.urlshortener.repository.URLRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class URLShortenerService {

    URLRepository repository

    @Autowired
    URLShortenerService(URLRepository repository) {
        this.repository = repository
    }

    URLData create(URLData urlData) {
        repository.save(urlData)
    }

    Optional<URLData> get(String hash) {
        repository.get(hash)
    }

}
