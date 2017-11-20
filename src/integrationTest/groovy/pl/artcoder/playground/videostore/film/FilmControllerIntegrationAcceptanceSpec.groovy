package pl.artcoder.playground.videostore.film

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.ResultActions
import pl.artcoder.playground.videostore.base.IntegrationSpec
import pl.artcoder.playground.videostore.film.application.SaveFilmCommand
import pl.artcoder.playground.videostore.film.commons.SampleFilms
import pl.artcoder.playground.videostore.film.domain.Film

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class FilmControllerIntegrationAcceptanceSpec extends IntegrationSpec implements SampleFilms {
    @Autowired
    SaveFilmCommand saveFilm

    @WithMockUser
    def "should get films"() {
        given: 'inventory has sample film'
        Film film = createOldFilm()
        saveFilm.execute(film)

        when: 'I go to /films'
        ResultActions getFilms = mockMvc.perform(get("/films"))

        then: 'I see all repo'
        getFilms.andExpect(status().isOk())
                .andExpect(content().json("""
                {
                    "content": [
                        {"title":"$film.title.title","type":"$film.filmType"}
                    ]
                }"""))

        when: 'I go to /film/'
        ResultActions getFilm = mockMvc.perform(get("/film/$film.title.title"))

        then: 'I see details of that film'
        getFilm.andExpect(status().isOk())
                .andExpect(content().json("""
                        {"title":"$film.title.title","type":"$film.filmType"}
                """))
    }
}
