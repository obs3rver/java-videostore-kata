package pl.artcoder.playground.videostore.user.domain;

import io.vavr.control.Option;

public interface UserRepository {
    Option<User> findOneByUsername(Username username);

    User save(User user);

    void delete(User user);
}
