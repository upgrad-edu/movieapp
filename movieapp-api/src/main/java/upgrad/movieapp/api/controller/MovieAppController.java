package upgrad.movieapp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static upgrad.movieapp.api.controller.transformer.MovieTransformer.toMovieSummary;
import static upgrad.movieapp.api.controller.transformer.MovieTransformer.toMoviesSummaryResponse;
import static upgrad.movieapp.api.data.ResourceConstants.BASE_URL;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import upgrad.movieapp.api.controller.ext.ResponseBuilder;
import upgrad.movieapp.api.exception.RestException;
import upgrad.movieapp.api.model.MovieStatusType;
import upgrad.movieapp.api.model.MoviesSummaryResponse;
import upgrad.movieapp.api.model.UserDetailsResponse;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.business.MovieService;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.model.MovieStatus;
import upgrad.movieapp.service.user.exception.UserErrorCode;

@RestController
@RequestMapping(BASE_URL)
public class MovieAppController {

    @Autowired
    private MovieService movieService;


    @RequestMapping(method = GET, path = "/movies", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<MoviesSummaryResponse> getMovies(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                           @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                                           @RequestParam(value = "status", required = false) String status) {

        final SearchResult<MovieEntity> searchResult;
        if (StringUtils.isEmpty(status)) {
            searchResult = movieService.findMovies(page, limit);
        } else {
            searchResult = movieService.findMovies(page, limit, MovieStatus.valueOf(toEnum(status).name()));
        }
        return ResponseBuilder.ok().payload(toMoviesSummaryResponse(page, limit, searchResult)).build();
    }

    @RequestMapping(method = GET, path = "/movies/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getMovie(@PathVariable("id") final String movieUuid)
            throws ApplicationException {

        final MovieEntity movieEntity = movieService.findMovieByUuid(movieUuid);
        return ResponseBuilder.ok().payload(toMovieSummary(movieEntity)).build();
    }


    private MovieStatusType toEnum(final String status) {
        try {
            return MovieStatusType.valueOf(status);
        } catch (IllegalArgumentException exc) {
            throw new RestException(UserErrorCode.USR_010, StringUtils.join(MovieStatusType.values(), ","));
        }
    }

}
