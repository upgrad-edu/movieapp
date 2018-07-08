package upgrad.movieapp.api.servlet;

import static upgrad.movieapp.api.data.ResourceConstants.*;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import upgrad.movieapp.api.exception.RestErrorCode;
import upgrad.movieapp.api.exception.UnauthorizedException;
import upgrad.movieapp.api.controller.provider.BearerAuthDecoder;
import upgrad.movieapp.service.common.exception.AuthorizationFailedException;
import upgrad.movieapp.service.user.business.AuthTokenService;

@WebFilter(filterName = "AuthFilter", urlPatterns = BASE_URL_PATTERN)
public class AuthFilter extends ApiFilter {

    @Autowired
    private AuthTokenService authTokenService;

    @Override
    public void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final String pathInfo = servletRequest.getRequestURI();
        if (pathInfo.contains("admin") || pathInfo.contains("login") || pathInfo.contains("bookings")) {
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
