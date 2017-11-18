package pl.artcoder.playground.videostore.user.application

import io.vavr.control.Option
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import pl.artcoder.playground.videostore.user.domain.*
import pl.artcoder.playground.videostore.user.infrastructure.configuration.UserModuleConfiguration
import spock.lang.Specification
import spock.lang.Subject

class UserModuleSpec extends Specification {
    UserModuleConfiguration config = new UserModuleConfiguration()
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()

    UserRepository userRepository

    @Subject
    FindUserQuery findUser

    User user

    def setup() {
        userRepository = config.userRepository()
        findUser = config.findUser()
    }

    def cleanup() {
        userRepository.delete(user)
        user = null
    }

    def "Should find user by username"() {
        given:
        user = aSavedUser()

        when:
        Option<User> maybeUser = findUser.findBy(user.getUsername())

        then:
        maybeUser.isDefined()
        maybeUser.get().getUsername() == user.getUsername()
    }

    private def aSavedUser() {
        userRepository.save(
                User.builder()
                        .id(0)
                        .username(Username.from("user"))
                        .password(Password.from(passwordEncoder.encode("secret")))
                        .bonusPoints(BonusPoints.zero())
                        .build()
        )
    }
}
