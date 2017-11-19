package pl.artcoder.playground.videostore.user.domain;

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
public class Password {
    private String encodedPassword;

    public String value() {
        return encodedPassword;
    }

    public static Password from(String encodedPassword) {
        return new Password(encodedPassword);
    }

    public static Password empty() {
        return new Password("");
    }

}
