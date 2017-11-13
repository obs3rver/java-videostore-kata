package pl.artcoder.playground.videostore.infrastructure.gateway.inmemory;

import io.vavr.control.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InMemoryRepository<E, Id> {
    Page<E> findAll(Pageable pageable);

    E save(E entity);

    void delete(E entity);

    Option<E> findById(Id filmId);

}
