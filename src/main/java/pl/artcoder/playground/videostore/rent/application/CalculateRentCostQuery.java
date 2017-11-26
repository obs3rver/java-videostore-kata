package pl.artcoder.playground.videostore.rent.application;

import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import pl.artcoder.playground.videostore.common.domain.Money;
import pl.artcoder.playground.videostore.film.application.ShowFilmQuery;
import pl.artcoder.playground.videostore.film.domain.Film;
import pl.artcoder.playground.videostore.film.domain.Title;
import pl.artcoder.playground.videostore.rent.domain.price.DailyRentPriceProvider;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CalculateRentCostQuery {
    private final ShowFilmQuery showFilmQuery;
    private final DailyRentPriceProvider dailyRentPriceProvider;

    public Option<RentCost> execute(Title filmTitle, int daysOrRental) {
        return showFilmQuery
                .byTitle(filmTitle)
                .map(toRentCostFor(filmTitle, daysOrRental));
    }

    public List<RentCost> execute(List<Title> filmTitles, int daysOrRental) {
        return filmTitles
                .map(title -> execute(title, daysOrRental))
                .flatMap(it -> it);
    }

    private Function<Film, RentCost> toRentCostFor(Title title, int daysOrRental) {
        return film -> RentCost.of(title, film.calculateRentCostFor(daysOrRental, dailyRentPriceProvider));
    }

    @Value(staticConstructor = "of")
    public static class RentCost {
        Title title;
        Money cost;
    }
}
