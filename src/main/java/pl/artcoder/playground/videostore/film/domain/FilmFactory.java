package pl.artcoder.playground.videostore.film.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilmFactory {
    private final FilmIdSequenceGenerator filmIdSequenceGenerator;

    public Film createFilmWithTitleAndType(@NonNull String filmTitle, @NonNull Film.Type filmType) {
        return Film.builder()
                .id(filmIdSequenceGenerator.nextId())
                .title(Title.from(filmTitle))
                .filmType(filmType)
                .build();
    }
}
