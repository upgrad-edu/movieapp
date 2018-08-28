/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: ResourceConstants.java
 * Date: Sep 28, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package upgrad.movieapp.api.data;

/**
 * TODO: Provide javadoc
 */
public interface ResourceConstants {

    String BASE_URL = "/v1";

    String BASE_URL_PATTERN = BASE_URL + "/*";

    String BASE_ADMIN_URL = "/v1/admin";

    String BASE_URL_AUTH = BASE_URL + "/auth";

    String HEADER_AUTHORIZATION = "authorization";

    String HEADER_CLIENT_ID = "client-id";

    String HEADER_CLIENT_IP_ADDRESS = "X-FORWARDED-FOR";

    String HEADER_REQUEST_ID = "request-id";

    String HEADER_ACCESS_TOKEN = "access-token";

    String HEADER_LOCATION = "location";

    String REQUEST_ATTR_REQUEST_CONTEXT = "request-context";

    String BASIC_AUTH_PREFIX = "Basic ";

    String BEARER_AUTH_PREFIX = "Bearer ";

    String AUTHORIZED_USER_UUID = "authorized-user-uuid";

}