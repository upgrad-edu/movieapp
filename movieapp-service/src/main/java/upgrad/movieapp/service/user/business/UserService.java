/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserService.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.user.business;

import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.user.entity.UserEntity;
import upgrad.movieapp.service.user.model.UserStatus;

/**
 * Interface for user related services.
 */
public interface UserService {

    SearchResult<UserEntity> findUsers(int page, int limit);

    SearchResult<UserEntity> findUsers(UserStatus userStatus, int page, int limit);

    UserEntity findUserByEmail(@NotNull String emailAddress) throws ApplicationException;

    UserEntity findUserByUuid(@NotNull String userUuid) throws ApplicationException;

    UserEntity createUser(@NotNull UserEntity newUser, @NotNull Integer roleUuid) throws ApplicationException;

    UserEntity createUser(@NotNull UserEntity newUser) throws ApplicationException;

    void updateUser(@NotNull String userUuid, @NotNull UserEntity updatedUser) throws ApplicationException;

    void changeUserStatus(@NotNull String userUuid, @NotNull UserStatus newUserStatus) throws ApplicationException;

    void deleteUser(@NotNull String userUuid) throws ApplicationException;

}