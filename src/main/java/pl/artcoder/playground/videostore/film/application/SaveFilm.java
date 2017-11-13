package pl.artcoder.playground.videostore.film.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.artcoder.playground.videostore.film.domain.Film;
import pl.artcoder.playground.videostore.film.domain.FilmRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SaveFilm {
    private final FilmRepository filmRepository;

    public Film save(Film film) {
        return filmRepository.save(film);
    }
}
