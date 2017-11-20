package pl.artcoder.playground.videostore

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.ResultActions
import pl.artcoder.playground.videostore.base.IntegrationSpec
import pl.artcoder.playground.videostore.film.application.SaveFilmCommand
import pl.artcoder.playground.videostore.film.commons.SampleFilms
import pl.artcoder.playground.videostore.film.domain.Film
import pl.artcoder.playground.videostore.user.application.AddUserCommand
import pl.artcoder.playground.videostore.user.commons.SampleUsers
import pl.artcoder.playground.videostore.user.domain.User

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class VideostoreAcceptanceSpec extends IntegrationSpec implements SampleFilms, SampleUsers {

    @Autowired
    SaveFilmCommand saveFilmCommand

    @Autowired
    AddUserCommand addUserCommand

    @WithMockUser
    def "positive renting scenario"() {
        given: 'inventory has an old film "Metropolis" and a new release of "Blade Runner 2049"'
        Film metropolis = aSavedOldFilm()
        Film bladeRunner = aSavedNewFilm()

        and: 'db contains default user'
        User user = aSavedDefaultUser()

        when: 'I go to /films'
        ResultActions getFilms = mockMvc.perform(get("/films"))
        then: 'I see both films'
        getFilms.andExpect(status().isOk())
                .andExpect(content().json("""
                {
                    "content": [
                        {"title":"$metropolis.title.title","type":"$metropolis.filmType"},
                        {"title":"$bladeRunner.title.title","type":"$bladeRunner.filmType"}
                    ]
                }"""))

        when: 'I go to /points'
        then: 'I see I have no points'

        when: 'I post to /calculate with both filmTitles for 3 days'
        then: 'I can see it will cost me 120 EURO for Trumpet and 30 EURO for Clingon'

        when: 'I post to /rent with both firms for 3 days'
        then: 'I have rented both movies'

        when: 'I go to /rent'
        then: 'I see both movies are rented'

        when: 'I go to /points'
        then: 'I see I have 3 points'

        when: 'I post to /return with Trumper'
        then: 'trumper is returned'

        when: 'I go to /rent'
        then: 'I see only Clingon is rented'
    }

    private Film aSavedOldFilm() {
        saveFilmCommand.execute(
                createOldFilm()
        )
    }

    private Film aSavedNewFilm() {
        saveFilmCommand.execute(
                createNewFilm()
        )
    }

    private User aSavedDefaultUser() {
        addUserCommand.execute(
                defaultUser
        )
    }

}
