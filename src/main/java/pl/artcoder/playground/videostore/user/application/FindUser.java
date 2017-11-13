package pl.artcoder.playground.videostore.user.application;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.artcoder.playground.videostore.user.domain.User;
import pl.artcoder.playground.videostore.user.domain.UserRepository;
import pl.artcoder.playground.videostore.user.domain.Username;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindUser {
    private final UserRepository userRepository;

    public Option<User> findByUsername(Username username) {
        return userRepository.findOneByUsername(username);
    }
}
