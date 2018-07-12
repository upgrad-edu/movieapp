/*
 * Copyright 2018-2019, https://beingtechie.io
 *
 * File: UserErrorCode.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.user.exception;

import java.util.HashMap;
import java.util.Map;

import upgrad.movieapp.service.common.exception.ErrorCode;

/**
 * Error code for USER module.
 */
public enum UserErrorCode implements ErrorCode {

    USR_001("USR-001", "User with identifier [{0}] does not exist"),

    USR_002("USR-002", "Username does not exist"),

    USR_003("USR-003", "Password match failed"),

    USR_004("USR-004", "Concurrent login attempt by the user from other device(s)"),

    USR_005("USR-005", "Invalid access token"),

    USR_006("USR-006", "Access token is already expired"),

    USR_007("USR-007", "User account is LOCKED"),

    USR_008("USR-008", "User account is not ACTIVE. Current status is {0}"),

    USR_009("USR-009", "User with email address [{0}] already exist"),

    USR_010("USR-010", "Not a valid user status. Supported statuses are [{0}]"),

    USR_011("USR-011", "Role with identifier [{0}] does not exist"),

    USR_012("USR-012", "User with identifier [{0}] cannot be updated as it is in DELETED status.")
    ;

    private static final Map<String, UserErrorCode> LOOKUP = new HashMap<String, UserErrorCode>();

    static {
        for (final UserErrorCode enumeration : UserErrorCode.values()) {
            LOOKUP.put(enumeration.getCode(), enumeration);
        }
    }

    private final String code;

    private final String defaultMessage;

    private UserErrorCode(final String code, final String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDefaultMessage() {
        return defaultMessage;
    }

}