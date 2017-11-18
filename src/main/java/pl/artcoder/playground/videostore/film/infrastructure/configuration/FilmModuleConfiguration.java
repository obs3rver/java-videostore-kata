package pl.artcoder.playground.videostore.film.infrastructure.configuration;

import pl.artcoder.playground.videostore.film.application.SaveFilmCommand;
import pl.artcoder.playground.videostore.film.application.ShowFilmQuery;
import pl.artcoder.playground.videostore.film.domain.Film;
import pl.artcoder.playground.videostore.film.domain.FilmFactory;
import pl.artcoder.playground.videostore.film.domain.FilmIdSequenceGenerator;
import pl.artcoder.playground.videostore.film.domain.FilmRepository;
import pl.artcoder.playground.videostore.film.infrastructure.gateway.inmemory.InMemoryFilmIdSequenceGenerator;
import pl.artcoder.playground.videostore.film.infrastructure.gateway.inmemory.InMemoryFilmRepository;

public class FilmModuleConfiguration {
    private final InMemoryFilmRepository filmRepository = new InMemoryFilmRepository(Film::getId);

    public ShowFilmQuery showFilm() {
        return new ShowFilmQuery(filmRepository());
    }

    public SaveFilmCommand saveFilm() {
        return new SaveFilmCommand(filmRepository());
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
