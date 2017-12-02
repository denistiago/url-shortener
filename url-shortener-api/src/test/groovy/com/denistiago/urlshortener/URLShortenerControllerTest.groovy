package com.denistiago.urlshortener

import com.denistiago.urlshortener.controller.URLShortenerController
import com.denistiago.urlshortener.model.URLData
import com.denistiago.urlshortener.service.URLShortenerService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@Unroll
class URLShortenerControllerTest extends Specification {

    URLShortenerController controller
    URLShortenerService urlShortenerServiceMock = Mock(URLShortenerService)

    MockMvc mvc

    def setup() {
        this.controller = new URLShortenerController(urlShortenerServiceMock)
        this.mvc = standaloneSetup(controller).build()
    }

    def "given invalid #url should return bad request"() {

        when:
        def result = postUrl(url)

        then:
        result.andExpect(status().isBadRequest())

        where:
        url << ['xxx',null]

    }

    def "given valid #url should return created status code"() {

        given:
        urlShortenerServiceMock.create(_) >> new URLData()

        when:
        def result = postUrl(url)

        then:
        result.andExpect(status().isCreated())

        where:
        url << ['https://www.google.com']

    }

    def 'given non existing hash should return not found status code'() {

        given:
        urlShortenerServiceMock.get(_) >> Optional.empty()

        when:
        def result = getByHash('hash')

        then:
        result.andExpect(status().isNotFound())

    }

    def 'given existing hash should return url'() {

        given:
        def data = new URLData(url: 'http://www.google.com')
        urlShortenerServiceMock.get(_) >> Optional.of(data)

        when:
        def result = getByHash('hash')

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.url').value(data.url))

    }


    private def postUrl(String url) {
        this.mvc.perform(post('/api/urls')
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"url": "$url"}"""))
    }

    private def getByHash(String hash) {
        this.mvc.perform(get("/api/urls/$hash")
                .accept(MediaType.APPLICATION_JSON))
    }

}
