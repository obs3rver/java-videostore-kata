package pl.artcoder.playground.videostore.film.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.artcoder.playground.videostore.film.domain.Film;
import pl.artcoder.playground.videostore.film.domain.FilmRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SaveFilmCommand {
    private final FilmRepository filmRepository;

    public Film execute(Film film) {
        return filmRepository.save(film);
    }
}
