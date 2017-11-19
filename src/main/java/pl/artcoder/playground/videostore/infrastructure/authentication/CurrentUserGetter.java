package pl.artcoder.playground.videostore.infrastructure.authentication;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.artcoder.playground.videostore.user.domain.User;
import pl.artcoder.playground.videostore.user.domain.Username;

import static pl.artcoder.playground.videostore.user.domain.User.Role.ROLE_ANONYMOUS;

@Component
public class CurrentUserGetter {

    private Option<Authentication> getSignedInAuthentication() {
        final SecurityContext context = SecurityContextHolder.getContext();
        return Option.of(context.getAuthentication());
    }

    public Username getSignedInUserNameOrAnonymous() {
        return getSignedInAuthentication()
                .map(Authentication::getName)
                .map(Username::from)
                .getOrElse(Username::anonymous);
    }

    private List<String> getSignedInUserAuthorities() {
        return getSignedInAuthentication()
                .map(Authentication::getAuthorities)
                .toList()
                .flatMap(a -> a)
                .map(GrantedAuthority::getAuthority);
    }

    public User.Role getSignedInUserRoleOrAnonymous() {
        return getSignedInUserAuthorities()
                .headOption()
                .map(User.Role::valueOf)
                .getOrElse(ROLE_ANONYMOUS);
    }
}
