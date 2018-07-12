package upgrad.movieapp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static upgrad.movieapp.api.controller.transformer.MovieTransformer.*;
import static upgrad.movieapp.api.data.ResourceConstants.BASE_ADMIN_URL;
import static upgrad.movieapp.api.model.MovieOperationRequest.PathEnum.RELEASE_DATE;
import static upgrad.movieapp.api.model.MovieOperationRequest.PathEnum.STATUS;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import upgrad.movieapp.api.controller.ext.ResponseBuilder;
import upgrad.movieapp.api.exception.RestException;
import upgrad.movieapp.api.model.CreateMovieRequest;
import upgrad.movieapp.api.model.CreateMovieResponse;
import upgrad.movieapp.api.model.MovieOperationRequest;
import upgrad.movieapp.api.model.MovieOperationsRequest;
import upgrad.movieapp.api.model.MovieStatusType;
import upgrad.movieapp.api.model.MoviesSummaryResponse;
import upgrad.movieapp.api.model.UpdateMovieRequest;
import upgrad.movieapp.api.model.UserDetailsResponse;
import upgrad.movieapp.service.common.data.DateTimeProvider;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.business.MovieService;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.exception.MovieErrorCode;
import upgrad.movieapp.service.movie.model.MovieStatus;

@RestController
@RequestMapping(BASE_ADMIN_URL)
public class MovieAdminController {

    @Autowired
    private MovieService movieService;


    @RequestMapping(method = GET, path = "/movies", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<MoviesSummaryResponse> getMovies(@RequestHeader("authorization") String accessToken,
                                                           @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                           @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                                           @RequestParam(value = "status", required = false) String status) {

        final SearchResult<MovieEntity> searchResult;
        if (StringUtils.isEmpty(status)) {
            searchResult = movieService.findMovies(page, limit);
        } else {
            searchResult = movieService.findMovies(page, limit, toMovieStatus(status));
        }
        return ResponseBuilder.ok().payload(toMoviesSummaryResponse(page, limit, searchResult)).build();
    }

    @RequestMapping(method = GET, path = "/movies/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getMovie(@RequestHeader("authorization") String accessToken,
                                                        @PathVariable("id") final String movieUuid)
            throws ApplicationException {

        final MovieEntity movieEntity = movieService.findMovieByUuid(movieUuid);
        return ResponseBuilder.ok().payload(toMovieSummary(movieEntity)).build();
    }

    @RequestMapping(method = POST, path = "/movies", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CreateMovieResponse> createMove(@RequestHeader("authorization") String accessToken,
                                                          @RequestBody final CreateMovieRequest newMovieRequest) throws ApplicationException {

        final MovieEntity newMovie = toEntity(newMovieRequest);
        final MovieEntity createdMovie = movieService.createMovie(newMovie);
        return ResponseBuilder.created().payload(toCreateMovieResponse(createdMovie)).build();
    }

    @RequestMapping(method = PUT, path = "/movies/{id}", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateMovie(@RequestHeader("authorization") String accessToken,
                                      @PathVariable("id") final String movieUuid,
                                      @RequestBody final UpdateMovieRequest updatedMovieRequest) throws ApplicationException {

        final MovieEntity updatedMovieEntity = toEntity(updatedMovieRequest);
        movieService.updateMovie(movieUuid, updatedMovieEntity);
        return ResponseBuilder.ok().build();
    }

    @RequestMapping(method = PATCH, path = "/movies/{id}", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity patchMovie(@RequestHeader("authorization") String accessToken,
                                     @PathVariable("id") final String movieUuid,
                                     @RequestBody final MovieOperationsRequest movieOperationsRequest) throws ApplicationException {

        for (MovieOperationRequest movieOperationRequest : movieOperationsRequest) {
            if (STATUS == movieOperationRequest.getPath()) {
                movieService.updateStatus(movieUuid, toMovieStatus(movieOperationRequest.getValue()));
            } else if (RELEASE_DATE == movieOperationRequest.getPath()) {
                movieService.updateReleaseDate(movieUuid, DateTimeProvider.parse(movieOperationRequest.getValue()));
            }
        }

        return ResponseBuilder.ok().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/movies/{id}")
    public ResponseEntity deleteUser(@RequestHeader("authorization") String accessToken,
                                     @PathVariable("id") final String movieUuid) throws ApplicationException {
        movieService.deleteMovie(movieUuid);
        return ResponseBuilder.ok().build();
    }

    private MovieStatus toMovieStatus(final String status) {

        try {
            return MovieStatus.valueOf(MovieStatusType.valueOf(status).name());
        } catch (IllegalArgumentException exc) {
            throw new RestException(MovieErrorCode.MVI_002, StringUtils.join(MovieStatusType.values(), ","));
        }
    }

}
