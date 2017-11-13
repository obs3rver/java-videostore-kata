package pl.artcoder.playground.videostore.film.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
@Data
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilmId implements Serializable {

    private Long id;

    public Long value() {
        return id;
    }

    public static FilmId from(String filmId) {
        return new FilmId(Long.valueOf(filmId));
    }

    public FilmId next() {
        return new FilmId(++id);
    }
}
