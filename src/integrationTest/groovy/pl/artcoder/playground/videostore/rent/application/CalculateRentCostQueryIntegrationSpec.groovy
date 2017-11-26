package pl.artcoder.playground.videostore.rent.application

import org.springframework.beans.factory.annotation.Autowired
import pl.artcoder.playground.videostore.base.IntegrationSpec
import pl.artcoder.playground.videostore.common.domain.Money
import pl.artcoder.playground.videostore.film.application.SaveFilmCommand
import pl.artcoder.playground.videostore.film.commons.SampleFilms
import pl.artcoder.playground.videostore.film.domain.Film
import pl.artcoder.playground.videostore.film.domain.FilmRepository
import pl.artcoder.playground.videostore.film.domain.Title
import spock.lang.Subject

import static io.vavr.API.Some

class CalculateRentCostQueryIntegrationSpec extends IntegrationSpec implements SampleFilms {
    @Subject
    @Autowired
    CalculateRentCostQuery calculateRentCostQuery

    @Autowired
    SaveFilmCommand saveFilmCommand

    @Autowired
    FilmRepository filmRepository

    Film film

    def cleanup() {
        removeSavedFilm()
    }

    def "should calculate rent cost for given nr.of days and NEW film type"() {
        given:
        film = aSavedNewFilm()

        expect:
        calculateRentCostQuery.execute(film.title, daysOfRental) == expectedRentCostResult(film.title, Money.of(cost))

        where:
        daysOfRental || cost
        1            || 40
        3            || 120
        5            || 200
    }

    private def aSavedNewFilm() {
        saveFilmCommand.execute(
                createNewFilm()
        )
    }

    private def expectedRentCostResult(Title title, Money cost) {
        Some(CalculateRentCostQuery.RentCost.of(title, cost))
    }

    private void removeSavedFilm() {
        if (film != null)
            filmRepository.delete(film)
        film = null
    }
}
