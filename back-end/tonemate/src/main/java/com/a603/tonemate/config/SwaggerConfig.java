package com.a603.tonemate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
//                //인증 방식
//                .securityContexts(Collections.singletonList(securityContext()))
//                //Swagger 내에서 인증하는 방식
//                .securitySchemes(List.of(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.a603.tonemate.api.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

//    // 인증 방식 설정
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .build();
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return List.of(new SecurityReference("Authorization", authorizationScopes));
//    }
//
//    // 버튼 클릭 시 입력 값 설정
//    private ApiKey apiKey() {
//        return new ApiKey("Authorization", "Authorization", "header");
//    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ToneMate Swagger")
                .description("ToneMate RESTAPI")
                .version("1.0.0")
                .build();
    }
}