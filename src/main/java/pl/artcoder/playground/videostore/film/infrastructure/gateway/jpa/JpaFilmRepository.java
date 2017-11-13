package pl.artcoder.playground.videostore.film.infrastructure.gateway.jpa;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import pl.artcoder.playground.videostore.film.domain.Film;
import pl.artcoder.playground.videostore.film.domain.FilmId;
import pl.artcoder.playground.videostore.film.domain.FilmRepository;
import pl.artcoder.playground.videostore.film.domain.Title;

@Component
@RequiredArgsConstructor
class JpaFilmRepository implements FilmRepository {

    private final PagingAndSortingFilmRepository filmRepository;

    @Override
    public Page<Film> findAll(Pageable pageable) {
        return filmRepository.findAll(pageable);
    }

    @Override
    public Film save(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public void delete(Film film) {
        filmRepository.delete(film);
    }

    @Override
    public Option<Film> findById(FilmId filmId) {
        return Option.of(
                filmRepository.findOne(filmId)
        );
    }

    @Override
    public Option<Film> findByTitle(Title title) {
        return filmRepository.findOneByTitle(title);
    }
}
