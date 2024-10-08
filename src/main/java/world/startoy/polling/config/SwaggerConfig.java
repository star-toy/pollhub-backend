// src/main/java/com/startoy/polling/config/SwaggerConfig.java
package world.startoy.polling.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {


        return new OpenAPI()
                .addServersItem(new Server().url("/").description("https설정"))
                .info(new Info()
                        .title("polling API")
                        .version("1.0.0")
                        .description("polling API Document"));
    }
}