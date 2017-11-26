package pl.artcoder.playground.videostore.rent.infrastructure.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.artcoder.playground.videostore.common.domain.Money;
import pl.artcoder.playground.videostore.rent.domain.price.DailyRentPriceProvider;

import java.math.BigDecimal;

@Component
public class PropertyBasedDailyRentPriceProvider implements DailyRentPriceProvider {
    private final Money basicPrice;
    private final Money premiumPrice;

    public PropertyBasedDailyRentPriceProvider(
            @Value("${renting.price.BASIC}") BigDecimal basicPrice,
            @Value("${renting.price.PREMIUM}") BigDecimal premiumPrice
    ) {
        this.basicPrice = Money.of(basicPrice);
        this.premiumPrice = Money.of(premiumPrice);
    }

    @Override
    public Money basic() {
        return basicPrice;
    }

    @Override
    public Money premium() {
        return premiumPrice;
    }
}
