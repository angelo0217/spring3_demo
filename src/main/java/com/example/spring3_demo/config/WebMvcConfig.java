package com.example.spring3_demo.config;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(toStringConverter());
    }

    /**
     * BigDecimal Long 轉化為String
     * @return
     */
    @Bean
    public MappingJackson2HttpMessageConverter toStringConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(BigDecimal.class, new BigDecimalSerialize());
        mapper.registerModule(simpleModule);
        converter.setObjectMapper(mapper);
        return converter;
    }

    public class BigDecimalSerialize extends JsonSerializer<BigDecimal> {
        @Override
        public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException, IOException {
            if (value != null) {
                gen.writeNumber(value.setScale(0, BigDecimal.ROUND_HALF_DOWN));
            } else {
                gen.writeNumber(BigDecimal.valueOf(0));
            }
        }
    }
}