package bzh.zomzog.zeshop.cucumber.stepdefs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import bzh.zomzog.zeshop.ZeShopApp;
import bzh.zomzog.zeshop.cucumber.World;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ZeShopApp.class)
public abstract class StepDefs {

    @Autowired
    protected World world;

}
