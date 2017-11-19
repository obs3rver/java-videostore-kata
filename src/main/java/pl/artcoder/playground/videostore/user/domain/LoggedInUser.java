package pl.artcoder.playground.videostore.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Accessors(fluent = true)
public class LoggedInUser {
    Username username;
    BonusPoints bonusPoints;

    public static LoggedInUser fromUser(User user) {
        return new LoggedInUser(
                user.getUsername(),
                user.getBonusPoints()
        );
    }
}
