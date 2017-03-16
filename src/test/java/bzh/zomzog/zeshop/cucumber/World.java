package bzh.zomzog.zeshop.cucumber;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

@Component
@Scope("cucumber-glue")
public class World {

    public ResultActions actions;
}
