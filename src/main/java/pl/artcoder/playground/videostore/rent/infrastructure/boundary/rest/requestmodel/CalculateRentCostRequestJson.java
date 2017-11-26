package pl.artcoder.playground.videostore.rent.infrastructure.boundary.rest.requestmodel;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class CalculateRentCostRequestJson {
    List<String> titles;
    int rentalDays;
}
