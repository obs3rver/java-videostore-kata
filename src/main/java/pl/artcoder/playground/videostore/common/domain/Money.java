package pl.artcoder.playground.videostore.common.domain;

import lombok.Value;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
@Accessors(fluent = true)
public class Money {
    BigDecimal value;

    public Money multiplyBy(int multiplier) {
        return Money.of(
                value.multiply(BigDecimal.valueOf(multiplier))
        );
    }

    public Money add(Money other) {
        return Money.of(value.add(other.value));
    }

    public static Money of(double cost) {
        return Money.of(BigDecimal.valueOf(cost));
    }
}
