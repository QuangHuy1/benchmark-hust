package vn.edu.benchmarkhust.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        String basePackage = "vn.edu.benchmarkhust.controller";
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securitySchemes(Collections.singletonList(tokenAccessKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API Document")
                .description("Benchmark HUST API")
                .version("1.0")
                .build();
    }

    @Bean
    public SecurityScheme tokenAccessKey() {
        return new ApiKey("Authorization", "Bearer", "header");
    }

}
