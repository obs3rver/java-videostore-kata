package pl.artcoder.playground.videostore.rent.infrastructure.boundary.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.artcoder.playground.videostore.film.domain.Title;
import pl.artcoder.playground.videostore.rent.application.CalculateRentCostQuery;
import pl.artcoder.playground.videostore.rent.infrastructure.boundary.rest.requestmodel.CalculateRentCostRequestJson;
import pl.artcoder.playground.videostore.rent.infrastructure.boundary.rest.viewmodel.RentCostJson;

import java.util.List;

@RestController
@RequiredArgsConstructor
class CalculateRentCostController {
    private final CalculateRentCostQuery calculateRentCostQuery;

    @PostMapping("/calculate")
    List<RentCostJson> calculate(@RequestBody CalculateRentCostRequestJson calcRequest) {
        return calculateRentCostQuery.execute(
                mapToDomain(calcRequest.getTitles()),
                calcRequest.getRentalDays()
        )
                .map(RentCostJson::fromDomain)
                .asJava();
    }

    private io.vavr.collection.List<Title> mapToDomain(List<String> titles) {
        return io.vavr.collection.List.ofAll(titles)
                .map(Title::from);
    }

}
