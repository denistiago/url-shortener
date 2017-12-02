package com.denistiago.urlshortener.model

import com.denistiago.urlshortener.validation.URL
import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty

@ToString(includeNames = true, includePackage = false, ignoreNulls = true)
class URLData {

    @NotEmpty
    @URL
    String url
    String hash

}