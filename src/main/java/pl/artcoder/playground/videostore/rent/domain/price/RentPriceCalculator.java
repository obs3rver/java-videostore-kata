package pl.artcoder.playground.videostore.rent.domain.price;

import io.vavr.Function2;
import pl.artcoder.playground.videostore.common.domain.Money;

public interface RentPriceCalculator extends Function2<Integer, DailyRentPriceProvider, Money> {
}
