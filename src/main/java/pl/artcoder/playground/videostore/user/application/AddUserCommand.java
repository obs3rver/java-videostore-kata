package pl.artcoder.playground.videostore.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.artcoder.playground.videostore.user.domain.Password;
import pl.artcoder.playground.videostore.user.domain.User;
import pl.artcoder.playground.videostore.user.domain.UserRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddUserCommand {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User execute(User user) {
        final User encodedUser = user.withPassword(
                Password.from(
                        passwordEncoder.encode(
                                user.getPassword().value()
                        )
                )
        );

        return userRepository.save(encodedUser);
    }
}
