package pl.artcoder.playground.videostore.film.infrastructure.gateway.inmemory;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import pl.artcoder.playground.videostore.film.domain.FilmId;
import pl.artcoder.playground.videostore.film.domain.FilmIdSequenceGenerator;

@RequiredArgsConstructor
public class InMemoryFilmIdSequenceGenerator implements FilmIdSequenceGenerator {
    private final ExtenededFilmRepository filmRepository;

    @Override
    public FilmId nextId() {
        final Option<FilmId> maybeFilmId = filmRepository.findTopById();
        return maybeFilmId
                .map(FilmId::next)
                .getOrElse(() -> FilmId.from("1"));
    }
}
