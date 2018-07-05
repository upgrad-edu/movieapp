/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: AuthenticationService.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.user.business;

import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.user.model.AuthorizedUser;

/**
 * Interface for authentication related services.
 */
public interface AuthenticationService {

    AuthorizedUser authenticate(@NotNull String username, @NotNull String password) throws ApplicationException;

}