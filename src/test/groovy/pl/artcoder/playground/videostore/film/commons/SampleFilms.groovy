package pl.artcoder.playground.videostore.film.commons

import groovy.transform.CompileStatic
import pl.artcoder.playground.videostore.film.domain.Film
import pl.artcoder.playground.videostore.film.domain.FilmFactory

@CompileStatic
trait SampleFilms {
    String filmTitle = "Bolek i Lolek"

    Film createFilm(FilmFactory filmFactory) {
        filmFactory.createFilmWithTitle(filmTitle)
    }
}