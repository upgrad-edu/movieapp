package upgrad.movieapp.service.user.model;

import java.util.HashMap;
import java.util.Map;

public enum RoleType {

    ADMINISTRATOR(101), CUSTOMER(102);

    private static final Map<Integer, RoleType> LOOKUP = new HashMap();

    static {
        for (final RoleType enumeration : values()) {
            LOOKUP.put(enumeration.getCode(), enumeration);
        }
    }

    private final int code;

    RoleType(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static RoleType get(final int code) {
        return LOOKUP.get(code);
    }

}
