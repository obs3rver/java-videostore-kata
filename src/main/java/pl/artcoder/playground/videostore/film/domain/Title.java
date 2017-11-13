package pl.artcoder.playground.videostore.film.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Data
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class Title {
    private String title;

    public String value() {
        return title;
    }

    public static Title from(String title) {
        return new Title(title);
    }
}
