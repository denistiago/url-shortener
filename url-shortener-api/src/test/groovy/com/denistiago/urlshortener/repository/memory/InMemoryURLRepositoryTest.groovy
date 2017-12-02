package com.denistiago.urlshortener.repository.memory

import com.denistiago.urlshortener.repository.URLRepository
import com.denistiago.urlshortener.repository.URLRepositorySpecification
import spock.lang.Unroll

@Unroll
class InMemoryURLRepositoryTest extends URLRepositorySpecification  {

    @Override
    URLRepository getRepository() {
        return new InMemoryURLRepository()
    }

}