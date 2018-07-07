package upgrad.movieapp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static upgrad.movieapp.api.controller.transformer.UserTransformer.toEntity;
import static upgrad.movieapp.api.controller.transformer.UserTransformer.toSignupResponse;
import static upgrad.movieapp.api.data.ResourceConstants.BASE_URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upgrad.movieapp.api.controller.ext.ResponseBuilder;
import upgrad.movieapp.api.model.SignupUserRequest;
import upgrad.movieapp.api.model.SignupUserResponse;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.user.business.UserService;
import upgrad.movieapp.service.user.entity.UserEntity;
import upgrad.movieapp.service.user.model.RoleType;
import upgrad.movieapp.service.user.model.UserStatus;

@RestController
@RequestMapping(BASE_URL)
public class SignupController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = POST, path = "/signup", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupUserResponse> signup(@RequestBody final SignupUserRequest signupUserRequest) throws ApplicationException {

        final UserEntity newUser = toEntity(signupUserRequest);
        newUser.setStatus(UserStatus.ACTIVE.name());

        final UserEntity registeredUser = userService.createUser(newUser, RoleType.CUSTOMER.getCode());
        return ResponseBuilder.created().payload(toSignupResponse(registeredUser)).build();
    }

}
