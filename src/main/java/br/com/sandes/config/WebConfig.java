package br.com.sandes.config;

import br.com.sandes.serializationconverter.YmlJackson2HttpConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration //serve para que quando o spring carregar, o spring leia esse arquivo pos contem informações de configuração da aplicação
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation
            (ContentNegotiationConfigurer configurer){

        //retornando via header

        final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");

        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                    .mediaType("json", MediaType.APPLICATION_JSON)
                    .mediaType("xml", MediaType.APPLICATION_XML)
                    .mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters){

        converters.add(new YmlJackson2HttpConverter());
    }
}
