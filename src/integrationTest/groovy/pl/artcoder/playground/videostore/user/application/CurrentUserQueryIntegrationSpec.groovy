package pl.artcoder.playground.videostore.user.application

import io.vavr.control.Option
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import pl.artcoder.playground.videostore.base.IntegrationSpec
import pl.artcoder.playground.videostore.user.domain.*
import spock.lang.Subject

import static pl.artcoder.playground.videostore.user.domain.User.Role.ROLE_USER

class CurrentUserQueryIntegrationSpec extends IntegrationSpec {
    @Subject
    @Autowired
    CurrentUserQuery currentUserQuery

    @Autowired
    AddUserCommand addUserCommand

    @WithMockUser(username = "user", password = "passwd", roles = ["USER"])
    def "should fetch non-anonymous, pre-saved user info"() {
        given:
        User existingUser = addUserCommand.execute(aUser())

        when:
        Option<LoggedInUser> maybeCurrentUser = currentUserQuery.loggedInUser()

        then:
        maybeCurrentUser.isDefined()
        maybeCurrentUser.get().username == existingUser.username
    }

    @WithMockUser(username = "anonymous", roles = [])
    def "should not fetch anonymous, un-saved user info"() {
        when:
        Option<LoggedInUser> maybeCurrentUser = currentUserQuery.loggedInUser()

        then:
        !maybeCurrentUser.isDefined()
    }

    private def aUser() {
        User.builder()
                .username(Username.from("user"))
                .password(Password.from("passwd"))
                .role(ROLE_USER)
                .bonusPoints(BonusPoints.zero())
                .build()
    }
}
