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
public class Username {
    private String username;

    public String value() {
        return username;
    }

    public static Username from(String username) {
        return new Username(username);
    }

    public static Username anonymous() {
        return new Username("anonymous");
    }

}
