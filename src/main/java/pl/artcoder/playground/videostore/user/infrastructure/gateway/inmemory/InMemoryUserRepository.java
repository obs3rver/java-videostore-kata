package pl.artcoder.playground.videostore.user.infrastructure.gateway.inmemory;

import io.vavr.control.Option;
import pl.artcoder.playground.videostore.infrastructure.gateway.inmemory.BaseInMemoryRepository;
import pl.artcoder.playground.videostore.user.domain.User;
import pl.artcoder.playground.videostore.user.domain.UserRepository;
import pl.artcoder.playground.videostore.user.domain.Username;

import java.util.function.Function;

public class InMemoryUserRepository
        extends BaseInMemoryRepository<User, Long>
        implements UserRepository {

    public InMemoryUserRepository(Function<User, Long> userLongFunction) {
        super(userLongFunction);
    }

    @Override
    public Option<User> findOneByUsername(Username username) {
        return repo
                .values()
                .find(u -> username.equals(u.getUsername()));
    }
}
