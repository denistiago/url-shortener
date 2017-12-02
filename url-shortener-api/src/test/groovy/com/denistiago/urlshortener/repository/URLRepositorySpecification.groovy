package com.denistiago.urlshortener.repository

import com.denistiago.urlshortener.model.URLData
import spock.lang.Specification
import spock.lang.Unroll

import static java.util.stream.Collectors.toList

@Unroll
abstract class URLRepositorySpecification extends Specification {

    abstract URLRepository getRepository()

    def setup() {
        repository.clear()
    }

    def 'given different urls should return different hashes'() {
        given:
        def ids = [] as Set<String>
        ids << repository.save(new URLData(url: "https://www.google.com")).hash
        ids << repository.save(new URLData(url: "https://www.facebook.com")).hash
        ids << repository.save(new URLData(url: "https://www.twitter.com")).hash
        expect:
        ids.size() == 3
    }

    def 'given same url should return same hash'() {
        given:
        def url = "https://www.google.com"
        when:
        def hash1 = repository.save(new URLData(url: url)).hash
        def hash2 = repository.save(new URLData(url: url)).hash
        then:
        hash1 == hash2
    }

    def 'given hash should return url'() {
        given:
        def url = "https://www.google.com"
        when:
        def hash = repository.save(new URLData(url: url)).hash
        then:
        url == repository.get(hash).get().url
    }

    def 'given different urls in parallel should save all'() {

        given:
        def urls = (1..100).collect { "http://www.${it}.com" }

        when:
        List<URLData> created = saveInParallel(urls)

        then:
        created.every {
            repository.get(it.hash).isPresent()
        }

    }

    def 'given duplicated urls in parallel should save only uniques'() {

        Random random = new Random()

        given:
        def urls = (1..100).collect { "http://www.${random.nextInt(10)}.com" }

        when:
        saveInParallel(urls)

        then:
        urls.unique().size() == repository.count()

    }

    List saveInParallel(List<String> urls) {
        urls.parallelStream()
                .map({ repository.save(new URLData(url: it)) })
                .collect(toList())
    }

}
