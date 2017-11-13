package pl.artcoder.playground.videostore.infrastructure.gateway.inmemory;

import io.vavr.collection.LinkedHashMap;
import io.vavr.control.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

public abstract class BaseInMemoryRepository<E, Id extends Serializable> implements InMemoryRepository<E, Id> {
    protected LinkedHashMap<Id, E> repo = LinkedHashMap.empty();
    protected final Function<E, Id> idFunction;

    public BaseInMemoryRepository(final Function<E, Id> idFunction) {
        this.idFunction = idFunction;
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        return new PageImpl(
                repo
                        .takeRight(pageable.getPageSize())
                        .values()
                        .asJava()
        );
    }

    @Override
    public E save(E entity) {
        Objects.requireNonNull(idFunction.apply(entity));
        repo = repo.put(idFunction.apply(entity), entity);
        return entity;
    }

    @Override
    public void delete(E entity) {
        repo = repo.remove(idFunction.apply(entity));
    }

    @Override
    public Option<E> findById(Id id) {
        return repo.get(id);
    }

}
