package bzh.zomzog.zeshop.cucumber.stepdefs;

import bzh.zomzog.zeshop.ZeShopApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ZeShopApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
