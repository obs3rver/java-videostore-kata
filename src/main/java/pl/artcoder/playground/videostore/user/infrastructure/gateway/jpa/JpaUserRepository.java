package pl.artcoder.playground.videostore.user.infrastructure.gateway.jpa;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.artcoder.playground.videostore.user.domain.User;
import pl.artcoder.playground.videostore.user.domain.UserRepository;
import pl.artcoder.playground.videostore.user.domain.Username;

@Component
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {
    private final CrudUserRepository crudUserRepository;

    @Override
    public Option<User> findOneByUsername(Username username) {
        return crudUserRepository.findOneByUsername(username);
    }

    @Override
    public User save(User user) {
        return crudUserRepository.save(user);
    }

    @Override
    public void delete(User user) {
        crudUserRepository.delete(user);
    }
}
