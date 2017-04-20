package bzh.zomzog.zeshop.auth.configuration;

import java.sql.SQLException;

import org.h2.server.web.WebServlet;
import org.h2.tools.Server;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@Configuration
public class DatabaseConfiguration {
    /**
     * Open the TCP port for the H2 database, so it is available remotely.
     *
     * @return the H2 database TCP server
     * @throws SQLException
     *             if the server failed to start
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2TCPServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers");
    }

    @Bean
    ServletRegistrationBean h2servletRegistration() {
        final ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }

    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }
}
