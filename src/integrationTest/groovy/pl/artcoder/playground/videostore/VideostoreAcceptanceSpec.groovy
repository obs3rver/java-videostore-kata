package pl.artcoder.playground.videostore

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.ResultActions
import pl.artcoder.playground.videostore.base.IntegrationSpec
import pl.artcoder.playground.videostore.film.application.SaveFilmCommand
import pl.artcoder.playground.videostore.film.commons.SampleFilms
import pl.artcoder.playground.videostore.film.domain.Film
import pl.artcoder.playground.videostore.film.infrastructure.boundary.rest.viewmodel.FilmJson
import pl.artcoder.playground.videostore.rent.infrastructure.boundary.rest.requestmodel.CalculateRentCostRequestJson
import pl.artcoder.playground.videostore.rent.infrastructure.boundary.rest.viewmodel.RentCostJson
import pl.artcoder.playground.videostore.user.application.AddUserCommand
import pl.artcoder.playground.videostore.user.commons.SampleUsers
import pl.artcoder.playground.videostore.user.domain.User

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
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
                .andExpect(content().json(asPageJsonString(aFilmPageExpectedResult([metropolis, bladeRunner]))))

        when: 'I go to /points'
        then: 'I see I have no points'

        when: 'I post to /calculate with both film titles for 3 days'
        def calcRequest = aCalculateRentCostRequest([metropolis, bladeRunner], 3)
        ResultActions postCalculate = mockMvc.perform(
                post("/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(calcRequest))
        )
        then: 'I can see it will cost me 120 EURO for Blade Runner and 30 EURO for Metropolis'
        def metropolisCalcResponse = aCalculateRentCostResponse(metropolis, 30.0)
        def bladeRunnerCalcResponse = aCalculateRentCostResponse(bladeRunner, 120.0)
        postCalculate
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString([metropolisCalcResponse, bladeRunnerCalcResponse])))

        when: 'I post to /rent with both firms for 3 days'
        then: 'I have rented both movies'

        when: 'I go to /rent'
        then: 'I see both movies are rented'

        when: 'I go to /points'
        then: 'I see I have 3 points'

        when: 'I post to /return with Metropolis'
        then: 'Metropolis is returned'

        when: 'I go to /rent'
        then: 'I see only Blade Runner is rented'
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

    private def aFilmPageExpectedResult(List<Film> films) {
        films.collect { FilmJson.fromDomain(it) }
    }

    private def aCalculateRentCostRequest(List<Film> films, int rentalDays) {
        CalculateRentCostRequestJson.of(
                films.collect { it.title.value() },
                rentalDays
        )
    }

    private def aCalculateRentCostResponse(Film film, double expectedCost) {
        RentCostJson.of(film.title.value(), BigDecimal.valueOf(expectedCost))
    }

}
