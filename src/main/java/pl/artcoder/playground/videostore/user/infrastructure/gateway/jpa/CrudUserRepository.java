package pl.artcoder.playground.videostore.user.infrastructure.gateway.jpa;

import io.vavr.control.Option;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.artcoder.playground.videostore.user.domain.User;
import pl.artcoder.playground.videostore.user.domain.Username;

@Repository
interface CrudUserRepository extends CrudRepository<User, Long> {
    Option<User> findOneByUsername(Username username);
}
