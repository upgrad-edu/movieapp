/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: AuthTokenService.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.user.business;

import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.exception.AuthorizationFailedException;
import upgrad.movieapp.service.user.entity.UserAuthTokenEntity;
import upgrad.movieapp.service.user.entity.UserEntity;

/**
 * Interface for user authentication related services.
 */
public interface AuthTokenService {

    UserAuthTokenEntity issueToken(@NotNull UserEntity userEntity) throws AuthorizationFailedException;

    void invalidateToken(@NotNull String accessToken) throws AuthorizationFailedException;

    UserAuthTokenEntity validateToken(@NotNull String accessToken) throws AuthorizationFailedException;

}