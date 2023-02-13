package com.ead.course.configuration;

import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.cloud.openfeign.support.SortJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfigurationFactory {

    /*Classe de configuração criada por conta de um erro de desserialização do Objeto Page, quando retornado
      de uma comunicação sincrona usando o FeginClient*/

    @Bean
    public PageJacksonModule pageJacksonModule() {
        return new PageJacksonModule();
    }

    @Bean
    public SortJacksonModule sortJacksonModule() {
        return new SortJacksonModule();
    }
}
