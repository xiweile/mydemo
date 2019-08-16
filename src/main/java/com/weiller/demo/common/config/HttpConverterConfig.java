package com.weiller.demo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

@Configuration
public class HttpConverterConfig implements WebMvcConfigurer {

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        return new MappingJackson2HttpMessageConverter(){
            @Override
            protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                if(object instanceof String){//如果是字符串模式去除双引号
                    StreamUtils.copy((String)object, this.getDefaultCharset(), outputMessage.getBody());
                }else{
                    super.writeInternal(object, type, outputMessage);
                }
            }
        };
    }
 
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = mappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(new LinkedList<MediaType>(){{
            add(MediaType.TEXT_HTML);
            add(MediaType.APPLICATION_JSON_UTF8);
        }});
        converters.add(new StringHttpMessageConverter());
        converters.add(converter);
    }
}