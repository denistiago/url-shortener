package com.denistiago.urlshortener.validation

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class URLConstraintValidatorTest extends Specification {

    def '#url should be valid'() {
        given:
        def validator = new URLConstraintValidator()

        expect:
        validator.isValid(url, null)

        where:
        url << [
                'https://www.google.com',
                'https://www.google.com?q=xxxx',
                'http://www.google.com/?q=xxx&o=xxxxx',
                'http://www.google.com/tags/bla',
                'google.com',
                'www.google.com',
                'ftp://google.com'
        ]
    }

    def '#url should not be valid'() {

        given:
        def validator = new URLConstraintValidator()

        expect:
        !validator.isValid(url, null)

        where:
        url << [
                'x',
                null,
                '',
                '1',
                '?',
                'http:\\',
                'www.xxx',
                'xxx://xx.com'
        ]

    }

}
