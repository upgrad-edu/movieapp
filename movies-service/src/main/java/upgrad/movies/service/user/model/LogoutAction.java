/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: LogoutAction.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movies.service.user.model;

import java.util.HashMap;
import java.util.Map;

/**
 * An enum representing user's invalidateToken action.
 */
public enum LogoutAction {

    USER(1), EXPIRED(2);

    private static final Map<Integer, LogoutAction> LOOKUP = new HashMap();

    static {
        for (final LogoutAction enumeration : values()) {
            LOOKUP.put(enumeration.getCode(), enumeration);
        }
    }

    private final int code;

    private LogoutAction(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static LogoutAction get(final int code) {
        return LOOKUP.get(code);
    }

}