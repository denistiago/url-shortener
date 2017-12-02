package com.denistiago.urlshortener.controller

import com.denistiago.urlshortener.model.URLData
import com.denistiago.urlshortener.service.URLShortenerService
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
@RequestMapping(
        value = '/api/urls',
        produces = [MediaType.APPLICATION_JSON_VALUE]
)
@CrossOrigin
class URLShortenerController {

    URLShortenerService urlShortenerService

    URLShortenerController(URLShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    URLData create(@Validated @RequestBody URLData urlData) {
        log.info('create {}', urlData)
        urlShortenerService.create(urlData)
    }

    @GetMapping(value = '/{hash}')
    ResponseEntity<URLData> get(@PathVariable String hash) {
        log.info('get url {}', hash)
        urlShortenerService.get(hash)
                .map({ ResponseEntity.ok(it)})
                .orElse(ResponseEntity.notFound().build())
    }

}
