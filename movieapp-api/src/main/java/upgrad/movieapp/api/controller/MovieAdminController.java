package upgrad.movieapp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static upgrad.movieapp.api.controller.transformer.ArtistTransformer.toArtistType;
import static upgrad.movieapp.api.controller.transformer.ArtistTransformer.toArtistsSummaryResponse;
import static upgrad.movieapp.api.controller.transformer.MovieTransformer.*;
import static upgrad.movieapp.api.data.ResourceConstants.BASE_ADMIN_URL;
import static upgrad.movieapp.api.model.MovieOperationRequest.PathEnum.RELEASE_DATE;
import static upgrad.movieapp.api.model.MovieOperationRequest.PathEnum.STATUS;
import static upgrad.movieapp.service.common.data.DateTimeProvider.SIMPLE_DATE_FORMAT;
import static upgrad.movieapp.service.common.data.DateTimeProvider.parse;

import java.util.Set;

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
import upgrad.movieapp.api.controller.transformer.MovieTransformer;
import upgrad.movieapp.api.exception.RestException;
import upgrad.movieapp.api.model.ArtistsSummaryResponse;
import upgrad.movieapp.api.model.CreateMovieRequest;
import upgrad.movieapp.api.model.CreateMovieResponse;
import upgrad.movieapp.api.model.MovieOperationRequest;
import upgrad.movieapp.api.model.MovieOperationsRequest;
import upgrad.movieapp.api.model.MovieStatusType;
import upgrad.movieapp.api.model.MoviesSummaryResponse;
import upgrad.movieapp.api.model.UpdateMovieRequest;
import upgrad.movieapp.api.model.UserDetailsResponse;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.business.ArtistService;
import upgrad.movieapp.service.movie.business.MovieService;
import upgrad.movieapp.service.movie.entity.ArtistEntity;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.exception.MovieErrorCode;
import upgrad.movieapp.service.movie.model.MovieSearchQuery;
import upgrad.movieapp.service.movie.model.MovieStatus;

@RestController
@RequestMapping(BASE_ADMIN_URL)
public class MovieAdminController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ArtistService artistService;


    @RequestMapping(method = GET, path = "/movies", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<MoviesSummaryResponse> getMovies(@RequestHeader("authorization") String accessToken,
                                                           @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                           @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                                           @RequestParam(value = "title", required = false) String title,
                                                           @RequestParam(value = "status", required = false) String status,
                                                           @RequestParam(value = "start_date", required = false) String startDate,
                                                           @RequestParam(value = "end_date", required = false) String endDate,
                                                           @RequestParam(value = "genre", required = false) String genre,
                                                           @RequestParam(value = "min_rating", required = false) Float minRating,
                                                           @RequestParam(value = "max_rating", required = false) Float maxRating,
                                                           @RequestParam(value = "sort", required = false) String sort) {

        final MovieSearchQuery movieSearchQuery = MovieTransformer.toSearchQuery(page, limit, title, status,
                startDate, endDate, genre, minRating, maxRating, sort);
        final SearchResult<MovieEntity> searchResult = movieService.findMovies(movieSearchQuery);
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
    public ResponseEntity<CreateMovieResponse> createMovie(@RequestHeader("authorization") String accessToken,
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
                movieService.updateReleaseDate(movieUuid, parse(movieOperationRequest.getValue(), SIMPLE_DATE_FORMAT));
            }
        }

        return ResponseBuilder.ok().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/movies/{id}")
    public ResponseEntity deleteMovie(@RequestHeader("authorization") String accessToken,
                                     @PathVariable("id") final String movieUuid) throws ApplicationException {
        movieService.deleteMovie(movieUuid);
        return ResponseBuilder.ok().build();
    }

    @RequestMapping(method = GET, path = "/movies/{movieId}/artists", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArtistsSummaryResponse> getMovieArtists(@RequestHeader("authorization") String accessToken,
                                                                  @PathVariable("movieId") final String movieUuid)
            throws ApplicationException {

        SearchResult<ArtistEntity> movieArtists = artistService.findArtists(movieUuid);
        return ResponseBuilder.ok().payload(toArtistsSummaryResponse(1, -1, movieArtists)).build();
    }

    @RequestMapping(method = GET, path = "/movies/{movieId}/artists/{artistId}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArtistsSummaryResponse> getMovieArtistDetails(@RequestHeader("authorization") String accessToken,
                                                                        @PathVariable("movieId") final String movieUuid,
                                                                        @PathVariable("artistId") final String artistUuid)
            throws ApplicationException {

        ArtistEntity movieArtist = artistService.findArtist(movieUuid, artistUuid);
        return ResponseBuilder.ok().payload(toArtistType(movieArtist)).build();
    }

    private MovieStatus toMovieStatus(final String status) {

        try {
            return MovieStatus.valueOf(MovieStatusType.valueOf(status).name());
        } catch (IllegalArgumentException exc) {
            throw new RestException(MovieErrorCode.MVI_002, status, StringUtils.join(MovieStatusType.values(), ","));
        }
    }

}
