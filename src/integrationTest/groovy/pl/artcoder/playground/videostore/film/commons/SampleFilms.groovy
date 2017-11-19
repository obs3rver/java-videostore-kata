package pl.artcoder.playground.videostore.film.commons

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import pl.artcoder.playground.videostore.film.domain.Film
import pl.artcoder.playground.videostore.film.domain.FilmFactory

import static pl.artcoder.playground.videostore.film.domain.Film.Type.*

@CompileStatic
trait SampleFilms {
    @Autowired
    FilmFactory filmFactory

    String oldFilmTitle = "Metropolis"
    String regularFilmTitle = "Inception"
    String newFilmTitle = "Blade Runner 2049"

    Film createOldFilm() {
        filmFactory.createFilmWithTitleAndType(oldFilmTitle, OLD)
    }

    Film createRegularFilm() {
        filmFactory.createFilmWithTitleAndType(regularFilmTitle, REGULAR)
    }

    Film createNewFilm() {
        filmFactory.createFilmWithTitleAndType(newFilmTitle, NEW)
    }
}