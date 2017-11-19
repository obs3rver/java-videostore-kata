package pl.artcoder.playground.videostore.user.application;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.artcoder.playground.videostore.infrastructure.authentication.CurrentUserGetter;
import pl.artcoder.playground.videostore.user.domain.LoggedInUser;

@Service
@RequiredArgsConstructor
public class CurrentUserQuery {
    private final CurrentUserGetter currentUserGetter;
    private final FindUserQuery findUserQuery;

    public Option<LoggedInUser> loggedInUser() {
        return findUserQuery
                .findBy(
                        currentUserGetter.getSignedInUserNameOrAnonymous()
                )
                .map(LoggedInUser::fromUser);
    }

}
