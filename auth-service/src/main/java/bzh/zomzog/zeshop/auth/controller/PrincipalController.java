package bzh.zomzog.zeshop.auth.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrincipalController {

    @RequestMapping("/user")
    public Principal principal(final Principal p) {
        return p;
    }
}
