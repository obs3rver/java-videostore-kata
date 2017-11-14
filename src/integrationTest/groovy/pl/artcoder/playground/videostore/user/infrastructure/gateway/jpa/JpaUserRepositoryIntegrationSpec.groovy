package pl.artcoder.playground.videostore.user.infrastructure.gateway.jpa

import io.vavr.control.Option
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import pl.artcoder.playground.videostore.base.IntegrationSpec
import pl.artcoder.playground.videostore.user.domain.*

class JpaUserRepositoryIntegrationSpec extends IntegrationSpec {
    @Autowired
    UserRepository userRepository

    @Autowired
    PasswordEncoder passwordEncoder

    User user

    def cleanup() {
        userRepository.delete(user)
        user = null
    }

    def "Saved user should be able to be found by username"() {
        given:
        user = aSavedUser()

        when:
        Option<User> maybeUser = userRepository.findOneByUsername(user.getUsername())

        then:
        maybeUser.isDefined()
        maybeUser.get().getUsername() == user.getUsername()
    }

    private def aSavedUser() {
        userRepository.save(
                User.builder()
                        .username(Username.from("user"))
                        .password(Password.from(passwordEncoder.encode("secret")))
                        .bonusPoints(BonusPoints.zero())
                        .build()
        )
    }
}
