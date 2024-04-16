package xyz.needpainkiller.lib.jpa;


import jakarta.persistence.AttributeConverter;
import xyz.needpainkiller.lib.mybatis.CodeEnum;

public interface CodeEnumConverter<E extends CodeEnum> extends AttributeConverter<E, Integer> {

}
