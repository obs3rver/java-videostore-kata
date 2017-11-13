package pl.artcoder.playground.videostore.film.commons

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import pl.artcoder.playground.videostore.film.domain.Film
import pl.artcoder.playground.videostore.film.domain.FilmFactory

@CompileStatic
trait SampleFilms {
    @Autowired
    FilmFactory filmFactory

    String filmTitle = "Bolek i Lolek"

    Film createFilm() {
        filmFactory.createFilmWithTitle(filmTitle)
    }
}