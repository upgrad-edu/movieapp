/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserAuthDaoImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movies.service.user.dao;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;
import upgrad.movies.service.common.dao.BaseDaoImpl;
import upgrad.movies.service.user.entity.UserAuthTokenEntity;

/**
 * Implementation of {@link UserAuthDao}.
 */
@Repository
public class UserAuthDaoImpl extends BaseDaoImpl<UserAuthTokenEntity> implements UserAuthDao {

    @Override
    public UserAuthTokenEntity findToken(final String accessToken) {
        try {
            return entityManager.createNamedQuery(UserAuthTokenEntity.BY_ACCESS_TOKEN, UserAuthTokenEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public UserAuthTokenEntity findByUser(final long userId) {
        try {
            return entityManager.createNamedQuery(UserAuthTokenEntity.BY_USER, UserAuthTokenEntity.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}