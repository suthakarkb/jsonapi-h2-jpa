package com.sc.csl.retail.crm;

import com.sc.csl.retail.crm.persistence.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EntityScan(basePackageClasses=User.class)
@EnableJpaRepositories("com.sc.csl.retail.crm.persistence.dao")
public class LeadsSvcApp extends WebMvcConfigurerAdapter {

    @Value("${cors.allow.origin}")
    private String originDomain;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // @formatter:off
                registry
                    .addMapping("/**")
                        .allowedOrigins(originDomain)
                        .allowedMethods("GET","POST", "DELETE", "PUT")
                        .maxAge(3600);
                // @formatter:on
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(LeadsSvcApp.class, args);
    }

}

