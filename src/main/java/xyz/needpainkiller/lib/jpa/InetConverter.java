package xyz.needpainkiller.lib.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import xyz.needpainkiller.helper.Inets;

@Converter
public class InetConverter implements AttributeConverter<String, Long> {


    public Long convertToDatabaseColumn(String requestIp) {
        return Inets.aton(requestIp);
    }

    public String convertToEntityAttribute(Long requestIp) {
        return Inets.ntoa(requestIp);
    }

}