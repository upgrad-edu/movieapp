/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserAuthDao.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.user.dao;

import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.dao.BaseDao;
import upgrad.movieapp.service.user.entity.UserAuthTokenEntity;

/**
 * DAO interface for {@link UserAuthTokenEntity}.
 */
public interface UserAuthDao extends BaseDao<UserAuthTokenEntity> {

    UserAuthTokenEntity findToken(@NotNull String accessToken);

    UserAuthTokenEntity findActiveTokenByUser(@NotNull long userId, @NotNull ZonedDateTime currentAt);

}