package com.sc.csl.retail.crm;

import com.sc.csl.retail.crm.persistence.entity.User;

import io.katharsis.resource.registry.ResourceRegistry;
import io.katharsis.spring.boot.KatharsisConfigV2;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
//import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
//@EnableCaching
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages= {"com.sc.csl.retail.crm.resource.repository"})
@EntityScan(basePackageClasses=User.class)
@EnableJpaRepositories("com.sc.csl.retail.crm.persistence.dao")
@RestController
@Import(KatharsisConfigV2.class)
public class LeadsSvcApp extends WebMvcConfigurerAdapter {
	
	@Autowired
	private ResourceRegistry resourceRegistry;

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
    
    @RequestMapping("/resourcesInfo")
    public Map<?, ?> getResources() {
        Map<String, String> result = new HashMap<>();
        for (Class<?> clazz : resourceRegistry.getResources().keySet()) {
           result.put(resourceRegistry.getResourceType(clazz), resourceRegistry.getResourceUrl(clazz));
        }
        return result;
    }

    public static void main(String[] args) {
        SpringApplication.run(LeadsSvcApp.class, args);
    }

}

