package upgrad.movieapp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static upgrad.movieapp.api.controller.transformer.UserTransformer.*;
import static upgrad.movieapp.api.data.ResourceConstants.BASE_ADMIN_URL;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import upgrad.movieapp.api.controller.ext.ResponseBuilder;
import upgrad.movieapp.api.exception.RestException;
import upgrad.movieapp.api.model.CreateUserRequest;
import upgrad.movieapp.api.model.CreateUserResponse;
import upgrad.movieapp.api.model.UpdateUserRequest;
import upgrad.movieapp.api.model.UserDetailsResponse;
import upgrad.movieapp.api.model.UserOperationRequest;
import upgrad.movieapp.api.model.UserOperationsRequest;
import upgrad.movieapp.api.model.UserStatusType;
import upgrad.movieapp.api.model.UsersSummaryResponse;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.user.business.RoleService;
import upgrad.movieapp.service.user.business.UserService;
import upgrad.movieapp.service.user.entity.RoleEntity;
import upgrad.movieapp.service.user.entity.UserEntity;
import upgrad.movieapp.service.user.exception.UserErrorCode;
import upgrad.movieapp.service.user.model.UserStatus;

@RestController
@RequestMapping(BASE_ADMIN_URL)
public class UserAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @RequestMapping(method = GET, path = "/users", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UsersSummaryResponse> getUsers(@RequestHeader("authorization") String accessToken,
                                                         @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                         @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                                         @RequestParam(value = "status", required = false) String status) {

        final SearchResult<UserEntity> searchResult;
        if (StringUtils.isEmpty(status)) {
            searchResult = userService.findUsers(page, limit);
        } else {
            searchResult = userService.findUsers(UserStatus.valueOf(toEnum(status).name()), page, limit);
        }
        return ResponseBuilder.ok().payload(toUsersSummaryResponse(page, limit, searchResult)).build();
    }

    @RequestMapping(method = GET, path = "/users/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUser(@RequestHeader("authorization") String accessToken,
                                                       @PathVariable("id") final String userUuid)
            throws ApplicationException {

        final UserEntity userEntity = userService.findUserByUuid(userUuid);
        return ResponseBuilder.ok().payload(toUserDetailsResponse(userEntity)).build();
    }

    @RequestMapping(method = POST, path = "/users", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CreateUserResponse> createUser(@RequestHeader("authorization") String accessToken,
                                                         @RequestBody final CreateUserRequest newUserRequest) throws ApplicationException {

        final UserEntity newUser = toEntity(newUserRequest);
        newUser.setStatus(UserStatus.ACTIVE.name());

        final UserEntity createdUser = userService.createUser(newUser, newUserRequest.getRole().getId());
        return ResponseBuilder.created().payload(toCreateUserResponse(createdUser)).build();
    }

    @RequestMapping(method = PUT, path = "/users/{id}", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateUser(@RequestHeader("authorization") String accessToken,
                                     @PathVariable("id") final String userUuid,
                                     @RequestBody final UpdateUserRequest updatedUserRequest) throws ApplicationException {

        final UserEntity updatedUserEntity = toEntity(updatedUserRequest);
        final RoleEntity roleEntity = roleService.findRoleByUuid(updatedUserRequest.getRole().getId());
        updatedUserEntity.setRole(roleEntity);

        userService.updateUser(userUuid, updatedUserEntity);
        return ResponseBuilder.ok().build();
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/users/{id}", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity patchUser(@RequestHeader("authorization") String accessToken,
                                     @PathVariable("id") final String userUuid,
                                     @RequestBody final UserOperationsRequest userOperationsRequest) throws ApplicationException {

        for (UserOperationRequest userOperationRequest : userOperationsRequest) {
            userService.changeUserStatus(userUuid, UserStatus.valueOf(toEnum(userOperationRequest.getValue()).name()));
        }

        return ResponseBuilder.ok().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/users/{id}")
    public ResponseEntity deleteUser(@RequestHeader("authorization") String accessToken,
                                     @PathVariable("id") final String userUuid) throws ApplicationException {
        userService.deleteUser(userUuid);
        return ResponseBuilder.ok().build();
    }


    private UserStatusType toEnum(final String status) {
        try {
            return UserStatusType.valueOf(status);
        } catch (IllegalArgumentException exc) {
            throw new RestException(UserErrorCode.USR_010, StringUtils.join(UserStatusType.values(), ","));
        }
    }

}
