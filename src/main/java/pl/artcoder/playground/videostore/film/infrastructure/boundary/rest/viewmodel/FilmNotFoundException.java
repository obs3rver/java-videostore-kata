package pl.artcoder.playground.videostore.film.infrastructure.boundary.rest.viewmodel;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(String title) {
        super("No film of title " + title + " found", null, false, false);
    }
}
