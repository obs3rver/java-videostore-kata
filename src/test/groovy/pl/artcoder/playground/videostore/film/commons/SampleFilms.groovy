package pl.artcoder.playground.videostore.film.commons

import groovy.transform.CompileStatic
import pl.artcoder.playground.videostore.film.domain.Film
import pl.artcoder.playground.videostore.film.domain.FilmFactory

import static pl.artcoder.playground.videostore.film.domain.FilmType.*

@CompileStatic
trait SampleFilms {
    String oldFilmTitle = "Metropolis"
    String regularFilmTitle = "Inception"
    String newFilmTitle = "Blade Runner 2049"

    Film createOldFilm(FilmFactory filmFactory) {
        filmFactory.createFilmWithTitleAndType(oldFilmTitle, OLD)
    }

    Film createRegularFilm(FilmFactory filmFactory) {
        filmFactory.createFilmWithTitleAndType(regularFilmTitle, REGULAR)
    }

    Film createNewFilm(FilmFactory filmFactory) {
        filmFactory.createFilmWithTitleAndType(newFilmTitle, NEW)
    }
}