package pl.artcoder.playground.videostore.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Data
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class BonusPoints {
    private int points;

    public void addPoints(int newPoints) {
        points += newPoints;
    }

    private int value() {
        return points;
    }

    public static BonusPoints zero() {
        return new BonusPoints(0);
    }
}
