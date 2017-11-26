package pl.artcoder.playground.videostore.rent.infrastructure.boundary.rest.viewmodel;

import lombok.Value;
import pl.artcoder.playground.videostore.common.domain.Money;
import pl.artcoder.playground.videostore.film.domain.Title;
import pl.artcoder.playground.videostore.rent.application.CalculateRentCostQuery;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
public class RentCostJson {
    String title;
    BigDecimal cost;

    public static RentCostJson from(Title title, Money cost) {
        return RentCostJson.of(title.value(), cost.value());
    }

    public static RentCostJson fromDomain(CalculateRentCostQuery.RentCost domain) {
        return RentCostJson.from(domain.getTitle(), domain.getCost());
    }
}
