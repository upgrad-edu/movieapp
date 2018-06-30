package upgrad.movies.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static upgrad.movies.api.controller.transformer.UserTransformer.toEntity;
import static upgrad.movies.api.controller.transformer.UserTransformer.toSignupResponse;
import static upgrad.movies.api.data.ResourceConstants.BASE_URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upgrad.movies.api.controller.ext.ResponseBuilder;
import upgrad.movies.api.model.SignupUserRequest;
import upgrad.movies.api.model.SignupUserResponse;
import upgrad.movies.service.common.exception.ApplicationException;
import upgrad.movies.service.user.business.UserService;
import upgrad.movies.service.user.entity.UserEntity;
import upgrad.movies.service.user.model.RoleType;
import upgrad.movies.service.user.model.UserStatus;

@RestController
@RequestMapping(BASE_URL)
public class SignupController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = POST, path = "/signup", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupUserResponse> signup(@RequestBody final SignupUserRequest signupUserRequest) throws ApplicationException {

        final UserEntity newUserEntity = toEntity(signupUserRequest);
        newUserEntity.setStatus(UserStatus.REGISTERED.getCode());

        final UserEntity registeredUser = userService.createUser(newUserEntity, RoleType.CUSTOMER.getCode());
        return ResponseBuilder.created().payload(toSignupResponse(registeredUser)).build();
    }

}
