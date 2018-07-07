package upgrad.movieapp.service.user.business;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.exception.EntityNotFoundException;
import upgrad.movieapp.service.user.entity.RoleEntity;
import upgrad.movieapp.service.user.exception.UserErrorCode;
import upgrad.movieapp.service.user.dao.RoleDao;

@Service
public class RoleServiceImpl implements  RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public RoleEntity findRoleByUuid(@NotNull Integer roleUuid) throws ApplicationException {

        RoleEntity roleEntity = roleDao.findByUUID(roleUuid);
        if(roleEntity == null) {
            throw new EntityNotFoundException(UserErrorCode.USR_011, roleUuid);
        }
        return roleEntity;
    }
}
