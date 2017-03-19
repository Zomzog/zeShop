package bzh.zomzog.zeshop.security.social;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import bzh.zomzog.zeshop.security.jwt.TokenProvider;
import io.github.jhipster.config.JHipsterProperties;

public class CustomSignInAdapter implements SignInAdapter {

    private final Logger log = LoggerFactory.getLogger(CustomSignInAdapter.class);

    private final UserDetailsService userDetailsService;

    private final JHipsterProperties jHipsterProperties;

    private final TokenProvider tokenProvider;

    public CustomSignInAdapter(final UserDetailsService userDetailsService, final JHipsterProperties jHipsterProperties,
            final TokenProvider tokenProvider) {
        this.userDetailsService = userDetailsService;
        this.jHipsterProperties = jHipsterProperties;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public String signIn(final String userId, final Connection<?> connection, final NativeWebRequest request) {
        try {
            final UserDetails user = userDetailsService.loadUserByUsername(userId);
            final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            final String jwt = tokenProvider.createToken(authenticationToken, false);
            final ServletWebRequest servletWebRequest = (ServletWebRequest) request;
            servletWebRequest.getResponse().addCookie(getSocialAuthenticationCookie(jwt));
        } catch (final AuthenticationException ae) {
            log.error("Social authentication error");
            log.trace("Authentication exception trace: {}", ae);
        }
        return jHipsterProperties.getSocial().getRedirectAfterSignIn();
    }

    private Cookie getSocialAuthenticationCookie(final String token) {
        final Cookie socialAuthCookie = new Cookie("social-authentication", token);
        socialAuthCookie.setPath("/");
        socialAuthCookie.setMaxAge(10);
        return socialAuthCookie;
    }
}
