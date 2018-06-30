package upgrad.movies.service.user.business;

import javax.validation.constraints.NotNull;

import upgrad.movies.service.common.exception.ApplicationException;
import upgrad.movies.service.user.entity.RoleEntity;

public interface RoleService {

    RoleEntity findRoleByUuid(@NotNull Integer roleUuid) throws ApplicationException;

}