package upgrad.movies.api.servlet;

import static upgrad.movies.api.data.ResourceConstants.*;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import upgrad.movies.api.controller.provider.BearerAuthDecoder;
import upgrad.movies.api.exception.RestErrorCode;
import upgrad.movies.api.exception.UnauthorizedException;
import upgrad.movies.service.common.exception.AuthorizationFailedException;
import upgrad.movies.service.user.business.AuthTokenService;

@WebFilter(filterName = "AuthFilter", urlPatterns = BASE_URL_PATTERN)
public class AuthFilter extends ApiFilter {

    @Autowired
    private AuthTokenService authTokenService;

    @Override
    public void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final String pathInfo = servletRequest.getRequestURI();
        if (!pathInfo.contains("signup") && !pathInfo.contains("swagger-ui") && !pathInfo.contains("v2/api-docs") && !pathInfo.contains("swagger-resources")) {
            final String authorization = servletRequest.getHeader(HEADER_AUTHORIZATION);
            if (StringUtils.isEmpty(authorization)) {
                throw new UnauthorizedException(RestErrorCode.ATH_001);
            }

            if (pathInfo.contains("login") && !authorization.startsWith(BASIC_AUTH_PREFIX)) {
                throw new UnauthorizedException(RestErrorCode.ATH_002);
            }

            if (!pathInfo.contains("login")) {
                final String accessToken = new BearerAuthDecoder(authorization).getAccessToken();
                try {
                    authTokenService.validateToken(accessToken);
                } catch (AuthorizationFailedException e) {
                    servletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
                    return;
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
