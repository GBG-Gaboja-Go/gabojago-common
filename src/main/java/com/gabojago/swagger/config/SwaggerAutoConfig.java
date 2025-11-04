package com.gabojago.swagger.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@AutoConfiguration
@OpenAPIDefinition(
    info = @Info(
        title = "GabojaGo Common API",
        version = "v1.0",
        description = "공통 Swagger 설정"
    )
)
public class SwaggerAutoConfig {

    @Bean
    public OpenAPI commonOpenAPI() {

        return new OpenAPI()
            .info(new io.swagger.v3.oas.models.info.Info()
                .title("GabojaGo API 문서")
                .version("v1.0")
                .description("공통 Swagger 자동 구성"));
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

}