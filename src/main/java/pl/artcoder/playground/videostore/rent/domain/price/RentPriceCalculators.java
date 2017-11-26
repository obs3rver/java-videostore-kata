package pl.artcoder.playground.videostore.rent.domain.price;

import lombok.RequiredArgsConstructor;
import pl.artcoder.playground.videostore.common.domain.Money;

import static pl.artcoder.playground.videostore.rent.domain.price.RentPriceCalculators.FlatRateDays.OLD_FILM;
import static pl.artcoder.playground.videostore.rent.domain.price.RentPriceCalculators.FlatRateDays.REGULAR_FILM;

public class RentPriceCalculators {

    public static final RentPriceCalculator newFilmRentCalcStrategy = (days, priceProvider) ->
            priceProvider.premium().multiplyBy(days);

    public static final RentPriceCalculator regularFilmRentCalcStrategy = (days, priceProvider) ->
            calculateBasicRentCostIncludingFlateRate(days, REGULAR_FILM.flatRateEndDay(), priceProvider);

    public static final RentPriceCalculator oldFilmRentCalcStrategy = (days, priceProvider) ->
            calculateBasicRentCostIncludingFlateRate(days, OLD_FILM.flatRateEndDay(), priceProvider);

    private static Money calculateBasicRentCostIncludingFlateRate(
            int rentalDays,
            int flatRateEndDay,
            DailyRentPriceProvider dailyRentPriceProvider
    ) {
        return (rentalDays <= flatRateEndDay) ?
                dailyRentPriceProvider.basic() :
                dailyRentPriceProvider.basic()
                        .multiplyBy(rentalDays - flatRateEndDay)
                        .add(dailyRentPriceProvider.basic());
    }

    @RequiredArgsConstructor
    enum FlatRateDays {
        REGULAR_FILM(3),
        OLD_FILM(5);

        private final int flatRateEndDay;

        public int flatRateEndDay() {
            return flatRateEndDay;
        }
    }
}
