package br.com.sandes.serializationconverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

public class YmlJackson2HttpConverter extends AbstractJackson2HttpMessageConverter {

    public YmlJackson2HttpConverter() {
        super(new YAMLMapper().setSerializationInclusion(
                JsonInclude.Include.NON_NULL),
                MediaType.parseMediaType("application/x-yaml"));
    }
}
