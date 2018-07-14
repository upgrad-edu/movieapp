package upgrad.movieapp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static upgrad.movieapp.api.controller.transformer.ArtistTransformer.toArtistType;
import static upgrad.movieapp.api.controller.transformer.ArtistTransformer.toArtistsSummaryResponse;
import static upgrad.movieapp.api.controller.transformer.MovieTransformer.toMovieSummary;
import static upgrad.movieapp.api.controller.transformer.MovieTransformer.toMoviesSummaryResponse;
import static upgrad.movieapp.api.data.ResourceConstants.BASE_URL;
import static upgrad.movieapp.service.movie.exception.MovieErrorCode.MVI_002;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import upgrad.movieapp.api.controller.ext.ResponseBuilder;
import upgrad.movieapp.api.controller.transformer.MovieTransformer;
import upgrad.movieapp.api.exception.RestException;
import upgrad.movieapp.api.model.ArtistsSummaryResponse;
import upgrad.movieapp.api.model.MovieStatusType;
import upgrad.movieapp.api.model.MoviesSummaryResponse;
import upgrad.movieapp.api.model.UserDetailsResponse;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.business.ArtistService;
import upgrad.movieapp.service.movie.business.MovieService;
import upgrad.movieapp.service.movie.entity.ArtistEntity;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.model.MovieSearchQuery;

@RestController
@RequestMapping(BASE_URL)
public class MovieAppController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ArtistService artistService;


    @RequestMapping(method = GET, path = "/movies", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<MoviesSummaryResponse> getMovies(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                           @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                                           @RequestParam(value = "title", required = false) String title,
                                                           @RequestParam(value = "status", required = false) String status,
                                                           @RequestParam(value = "releasedate_from", required = false) String releaseDateFrom,
                                                           @RequestParam(value = "releasedate_to", required = false) String releaseDateTo,
                                                           @RequestParam(value = "genre", required = false) String genre,
                                                           @RequestParam(value = "rating_min", required = false) Float ratingMin,
                                                           @RequestParam(value = "rating_max", required = false) Float ratingMax,
                                                           @RequestParam(value = "sort", required = false) String sort) {

        final MovieSearchQuery movieSearchQuery = MovieTransformer.toSearchQuery(page, limit, title, status,
                releaseDateFrom, releaseDateTo, genre, ratingMin, ratingMax, sort);
        final SearchResult<MovieEntity> searchResult = movieService.findMovies(movieSearchQuery);
        return ResponseBuilder.ok().payload(toMoviesSummaryResponse(page, limit, searchResult)).build();
    }

    @RequestMapping(method = GET, path = "/movies/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getMovie(@PathVariable("id") final String movieUuid)
            throws ApplicationException {

        final MovieEntity movieEntity = movieService.findMovieByUuid(movieUuid);
        return ResponseBuilder.ok().payload(toMovieSummary(movieEntity)).build();
    }

    @RequestMapping(method = GET, path = "/movies/{id}/artists", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArtistsSummaryResponse> getMovieArtists(@PathVariable("id") final String movieUuid)
            throws ApplicationException {

        SearchResult<ArtistEntity> movieArtists = artistService.findArtists(movieUuid);
        return ResponseBuilder.ok().payload(toArtistsSummaryResponse(1, -1, movieArtists)).build();
    }

    @RequestMapping(method = GET, path = "/movies/{movieId}/artists/{artistId}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArtistsSummaryResponse> getMovieArtistDetails(@PathVariable("movieId") final String movieUuid,
                                                                        @PathVariable("artistId") final String artistUuid)
            throws ApplicationException {

        ArtistEntity movieArtist = artistService.findArtist(movieUuid, artistUuid);
        return ResponseBuilder.ok().payload(toArtistType(movieArtist)).build();
    }

    private MovieStatusType toEnum(final String status) {
        try {
            return MovieStatusType.valueOf(status);
        } catch (IllegalArgumentException exc) {
            throw new RestException(MVI_002, status, StringUtils.join(MovieStatusType.values(), ","));
        }
    }

}
