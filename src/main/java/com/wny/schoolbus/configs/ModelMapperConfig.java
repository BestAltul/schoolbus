package com.wny.schoolbus.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Artem Peshko
 * E-Mail artem.peshko@gmail.com
 * pasv-store
 * Когда я начинал это писать, только я и Бог знали, как это работает.
 * Сейчас знает только Бог.
 */

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
