/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserAuthTokenVerifier.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.user.business;

import java.time.ZonedDateTime;

import upgrad.movieapp.service.common.data.DateTimeProvider;
import upgrad.movieapp.service.user.entity.UserAuthTokenEntity;
import upgrad.movieapp.service.user.model.UserAuthTokenStatus;

/**
 * Verifies the authentication token and capture the status.
 */
public final class UserAuthTokenVerifier {

    private final UserAuthTokenStatus status;

    public UserAuthTokenVerifier(final UserAuthTokenEntity userAuthToken) {

        if (userAuthToken == null) {
            status = UserAuthTokenStatus.NOT_FOUND;
        } else if (isLoggedOut(userAuthToken)) {
            status = UserAuthTokenStatus.LOGGED_OUT;
        } else if (isExpired(userAuthToken)) {
            status = UserAuthTokenStatus.EXPIRED;
        } else {
            status = UserAuthTokenStatus.ACTIVE;
        }
    }

    public boolean isActive() {
        return UserAuthTokenStatus.ACTIVE == status;
    }

    public boolean hasExpired() {
        return UserAuthTokenStatus.EXPIRED == status;
    }

    public boolean hasLoggedOut() {
        return UserAuthTokenStatus.LOGGED_OUT == status;
    }

    public boolean isNotFound() {
        return UserAuthTokenStatus.NOT_FOUND == status;
    }

    public UserAuthTokenStatus getStatus() {
        return status;
    }

    private boolean isExpired(final UserAuthTokenEntity userAuthToken) {
        final ZonedDateTime now = DateTimeProvider.currentProgramTime();
        return userAuthToken != null && (userAuthToken.getExpiresAt().isBefore(now) || userAuthToken.getExpiresAt().isEqual(now));
    }

    private boolean isLoggedOut(final UserAuthTokenEntity userAuthToken) {
        return userAuthToken != null && userAuthToken.getLogoutAt() != null;
    }

}