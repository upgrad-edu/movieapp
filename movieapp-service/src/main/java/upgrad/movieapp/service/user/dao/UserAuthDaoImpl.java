/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserAuthDaoImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.user.dao;

import static upgrad.movieapp.service.user.entity.UserAuthTokenEntity.BY_ACCESS_TOKEN;
import static upgrad.movieapp.service.user.entity.UserAuthTokenEntity.BY_USER;

import java.time.ZonedDateTime;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;
import upgrad.movieapp.service.common.dao.BaseDaoImpl;
import upgrad.movieapp.service.user.entity.UserAuthTokenEntity;

/**
 * Implementation of {@link UserAuthDao}.
 */
@Repository
public class UserAuthDaoImpl extends BaseDaoImpl<UserAuthTokenEntity> implements UserAuthDao {

    @Override
    public UserAuthTokenEntity findToken(final String accessToken) {
        try {
            return entityManager.createNamedQuery(BY_ACCESS_TOKEN, UserAuthTokenEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public UserAuthTokenEntity findActiveTokenByUser(final long userId, final ZonedDateTime currentAt) {
        try {
            return entityManager.createNamedQuery(BY_USER, UserAuthTokenEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("currentAt", currentAt)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}