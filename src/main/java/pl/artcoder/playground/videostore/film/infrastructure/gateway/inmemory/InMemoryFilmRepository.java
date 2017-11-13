package pl.artcoder.playground.videostore.film.infrastructure.gateway.inmemory;

import io.vavr.Tuple2;
import io.vavr.collection.LinkedHashMap;
import io.vavr.control.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.artcoder.playground.videostore.film.domain.Film;
import pl.artcoder.playground.videostore.film.domain.FilmId;
import pl.artcoder.playground.videostore.film.domain.FilmRepository;
import pl.artcoder.playground.videostore.film.domain.Title;

import java.util.Objects;

public class InMemoryFilmRepository implements FilmRepository, ExtenededFilmRepository {
    private LinkedHashMap<FilmId, Film> films = LinkedHashMap.empty();

    @Override
    public Page<Film> findAll(Pageable pageable) {
        return new PageImpl(
                films
                        .takeRight(pageable.getPageSize())
                        .values()
                        .asJava()
        );
    }

    @Override
    public Film save(Film film) {
        Objects.requireNonNull(film.getId());
        films = films.put(film.getId(), film);
        return film;
    }

    @Override
    public void delete(Film film) {
        films = films.remove(film.getId());
    }

    @Override
    public Option<Film> findById(FilmId filmId) {
        return films.get(filmId);
    }

    @Override
    public Option<Film> findByTitle(Title title) {
        return films
                .values()
                .find(f -> title.equals(f.getTitle()));
    }

    @Override
    public Option<FilmId> findTopById() {
        return films
                .lastOption()
                .map(Tuple2::_1);
    }

}
