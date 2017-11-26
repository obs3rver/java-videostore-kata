package pl.artcoder.playground.videostore.rent.domain.price;

import pl.artcoder.playground.videostore.common.domain.Money;

public interface DailyRentPriceProvider {
    Money basic();
    Money premium();
}
