package pl.artcoder.playground.videostore.infrastructure.authentication

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import pl.artcoder.playground.videostore.base.IntegrationSpec
import pl.artcoder.playground.videostore.user.domain.User
import pl.artcoder.playground.videostore.user.domain.Username
import spock.lang.Subject

import static pl.artcoder.playground.videostore.user.domain.User.Role.ROLE_ANONYMOUS

class CurrentUserGetterIntegrationSpec extends IntegrationSpec {
    @Subject
    @Autowired
    CurrentUserGetter currentUserGetter

    @WithMockUser(username = "user", roles = ["USER"])
    def "should fetch username of non-anonymous user"() {
        when:
        Username currentUsername = currentUserGetter.getSignedInUserNameOrAnonymous()

        then:
        currentUsername.value() == "user"
    }

    @WithMockUser(username = "user", roles = ["USER"])
    def "should fetch role of non-anonymous user"() {
        when:
        User.Role role = currentUserGetter.getSignedInUserRoleOrAnonymous()

        then:
        role == User.Role.ROLE_USER
    }

    @WithMockUser(username = "anonymous", password = "", roles = [])
    def "should fetch username of anonymous user"() {
        when:
        Username currentUsername = currentUserGetter.getSignedInUserNameOrAnonymous()

        then:
        currentUsername == Username.anonymous()
    }

    @WithMockUser(username = "anonymous", password = "", roles = [])
    def "should fetch role of anonymous user"() {
        when:
        User.Role role = currentUserGetter.getSignedInUserRoleOrAnonymous()

        then:
        role == ROLE_ANONYMOUS
    }
}
