package pl.artcoder.playground.videostore.infrastructure.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import pl.artcoder.playground.videostore.user.application.FindUserQuery;
import pl.artcoder.playground.videostore.user.domain.Username;

import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    private FindUserQuery findUserQuery;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    @Transactional(readOnly = true)
    UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username)
                    throws UsernameNotFoundException {
                return findUserQuery
                        .findBy(Username.from(username))
                        .map(toSpringUser())
                        .getOrElseThrow(
                                usernameNotFoundException(username)
                        );
            }

            private Supplier<UsernameNotFoundException> usernameNotFoundException(String user) {
                return () ->
                        new UsernameNotFoundException("could not find the user '" + user + "'");
            }

            private Function<pl.artcoder.playground.videostore.user.domain.User, User> toSpringUser() {
                return u -> new User(
                        u.getUsername().value(),
                        u.getPassword().value(),
                        true,
                        true,
                        true,
                        true,
                        AuthorityUtils.createAuthorityList("ROLE_USER")
                );
            }

        };
    }
}
