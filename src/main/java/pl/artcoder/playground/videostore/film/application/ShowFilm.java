package pl.artcoder.playground.videostore.film.application;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.artcoder.playground.videostore.film.domain.Film;
import pl.artcoder.playground.videostore.film.domain.FilmId;
import pl.artcoder.playground.videostore.film.domain.FilmRepository;
import pl.artcoder.playground.videostore.film.domain.Title;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShowFilm {
    private final FilmRepository filmRepository;

    public Page<Film> list(Pageable pageable) {
        return filmRepository.findAll(pageable);
    }

    public Option<Film> byId(FilmId filmId) {
        return filmRepository.findById(filmId);
    }

    public Option<Film> byTitle(Title title) {
        return filmRepository.findByTitle(title);
    }
}
