package upgrad.movieapp.api.servlet;

import static upgrad.movieapp.api.data.ResourceConstants.BASE_URL_PATTERN;
import static upgrad.movieapp.api.data.ResourceConstants.HEADER_REQUEST_ID;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "RequestContextFilter", urlPatterns = BASE_URL_PATTERN)
public class RequestContextFilter extends ApiFilter {

    @Override
    public void doFilter(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain) throws IOException, ServletException {
        httpResponse.addHeader(HEADER_REQUEST_ID, UUID.randomUUID().toString());
        filterChain.doFilter(httpRequest, httpResponse);
    }

}
