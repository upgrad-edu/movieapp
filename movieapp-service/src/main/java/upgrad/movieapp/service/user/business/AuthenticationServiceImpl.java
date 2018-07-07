/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: AuthenticationServiceImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.user.business;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.exception.AuthenticationFailedException;
import upgrad.movieapp.service.common.exception.EntityNotFoundException;
import upgrad.movieapp.service.user.dao.UserDao;
import upgrad.movieapp.service.user.entity.UserAuthTokenEntity;
import upgrad.movieapp.service.user.entity.UserEntity;
import upgrad.movieapp.service.user.exception.UserErrorCode;
import upgrad.movieapp.service.user.model.AuthorizedUser;
import upgrad.movieapp.service.user.model.UserRole;
import upgrad.movieapp.service.user.model.UserStatus;
import upgrad.movieapp.service.user.provider.PasswordCryptographyProvider;

/**
 * Implementation of {@link AuthenticationService}.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AuthorizedUser authenticate(final String username, final String password) throws ApplicationException {

        UserEntity userEntity;
        try {
            userEntity = userService.findUserByEmail(username);
        } catch (EntityNotFoundException e) {
            throw new AuthenticationFailedException(UserErrorCode.USR_002);
        }

        if(UserStatus.LOCKED == UserStatus.valueOf(userEntity.getStatus())) {
            throw new AuthenticationFailedException(UserErrorCode.USR_007);
        }
        else if(UserStatus.INACTIVE == UserStatus.valueOf(userEntity.getStatus())) {
            throw new AuthenticationFailedException(UserErrorCode.USR_008);
        }
        else {
            final String encryptedPassword = passwordCryptographyProvider.encrypt(password, userEntity.getSalt());
            if (!userEntity.getPassword().equals(encryptedPassword)) {
                int failedLoginCount = userEntity.getFailedLoginCount();
                if (failedLoginCount == 5) {
                    userEntity.setStatus(UserStatus.LOCKED.name());
                }
                else if (userEntity.getFailedLoginCount() <= 5) {
                    failedLoginCount++;
                    userEntity.setFailedLoginCount(failedLoginCount);
                }
                userDao.update(userEntity);
                throw new AuthenticationFailedException(UserErrorCode.USR_003);
            }

            if(userEntity.getFailedLoginCount() > 0) {
                userEntity.setFailedLoginCount(0);
                userDao.update(userEntity);
            }

            UserAuthTokenEntity userAuthToken = authTokenService.issueToken(userEntity);
            return authorizedUser(userEntity, userAuthToken);
        }

    }

    private AuthorizedUser authorizedUser(final UserEntity userEntity, final UserAuthTokenEntity userAuthToken) {
        final AuthorizedUser authorizedUser = new AuthorizedUser();
        authorizedUser.setId(userEntity.getUuid());
        authorizedUser.setFirstName(userEntity.getFirstName());
        authorizedUser.setLastName(userEntity.getLastName());
        authorizedUser.setEmailAddress(userEntity.getEmail());
        authorizedUser.setMobilePhoneNumber(userEntity.getMobilePhone());
        authorizedUser.setLastLoginTime(userEntity.getLastLoginAt());
        authorizedUser.setStatus(UserStatus.valueOf(userEntity.getStatus()));
        authorizedUser.setAccessToken(userAuthToken.getAccessToken());
        if(userEntity.getRole() != null) {
            authorizedUser.setRole(new UserRole(userEntity.getRole().getUuid(), userEntity.getRole().getName()));
        }
        return authorizedUser;
    }

}
