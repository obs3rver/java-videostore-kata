package pl.artcoder.playground.videostore.film.domain;

import io.vavr.control.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilmRepository {
    Page<Film> findAll(Pageable pageable);

    Film save(Film film);

    void delete(Film film);

    Option<Film> findById(FilmId filmId);

    Option<Film> findByTitle(Title title);
}
