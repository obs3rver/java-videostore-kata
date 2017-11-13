package pl.artcoder.playground.videostore.film.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilmFactory {
    private final FilmIdSequenceGenerator filmIdSequenceGenerator;

    public Film createFilmWithTitle(@NonNull String filmTitle) {
        return Film.builder()
                .id(filmIdSequenceGenerator.nextId())
                .title(Title.from(filmTitle))
                .build();
    }
}
