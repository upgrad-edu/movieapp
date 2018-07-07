/*
 * Copyright 2017-2018, Redux Software.
 *
 * File: AuthenticationController.java
 * Date: Sep 28, 2017
 * Author: P7107311
 * URL: www.redux.com
 */
package upgrad.movieapp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static upgrad.movieapp.api.data.ResourceConstants.BASE_URL_AUTH;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upgrad.movieapp.api.controller.ext.ResponseBuilder;
import upgrad.movieapp.api.controller.provider.BasicAuthDecoder;
import upgrad.movieapp.api.controller.provider.BearerAuthDecoder;
import upgrad.movieapp.api.model.AuthorizedUserResponse;
import upgrad.movieapp.api.model.RoleDetailsType;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.user.business.AuthTokenService;
import upgrad.movieapp.service.user.business.AuthenticationService;
import upgrad.movieapp.service.user.model.AuthorizedUser;

@RestController
@RequestMapping(path = BASE_URL_AUTH)
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthTokenService authTokenService;

    @RequestMapping(method = POST, path = "/login", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AuthorizedUserResponse> login(@RequestHeader final String authorization) throws ApplicationException {
        final BasicAuthDecoder basicAuthDecoder = new BasicAuthDecoder(authorization);
        final AuthorizedUser authorizedUser = authenticationService.authenticate(basicAuthDecoder.getUsername(), basicAuthDecoder.getPassword());
        return ResponseBuilder.ok().payload(toResponse(authorizedUser))
                .accessToken(authorizedUser.getAccessToken()).build();
    }

    @RequestMapping(method = POST, path = "/logout")
    public void logout(@RequestHeader final String authorization) throws ApplicationException {
        final BearerAuthDecoder authDecoder = new BearerAuthDecoder(authorization);
        authTokenService.invalidateToken(authDecoder.getAccessToken());
    }

    private AuthorizedUserResponse toResponse(final AuthorizedUser authorizedUser) {

        final AuthorizedUserResponse authorizedUserResponse = new AuthorizedUserResponse()
                .id(UUID.fromString(authorizedUser.getId()))
                .firstName(authorizedUser.getFirstName()).lastName(authorizedUser.getLastName())
                .emailAddress(authorizedUser.getEmailAddress()).mobilePhone(authorizedUser.getMobilePhoneNumber())
                .lastLoginTime(authorizedUser.getLastLoginTime())
                .status(authorizedUser.getStatus().name());
        if (authorizedUser.getRole() != null) {
            authorizedUserResponse.role(new RoleDetailsType().id(authorizedUser.getRole().getUuid()).name(authorizedUser.getRole().getName()));
        }
        return authorizedUserResponse;
    }

}