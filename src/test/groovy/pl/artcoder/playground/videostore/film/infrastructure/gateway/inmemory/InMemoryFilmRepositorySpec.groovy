package pl.artcoder.playground.videostore.film.infrastructure.gateway.inmemory

import io.vavr.control.Option
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import pl.artcoder.playground.videostore.film.commons.SampleFilms
import pl.artcoder.playground.videostore.film.domain.Film
import pl.artcoder.playground.videostore.film.domain.FilmFactory
import pl.artcoder.playground.videostore.film.domain.FilmRepository
import pl.artcoder.playground.videostore.film.infrastructure.configuration.FilmModuleConfiguration
import spock.lang.Specification
import spock.lang.Subject

class InMemoryFilmRepositorySpec extends Specification implements SampleFilms {
    FilmModuleConfiguration configuration = new FilmModuleConfiguration()
    @Subject
    FilmRepository filmRepository
    FilmFactory filmFactory

    Film film

    def setup() {
        filmRepository = configuration.filmRepository()
        filmFactory = configuration.filmFactory()
    }

    def cleanup() {
        filmRepository.delete(film)
    }

    def "Saved film should be able to be found by id"() {
        given:
        film = createFilm(filmFactory)

        when:
        film = filmRepository.save(film)
        Option<Film> maybeFilm = filmRepository.findById(film.getId())

        then:
        maybeFilm.isDefined()
        maybeFilm.get().title.title == filmTitle
    }

    def "Saved film should be able to be found by title"() {
        given:
        film = createFilm(filmFactory)

        when:
        film = filmRepository.save(film)
        Option<Film> maybeFilm = filmRepository.findByTitle(film.getTitle())

        then:
        maybeFilm.isDefined()
        maybeFilm.get().title.title == filmTitle
    }

    def "Saved film should be able to be found by findAll method"() {
        given:
        film = createFilm(filmFactory)

        when:
        film = filmRepository.save(film)
        Page<Film> page = filmRepository.findAll(new PageRequest(0, 10))

        then:
        page.getNumberOfElements() == 1
        page.first().title.title == filmTitle
    }
}
