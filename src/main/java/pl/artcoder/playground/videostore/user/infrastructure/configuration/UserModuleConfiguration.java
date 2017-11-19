package pl.artcoder.playground.videostore.user.infrastructure.configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.artcoder.playground.videostore.user.application.AddUserCommand;
import pl.artcoder.playground.videostore.user.application.FindUserQuery;
import pl.artcoder.playground.videostore.user.domain.User;
import pl.artcoder.playground.videostore.user.domain.UserRepository;
import pl.artcoder.playground.videostore.user.infrastructure.gateway.inmemory.InMemoryUserRepository;

public class UserModuleConfiguration {
    private final InMemoryUserRepository userRepository = new InMemoryUserRepository(User::getId);

    public FindUserQuery findUserQuery() {
        return new FindUserQuery(userRepository);
    }

    public AddUserCommand addUserCommand() {
        return new AddUserCommand(userRepository, passwordEncoder());
    }

    public UserRepository userRepository() {
        return userRepository;
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
