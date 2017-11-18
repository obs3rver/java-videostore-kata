package pl.artcoder.playground.videostore.film.infrastructure.boundary.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.artcoder.playground.videostore.film.application.ShowFilmQuery;
import pl.artcoder.playground.videostore.film.domain.Title;
import pl.artcoder.playground.videostore.film.infrastructure.boundary.rest.viewmodel.FilmJson;
import pl.artcoder.playground.videostore.film.infrastructure.boundary.rest.viewmodel.FilmNotFoundException;

@RestController
@RequiredArgsConstructor
class FilmController {
    private final ShowFilmQuery showFilmQuery;

    @GetMapping("films")
    Page<FilmJson> getFilms(Pageable pageable) {
        return showFilmQuery
                .list(pageable)
                .map(FilmJson::fromDomain);
    }

    @GetMapping("film/{title}")
    FilmJson getFilm(@PathVariable String title) {
        return showFilmQuery
                .byTitle(Title.from(title))
                .map(FilmJson::fromDomain)
                .getOrElseThrow(() -> new FilmNotFoundException(title));
    }
}
