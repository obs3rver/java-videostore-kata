package pl.artcoder.playground.videostore.film.infrastructure.rest

import org.springframework.test.web.servlet.ResultActions
import pl.artcoder.playground.videostore.base.IntegrationSpec
import pl.artcoder.playground.videostore.film.commons.SampleFilms

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class FilmControllerIntegrationSpec extends IntegrationSpec implements SampleFilms {

    def "should not get film with non-existing title"() {
        given: 'Non existing film title'
        String nonExistingTitle = "Spiderman"

        when: 'I go to /film/'
        ResultActions getFilm = mockMvc.perform(get("/film/$nonExistingTitle"))

        then: 'I see error message'
        getFilm.andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {"message":"No film of title $nonExistingTitle found"}
                """))
    }
}
