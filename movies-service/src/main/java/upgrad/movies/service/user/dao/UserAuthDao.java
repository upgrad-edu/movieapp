/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserAuthDao.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movies.service.user.dao;

import javax.validation.constraints.NotNull;

import upgrad.movies.service.common.dao.BaseDao;
import upgrad.movies.service.user.entity.UserAuthTokenEntity;

/**
 * DAO interface for {@link UserAuthTokenEntity}.
 */
public interface UserAuthDao extends BaseDao<UserAuthTokenEntity> {

    UserAuthTokenEntity findToken(@NotNull String accessToken);

    UserAuthTokenEntity findByUser(@NotNull long userId);

}