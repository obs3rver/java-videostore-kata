package pl.artcoder.playground.videostore.film.infrastructure.boundary.rest.viewmodel;

import lombok.Value;
import pl.artcoder.playground.videostore.film.domain.Film;

@Value(staticConstructor = "of")
public class FilmJson {
    String title;

    public static FilmJson fromDomain(Film film) {
        return FilmJson.of(film.getTitle().value());
    }
}
