/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: BasicAuthProvider.java
 * Date: Oct 30, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package upgrad.movieapp.api.controller.provider;

import java.util.Base64;

/**
 * Provider to decode basic auth credentials.
 */
public final class BasicAuthDecoder {

    private final String username;
    private final String password;

    public BasicAuthDecoder(final String base64EncodedCredentials) {
        final String[] base64Decoded = new String(Base64.getDecoder().decode(base64EncodedCredentials.split("Basic ")[1])).split(":");
        this.username = base64Decoded[0];
        this.password = base64Decoded[1];
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}