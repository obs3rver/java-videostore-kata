package pl.artcoder.playground.videostore.film.infrastructure.gateway.inmemory;

import io.vavr.control.Option;
import pl.artcoder.playground.videostore.film.domain.FilmId;

interface ExtenededFilmRepository {
    Option<FilmId> findTopById();
}
