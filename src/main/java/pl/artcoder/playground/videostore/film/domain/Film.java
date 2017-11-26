package pl.artcoder.playground.videostore.film.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Wither;
import pl.artcoder.playground.videostore.common.domain.Money;
import pl.artcoder.playground.videostore.rent.domain.price.DailyRentPriceProvider;
import pl.artcoder.playground.videostore.rent.domain.price.RentPriceCalculator;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import static pl.artcoder.playground.videostore.rent.domain.price.RentPriceCalculators.newFilmRentCalcStrategy;
import static pl.artcoder.playground.videostore.rent.domain.price.RentPriceCalculators.oldFilmRentCalcStrategy;
import static pl.artcoder.playground.videostore.rent.domain.price.RentPriceCalculators.regularFilmRentCalcStrategy;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Wither
@Entity
public class Film {

    @EmbeddedId
    private FilmId id;

    @Embedded
    @NotNull
    private Title title;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Type filmType;

    public Money calculateRentCostFor(int daysOrRental, DailyRentPriceProvider dailyRentPriceProvider) {
        return filmType.calculateRentCostFor(daysOrRental, dailyRentPriceProvider);
    }

    @RequiredArgsConstructor
    public enum Type {
        NEW(newFilmRentCalcStrategy),
        REGULAR(regularFilmRentCalcStrategy),
        OLD(oldFilmRentCalcStrategy);

        private final RentPriceCalculator rentPriceCalculator;

        public Money calculateRentCostFor(int daysOrRental, DailyRentPriceProvider dailyRentPriceProvider) {
            return rentPriceCalculator.apply(daysOrRental, dailyRentPriceProvider);
        }
    }
}
