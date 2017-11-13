package pl.artcoder.playground.videostore.film.infrastructure.gateway.jpa

import io.vavr.control.Option
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import pl.artcoder.playground.videostore.base.IntegrationSpec
import pl.artcoder.playground.videostore.film.commons.SampleFilms
import pl.artcoder.playground.videostore.film.domain.Film
import pl.artcoder.playground.videostore.film.domain.FilmRepository

class JpaFilmRepositoryIntegrationSpec extends IntegrationSpec implements SampleFilms {
    @Autowired
    FilmRepository filmRepository

    Film film

    def cleanup() {
        filmRepository.delete(film)
    }

    def "Saved film should be able to be found by id"() {
        given:
        film = createOldFilm()

        when:
        film = filmRepository.save(film)
        Option<Film> maybeFilm = filmRepository.findById(film.getId())

        then:
        maybeFilm.isDefined()
        maybeFilm.get().title.value() == oldFilmTitle
    }

    def "Saved film should be able to be found by title"() {
        given:
        film = createOldFilm()

        when:
        film = filmRepository.save(film)
        Option<Film> maybeFilm = filmRepository.findByTitle(film.getTitle())

        then:
        maybeFilm.isDefined()
        maybeFilm.get().title.value() == oldFilmTitle
    }

    def "Saved film should be able to be found by findAll method"() {
        given:
        film = createOldFilm()

        when:
        film = filmRepository.save(film)
        Page<Film> page = filmRepository.findAll(new PageRequest(0, 10))

        then:
        page.getNumberOfElements() == 1
        page.first().title.value() == oldFilmTitle
    }
}
