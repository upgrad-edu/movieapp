package upgrad.movieapp.service.user.business;

import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.user.entity.RoleEntity;

public interface RoleService {

    RoleEntity findRoleByUuid(@NotNull Integer roleUuid) throws ApplicationException;

}