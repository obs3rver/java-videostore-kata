package pl.artcoder.playground.videostore.film.infrastructure.boundary.rest.viewmodel;

import lombok.Value;
import pl.artcoder.playground.videostore.film.domain.Film;

@Value(staticConstructor = "of")
public class FilmJson {
    String title;
    TypeJson type;

    public static FilmJson fromDomain(Film film) {
        return FilmJson.of(
                film.getTitle().value(),
                TypeJson.valueOf(film.getFilmType().name())
        );
    }

    public enum TypeJson {
        NEW,
        REGULAR,
        OLD
    }
}
