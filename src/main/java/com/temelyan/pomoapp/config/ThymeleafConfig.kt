package com.temelyan.pomoapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Description
import org.springframework.context.annotation.Profile
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

import java.nio.charset.StandardCharsets

@Configuration
class ThymeleafConfig {

    @Bean
    @Profile("!aws")
    @Description("Thymeleaf template resolver")
    fun templateResolverDev(): ClassLoaderTemplateResolver {
        return getClassLoaderTemplateResolver(false)
    }

    @Bean
    @Profile("aws")
    @Description("Thymeleaf template resolver")
    fun templateResolver(): ClassLoaderTemplateResolver {
        return getClassLoaderTemplateResolver(true)
    }

    private fun getClassLoaderTemplateResolver(isCacheable: Boolean): ClassLoaderTemplateResolver {
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.prefix = "/templates/"
        templateResolver.suffix = ".html"
        templateResolver.isCacheable = isCacheable
        templateResolver.setTemplateMode("HTML")
        templateResolver.characterEncoding = StandardCharsets.UTF_8.name()
        templateResolver.order = 1
        return templateResolver
    }
}
