package pl.artcoder.playground.videostore.rent.infrastructure.configuration;

import pl.artcoder.playground.videostore.rent.domain.price.DailyRentPriceProvider;
import pl.artcoder.playground.videostore.rent.infrastructure.gateway.PropertyBasedDailyRentPriceProvider;

import java.math.BigDecimal;

public class RentModuleConfiguration {

    private final DailyRentPriceProvider dailyRentPriceProvider =
            new PropertyBasedDailyRentPriceProvider(
                    BigDecimal.valueOf(30.0),
                    BigDecimal.valueOf(40.0)
            );

    public DailyRentPriceProvider dailyRentPriceProvider() {
        return dailyRentPriceProvider;
    }
}
