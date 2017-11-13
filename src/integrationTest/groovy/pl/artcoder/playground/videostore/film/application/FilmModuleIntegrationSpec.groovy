package pl.artcoder.playground.videostore.film.application

import io.vavr.control.Option
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import pl.artcoder.playground.videostore.base.IntegrationSpec
import pl.artcoder.playground.videostore.film.commons.SampleFilms
import pl.artcoder.playground.videostore.film.domain.Film
import pl.artcoder.playground.videostore.film.domain.FilmRepository
import pl.artcoder.playground.videostore.film.domain.Title

class FilmModuleIntegrationSpec extends IntegrationSpec implements SampleFilms {

    @Autowired
    SaveFilm saveFilm

    @Autowired
    ShowFilm showFilm

    @Autowired
    FilmRepository filmRepository

    Film film

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
                createFilm()
        )
    }
}
