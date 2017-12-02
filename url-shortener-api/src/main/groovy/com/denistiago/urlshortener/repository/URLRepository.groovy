package com.denistiago.urlshortener.repository

import com.denistiago.urlshortener.model.URLData

interface URLRepository {

    Optional<URLData> get(String hash)

    URLData save(URLData urlData)

    long count()

    void clear()

}
