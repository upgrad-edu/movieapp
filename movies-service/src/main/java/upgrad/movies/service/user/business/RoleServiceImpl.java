package upgrad.movies.service.user.business;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upgrad.movies.service.common.exception.ApplicationException;
import upgrad.movies.service.common.exception.EntityNotFoundException;
import upgrad.movies.service.user.dao.RoleDao;
import upgrad.movies.service.user.entity.RoleEntity;
import upgrad.movies.service.user.exception.UserErrorCode;

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
