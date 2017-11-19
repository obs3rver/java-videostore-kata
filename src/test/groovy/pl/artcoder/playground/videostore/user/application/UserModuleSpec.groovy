package pl.artcoder.playground.videostore.user.application

import io.vavr.control.Option
import pl.artcoder.playground.videostore.user.domain.*
import pl.artcoder.playground.videostore.user.infrastructure.configuration.UserModuleConfiguration
import spock.lang.Specification
import spock.lang.Subject

class UserModuleSpec extends Specification {
    UserModuleConfiguration config = new UserModuleConfiguration()

    UserRepository userRepository

    AddUserCommand addUserCommand

    @Subject
    FindUserQuery findUserQuery

    User user

    def setup() {
        userRepository = config.userRepository()
        addUserCommand = config.addUserCommand()
        findUserQuery = config.findUserQuery()
    }

    def cleanup() {
        userRepository.delete(user)
        user = null
    }

    def "Should find user by username"() {
        given:
        user = aSavedUser()

        when:
        Option<User> maybeUser = findUserQuery.findBy(user.getUsername())

        then:
        maybeUser.isDefined()
        maybeUser.get().getUsername() == user.getUsername()
    }

    private def aSavedUser() {
        addUserCommand.execute(
                User.builder()
                        .id(0)
                        .username(Username.from("user"))
                        .password(Password.from("secret"))
                        .role(User.Role.ROLE_USER)
                        .bonusPoints(BonusPoints.zero())
                        .build()
        )
    }
}
