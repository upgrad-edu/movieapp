/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserServiceImpl.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movies.service.user.business;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import upgrad.movies.service.common.exception.ApplicationException;
import upgrad.movies.service.common.exception.EntityNotFoundException;
import upgrad.movies.service.common.model.SearchResult;
import upgrad.movies.service.user.dao.UserDao;
import upgrad.movies.service.user.entity.UserEntity;
import upgrad.movies.service.user.exception.UserErrorCode;
import upgrad.movies.service.user.model.UserStatus;
import upgrad.movies.service.user.provider.PasswordCryptographyProvider;

/**
 * Implementation of {@link UserService}.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @Override
    public SearchResult<UserEntity> findUsers(int page, int limit) {
        return userDao.findUsers(page, limit);
    }

    @Override
    public SearchResult<UserEntity> findUsers(UserStatus userStatus, int page, int limit) {
        return userDao.findUsers(userStatus, page, limit);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserEntity findUserByEmail(final String emailAddress) throws ApplicationException {

        final UserEntity userEntity = userDao.findByEmail(emailAddress);
        if (userEntity == null) {
            throw new EntityNotFoundException(UserErrorCode.USR_002, emailAddress);
        }
        return userEntity;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserEntity findUserByUuid(final String userUuid) throws ApplicationException {

        final UserEntity userEntity = userDao.findByUUID(userUuid);
        if (userEntity == null) {
            throw new EntityNotFoundException(UserErrorCode.USR_001, userUuid);
        }
        return userEntity;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity createUser(final UserEntity newUser, final Integer roleUuid) throws ApplicationException {

        final UserEntity existingUser = userDao.findByEmail(newUser.getEmail());
        if(existingUser != null) {
            throw new ApplicationException(UserErrorCode.USR_009, newUser.getEmail());
        }
        newUser.setRole(roleService.findRoleByUuid(roleUuid));
        encryptPassword(newUser);

        return userDao.create(newUser);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity createUser(final UserEntity newUser) throws ApplicationException {

        final UserEntity userEntity = userDao.findByEmail(newUser.getEmail());
        if(userEntity != null) {
            throw new ApplicationException(UserErrorCode.USR_009, newUser.getEmail());
        }
        encryptPassword(newUser);

        return userDao.create(newUser);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(final String userUuid, final UserEntity updatedUser) throws ApplicationException {

        final UserEntity existingUser = userDao.findByUUID(userUuid);
        if (existingUser == null) {
            throw new EntityNotFoundException(UserErrorCode.USR_001, userUuid);
        }

        if(!UserStatus.ACTIVE.equals(UserStatus.get(existingUser.getStatus()))) {
            throw new ApplicationException(UserErrorCode.USR_008, UserStatus.get(existingUser.getStatus()));
        }

        if(!existingUser.getEmail().equalsIgnoreCase(updatedUser.getEmail()) && userDao.findByEmail(updatedUser.getEmail()) != null) {
            throw new ApplicationException(UserErrorCode.USR_009, updatedUser.getEmail());
        }

        if(StringUtils.isNotEmpty(updatedUser.getFirstName())) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }
        if(StringUtils.isNotEmpty(updatedUser.getLastName())) {
            existingUser.setLastName(updatedUser.getLastName());
        }
        if(StringUtils.isNotEmpty(updatedUser.getEmail())) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if(StringUtils.isNotEmpty(updatedUser.getMobilePhone())) {
            existingUser.setMobilePhone(updatedUser.getMobilePhone());
        }
        if(updatedUser.getRole() != null) {
            existingUser.setRole(updatedUser.getRole());
        }

        userDao.update(existingUser);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void changeUserStatus(final String userUuid, final UserStatus newUserStatus) throws ApplicationException {

        final UserEntity existingUser = userDao.findByUUID(userUuid);
        if (existingUser == null) {
            throw new EntityNotFoundException(UserErrorCode.USR_001, userUuid);
        }

        if(existingUser.getStatus() != newUserStatus.getCode()) {
            existingUser.setStatus(newUserStatus.getCode());
            userDao.update(existingUser);
        }
    }

    @Override
    public void deleteUser(final String userUuid) throws ApplicationException {

        final UserEntity existingUser = userDao.findByUUID(userUuid);
        if(existingUser == null) {
            throw new EntityNotFoundException(UserErrorCode.USR_001, userUuid);
        }

        existingUser.setStatus(UserStatus.DELETED.getCode());
        userDao.update(existingUser);
    }

    private void encryptPassword(final UserEntity newUser) {

        String password = newUser.getPassword();
        if(password == null) {
            password = "proman@123";
        }

        final String[] encryptedData = passwordCryptographyProvider.encrypt(password);
        newUser.setSalt(encryptedData[0]);
        newUser.setPassword(encryptedData[1]);
    }

}