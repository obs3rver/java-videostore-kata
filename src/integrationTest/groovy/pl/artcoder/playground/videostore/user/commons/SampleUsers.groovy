package pl.artcoder.playground.videostore.user.commons

import groovy.transform.CompileStatic
import pl.artcoder.playground.videostore.user.domain.BonusPoints
import pl.artcoder.playground.videostore.user.domain.Password
import pl.artcoder.playground.videostore.user.domain.User
import pl.artcoder.playground.videostore.user.domain.Username

@CompileStatic
trait SampleUsers {

    User defaultUser = prepareDefaultUser("user")

    private static User prepareDefaultUser(String username) {
        User.builder()
                .username(Username.from(username))
                .password(Password.empty())
                .role(User.Role.ROLE_USER)
                .bonusPoints(BonusPoints.zero())
                .build()
    }
}