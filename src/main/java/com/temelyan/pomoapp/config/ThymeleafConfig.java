package com.temelyan.pomoapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Profile;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;

@Configuration
public class ThymeleafConfig {

    @Bean
    @Profile("!aws")
    @Description("Thymeleaf template resolver")
    public ClassLoaderTemplateResolver templateResolverDev() {
        return getClassLoaderTemplateResolver(false);
    }

    @Bean
    @Profile("aws")
    @Description("Thymeleaf template resolver")
    public ClassLoaderTemplateResolver templateResolver() {
        return getClassLoaderTemplateResolver(true);
    }

    private ClassLoaderTemplateResolver getClassLoaderTemplateResolver(boolean isCacheable) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(isCacheable);
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setOrder(1);
        return templateResolver;
    }
}