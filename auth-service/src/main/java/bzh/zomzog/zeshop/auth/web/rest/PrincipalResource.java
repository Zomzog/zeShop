package bzh.zomzog.zeshop.auth.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class PrincipalResource {

    @RequestMapping("/user")
    public Principal principal(final Principal p) {
        return p;
    }
}
