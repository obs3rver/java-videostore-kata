package pl.artcoder.playground.videostore.film.infrastructure.configuration;

import pl.artcoder.playground.videostore.film.application.SaveFilm;
import pl.artcoder.playground.videostore.film.application.ShowFilm;
import pl.artcoder.playground.videostore.film.domain.Film;
import pl.artcoder.playground.videostore.film.domain.FilmFactory;
import pl.artcoder.playground.videostore.film.domain.FilmIdSequenceGenerator;
import pl.artcoder.playground.videostore.film.domain.FilmRepository;
import pl.artcoder.playground.videostore.film.infrastructure.gateway.inmemory.InMemoryFilmIdSequenceGenerator;
import pl.artcoder.playground.videostore.film.infrastructure.gateway.inmemory.InMemoryFilmRepository;

public class FilmModuleConfiguration {
    private final InMemoryFilmRepository filmRepository = new InMemoryFilmRepository(Film::getId);

    public ShowFilm showFilm() {
        return new ShowFilm(filmRepository());
    }

    public SaveFilm saveFilm() {
        return new SaveFilm(filmRepository());
    }

    public FilmFactory filmFactory() {
        return new FilmFactory(filmIdSequenceGenerator());
    }

    public FilmRepository filmRepository() {
        return filmRepository;
    }

    FilmIdSequenceGenerator filmIdSequenceGenerator() {
        return new InMemoryFilmIdSequenceGenerator(filmRepository);
    }

}
