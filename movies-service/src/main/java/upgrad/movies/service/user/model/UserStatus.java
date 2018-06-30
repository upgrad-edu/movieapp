/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserStatus.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movies.service.user.model;

import java.util.HashMap;
import java.util.Map;

/**
 * An enum representing user's status.
 */
public enum UserStatus {

    INACTIVE(0), ACTIVE(1), LOCKED(2), REGISTERED(3), DELETED(4);

    private static final Map<Integer, UserStatus> LOOKUP = new HashMap();

    static {
        for (final UserStatus enumeration : values()) {
            LOOKUP.put(enumeration.getCode(), enumeration);
        }
    }

    private final int code;

    private UserStatus(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static UserStatus get(final int code) {
        return LOOKUP.get(code);
    }

}