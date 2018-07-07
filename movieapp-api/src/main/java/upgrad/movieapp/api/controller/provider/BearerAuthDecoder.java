/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: BearerAuthDecoder.java
 * Date: Nov 10, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package upgrad.movieapp.api.controller.provider;

import static upgrad.movieapp.api.data.ResourceConstants.BEARER_AUTH_PREFIX;

import upgrad.movieapp.api.exception.RestErrorCode;
import upgrad.movieapp.api.exception.UnauthorizedException;

/**
 * Provider to decode bearer token.
 */
public class BearerAuthDecoder {

    private final String accessToken;

    public BearerAuthDecoder(final String bearerToken) {
        if(!bearerToken.startsWith(BEARER_AUTH_PREFIX)) {
            throw new UnauthorizedException(RestErrorCode.ATH_003);
        }

        final String[] bearerTokens = bearerToken.split(BEARER_AUTH_PREFIX);
        if(bearerTokens.length != 2) {
            throw new UnauthorizedException(RestErrorCode.ATH_004);
        }
        this.accessToken = bearerTokens[1];
    }

    public String getAccessToken() {
        return accessToken;
    }

}