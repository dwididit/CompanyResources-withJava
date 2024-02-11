package com.testhsm.testhsm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${dwidi.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("didit@dwidi.dev");
        contact.setName("Dwi Didit Prasetiyo");
        contact.setUrl("https://dwidi.dev");

        License apacheLicense = new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html");

        Info info = new Info()
                .title("Company Resources API")
                .version("1.0")
                .contact(contact)
                .description("This RESTful API was created and is managed by Dwi Didit Prasetiyo.").termsOfService("https://dwidi.dev/privacy-policy/")
                .license(apacheLicense);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
