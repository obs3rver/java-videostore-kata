package pl.artcoder.playground.videostore.user.infrastructure.configuration;

import pl.artcoder.playground.videostore.user.application.FindUser;
import pl.artcoder.playground.videostore.user.domain.User;
import pl.artcoder.playground.videostore.user.domain.UserRepository;
import pl.artcoder.playground.videostore.user.infrastructure.gateway.inmemory.InMemoryUserRepository;

public class UserModuleConfiguration {
    private final InMemoryUserRepository userRepository = new InMemoryUserRepository(User::getId);

    public FindUser findUser() {
        return new FindUser(userRepository);
    }

    public UserRepository userRepository() {
        return userRepository;
    }
}
