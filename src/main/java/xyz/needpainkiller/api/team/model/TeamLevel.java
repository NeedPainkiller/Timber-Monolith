package xyz.needpainkiller.api.team.model;

import org.apache.ibatis.type.MappedTypes;
import xyz.needpainkiller.lib.jpa.CodeEnumConverter;
import xyz.needpainkiller.lib.mybatis.CodeEnum;
import xyz.needpainkiller.lib.mybatis.CodeEnumTypeHandler;

import java.util.Arrays;

public enum TeamLevel implements CodeEnum {

    NONE(0), // 미확인
    ROOT(1),  // HEAD 단위
    NODE(2); // LEAF 단위
    private final int code;

    TeamLevel(int code) {
        this.code = code;
    }

    public static TeamLevel of(int code) {
        return Arrays.stream(values())
                .filter(v -> v.code == code)
                .findFirst().orElse(NONE);
    }

    public static TeamLevel nameOf(String name) {
        return Arrays.stream(values())
                .filter(v -> name.equals(v.name()))
                .findFirst().orElse(NONE);
    }

    public static boolean isExist(TeamLevel taskStatus) {
        return !taskStatus.equals(NONE);
    }

    public static boolean isExist(int code) {
        return !of(code).equals(NONE);
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @MappedTypes(TeamLevel.class)
    public static class TypeHandler extends CodeEnumTypeHandler<TeamLevel> {
        public TypeHandler() {
            super(TeamLevel.class);
        }
    }

    public static class Converter implements CodeEnumConverter<TeamLevel> {
        @Override
        public Integer convertToDatabaseColumn(TeamLevel attribute) {
            return attribute.getCode();
        }

        @Override
        public TeamLevel convertToEntityAttribute(Integer dbData) {
            return of(dbData);
        }
    }
}