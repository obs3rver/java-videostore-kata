package pl.artcoder.playground.videostore.film.application

import io.vavr.control.Option
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import pl.artcoder.playground.videostore.film.domain.Film
import pl.artcoder.playground.videostore.film.domain.FilmFactory
import pl.artcoder.playground.videostore.film.domain.FilmRepository
import pl.artcoder.playground.videostore.film.domain.Title
import pl.artcoder.playground.videostore.film.infrastructure.configuration.FilmModuleConfiguration
import spock.lang.Specification
import spock.lang.Subject

class FilmModuleSpec extends Specification {
    FilmModuleConfiguration config = new FilmModuleConfiguration()

    FilmFactory filmFactory

    FilmRepository filmRepository

    @Subject
    SaveFilm saveFilm

    @Subject
    ShowFilm showFilm

    Film film

    String filmTitle = "Bolek i Lolek"

    def setup() {
        saveFilm = config.saveFilm()
        showFilm = config.showFilm()
        filmFactory = config.filmFactory()
        filmRepository = config.filmRepository()
    }

    def cleanup() {
        filmRepository.delete(film)
        film = null
    }

    def "Saved film should be able to be found by id"() {
        given:
        film = aSavedFilm()

        when:
        Option<Film> maybeFilm = showFilm.byId(film.getId())

        then:
        maybeFilm.isDefined()
        maybeFilm.get().getTitle().value() == filmTitle
    }

    def "Saved film should be able to be found by title"() {
        given:
        film = aSavedFilm()

        when:
        Option<Film> maybeFilm = showFilm.byTitle(Title.from(filmTitle))

        then:
        maybeFilm.isDefined()
        maybeFilm.get().getTitle().value() == filmTitle
    }

    def "Saved film should be able to be found by list method"() {
        given:
        film = aSavedFilm()

        when:
        Page<Film> page = showFilm.list(new PageRequest(0, 10))

        then:
        page.getNumberOfElements() == 1
        page.getContent().first().getTitle().value() == filmTitle
    }

    private Film aSavedFilm() {
        saveFilm.save(
                filmFactory.createFilmWithTitle(filmTitle)
        )
    }
}