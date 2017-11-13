package pl.artcoder.playground.videostore.film.infrastructure.gateway.jpa;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.artcoder.playground.videostore.film.domain.Film;
import pl.artcoder.playground.videostore.film.domain.FilmId;
import pl.artcoder.playground.videostore.film.domain.Title;

@Repository
interface PagingAndSortingFilmRepository extends PagingAndSortingRepository<Film, FilmId> {
    @Query("select max(f.id) from Film f")
    Option<FilmId> findTopById();

    Option<Film> findOneByTitle(Title title);
}
