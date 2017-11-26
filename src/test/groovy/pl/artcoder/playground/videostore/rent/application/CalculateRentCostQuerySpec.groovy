package pl.artcoder.playground.videostore.rent.application

import pl.artcoder.playground.videostore.common.domain.Money
import pl.artcoder.playground.videostore.film.commons.SampleFilms
import pl.artcoder.playground.videostore.film.domain.Film
import pl.artcoder.playground.videostore.film.domain.Title
import pl.artcoder.playground.videostore.film.infrastructure.configuration.FilmModuleConfiguration
import pl.artcoder.playground.videostore.rent.infrastructure.configuration.RentModuleConfiguration
import spock.lang.Specification
import spock.lang.Subject

import static io.vavr.API.None
import static io.vavr.API.Some

class CalculateRentCostQuerySpec extends Specification implements SampleFilms {
    FilmModuleConfiguration filmConfig = new FilmModuleConfiguration()
    RentModuleConfiguration rentConfig = new RentModuleConfiguration()

    @Subject
    CalculateRentCostQuery calculateRentCostQuery

    Film film

    def setup() {
        calculateRentCostQuery = calculateRentCostQuery()
    }

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

    def "should calculate rent cost for given nr.of days and REGULAR film type"() {
        given:
        film = aSavedRegularFilm()

        expect:
        calculateRentCostQuery.execute(film.title, daysOfRental) == expectedRentCostResult(film.title, Money.of(cost))

        where:
        daysOfRental || cost
        1            || 30
        3            || 30
        5            || 90
    }

    def "should calculate rent cost for given nr.of days and OLD film type"() {
        given:
        film = aSavedOldFilm()

        expect:
        calculateRentCostQuery.execute(film.title, daysOfRental) == expectedRentCostResult(film.title, Money.of(cost))

        where:
        daysOfRental || cost
        1            || 30
        5            || 30
        7            || 90
    }

    def "should yield None result for unknown title film"() {
        given:
        Title unknownTitle = Title.from("Spiderman")

        expect:
        calculateRentCostQuery.execute(unknownTitle, 1) == None()
    }

    private def aSavedNewFilm() {
        filmConfig.saveFilmCommand().execute(
                createNewFilm(filmConfig.filmFactory())
        )
    }

    private def aSavedRegularFilm() {
        filmConfig.saveFilmCommand().execute(
                createRegularFilm(filmConfig.filmFactory())
        )
    }

    private def aSavedOldFilm() {
        filmConfig.saveFilmCommand().execute(
                createOldFilm(filmConfig.filmFactory())
        )
    }

    private def expectedRentCostResult(Title title, Money cost) {
        Some(CalculateRentCostQuery.RentCost.of(title, cost))
    }

    private def calculateRentCostQuery() {
        new CalculateRentCostQuery(
                filmConfig.showFilmQuery(),
                rentConfig.dailyRentPriceProvider()
        )
    }

    private void removeSavedFilm() {
        if (film != null)
            filmConfig.filmRepository().delete(film)
        film = null
    }
}
