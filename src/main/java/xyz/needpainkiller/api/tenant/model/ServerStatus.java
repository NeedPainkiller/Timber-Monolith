package xyz.needpainkiller.api.tenant.model;

import lombok.Getter;
import org.apache.ibatis.type.MappedTypes;
import xyz.needpainkiller.lib.jpa.CodeEnumConverter;
import xyz.needpainkiller.lib.mybatis.CodeEnum;
import xyz.needpainkiller.lib.mybatis.CodeEnumTypeHandler;

import java.util.Arrays;

public enum ServerStatus implements CodeEnum {
    NONE(0, false, "미확인"),
    PREPARE(1, false, "준비중"),
    CONNECTED(2, true, "운영중"),
    DISCONNECTED(3, false, "연결 실패"),
    LOGIN_FAILED(4, false, "인증 실패");

    private final int code;

    @Getter
    private final Boolean available;
    @Getter
    private final String label;

    ServerStatus(int code, Boolean available, String label) {
        this.code = code;
        this.available = available;
        this.label = label;
    }

    public static ServerStatus of(int code) {
        return Arrays.stream(values())
                .filter(v -> v.code == code)
                .findFirst().orElse(NONE);
    }

    public static ServerStatus nameOf(String name) {
        return Arrays.stream(values())
                .filter(v -> name.equals(v.name()))
                .findFirst().orElse(NONE);
    }

    public static boolean isExist(ServerStatus solutionType) {
        return !solutionType.equals(NONE);
    }

    public static boolean isExist(int code) {
        return !of(code).equals(NONE);
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @MappedTypes(ServerStatus.class)
    public static class TypeHandler extends CodeEnumTypeHandler<ServerStatus> {
        public TypeHandler() {
            super(ServerStatus.class);
        }
    }

    public static class Converter implements CodeEnumConverter<ServerStatus> {
        @Override
        public Integer convertToDatabaseColumn(ServerStatus attribute) {
            return attribute.getCode();
        }

        @Override
        public ServerStatus convertToEntityAttribute(Integer dbData) {
            return of(dbData);
        }
    }

}
