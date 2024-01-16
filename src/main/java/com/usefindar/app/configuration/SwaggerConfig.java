package com.usefindar.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    private static final String SWAGGER_API_VERSION = "1.0";
    private static final String LICENCE_TEXT = "MIT License";
    private static final String LICENCE_URL = "https://www.opensource.org/licenses/MIT";
    private static final String TITTLE = "UseFindar API Service";
    private static final String DESCRIPTION = "Restful API for Book Management Test Service.";
    private static final String CONTACT_NAME = "Ibikunle .A Adeoluwa";
    private static final String CONTACT_EMAIL = "dev.ibikunle@gmail.com";
    private static final String CONTACT_URL = "https://www.linkedin.com/in/ibikunle-adeoluwa";
    private static final String TERMS = "https://www.linkedin.com/in/ibikunle-adeoluwa";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(TITTLE)
                .description(DESCRIPTION)
                .license(LICENCE_TEXT)
                .version(SWAGGER_API_VERSION)
                .license(LICENCE_URL)
                .contact(new Contact(CONTACT_NAME, CONTACT_URL, CONTACT_EMAIL))
                .termsOfServiceUrl(TERMS)
                .build();
    }

    @Bean
    public Docket projectsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
