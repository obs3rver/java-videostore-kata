package pl.artcoder.playground.videostore.film.infrastructure.gateway.inmemory;

import io.vavr.Tuple2;
import io.vavr.control.Option;
import pl.artcoder.playground.videostore.film.domain.Film;
import pl.artcoder.playground.videostore.film.domain.FilmId;
import pl.artcoder.playground.videostore.film.domain.FilmRepository;
import pl.artcoder.playground.videostore.film.domain.Title;
import pl.artcoder.playground.videostore.infrastructure.gateway.inmemory.BaseInMemoryRepository;

import java.util.function.Function;

public class InMemoryFilmRepository
        extends BaseInMemoryRepository<Film, FilmId>
        implements FilmRepository, ExtenededFilmRepository {

    public InMemoryFilmRepository(Function<Film, FilmId> idFunction) {
        super(idFunction);
    }

    @Override
    public Option<Film> findByTitle(Title title) {
        return repo
                .values()
                .find(f -> title.equals(f.getTitle()));
    }

    @Override
    public Option<FilmId> findTopById() {
        return repo
                .lastOption()
                .map(Tuple2::_1);
    }

}
