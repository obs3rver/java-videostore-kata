package pl.artcoder.playground.videostore.film.infrastructure.gateway.jpa;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.artcoder.playground.videostore.film.domain.FilmIdSequenceGenerator;
import pl.artcoder.playground.videostore.film.domain.FilmId;

@Component
@RequiredArgsConstructor
class JpaFilmIdSequenceGenerator implements FilmIdSequenceGenerator {

    private final PagingAndSortingFilmRepository crudFilmRepository;

    @Override
    public FilmId nextId() {
        final Option<FilmId> maybeFilmId = crudFilmRepository.findTopById();
        return maybeFilmId
                .map(FilmId::next)
                .getOrElse(() -> FilmId.from("1"));
    }
}
