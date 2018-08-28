package upgrad.movieapp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static upgrad.movieapp.api.controller.transformer.ArtistTransformer.toArtistType;
import static upgrad.movieapp.api.controller.transformer.ArtistTransformer.toArtistsSummaryResponse;
import static upgrad.movieapp.api.controller.transformer.MovieTransformer.toMovieSummary;
import static upgrad.movieapp.api.controller.transformer.MovieTransformer.toMoviesSummaryResponse;
import static upgrad.movieapp.api.data.ResourceConstants.BASE_URL;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import upgrad.movieapp.api.controller.ext.ResponseBuilder;
import upgrad.movieapp.api.controller.transformer.MovieTransformer;
import upgrad.movieapp.api.controller.transformer.ShowTransformer;
import upgrad.movieapp.api.model.ArtistsSummaryResponse;
import upgrad.movieapp.api.model.CreateMovieResponse;
import upgrad.movieapp.api.model.MovieShowStatusType;
import upgrad.movieapp.api.model.MovieShowType;
import upgrad.movieapp.api.model.MovieShowsSummaryResponse;
import upgrad.movieapp.api.model.MoviesSummaryResponse;
import upgrad.movieapp.api.model.UserDetailsResponse;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.business.ArtistService;
import upgrad.movieapp.service.movie.business.CityService;
import upgrad.movieapp.service.movie.business.MovieService;
import upgrad.movieapp.service.movie.business.ShowService;
import upgrad.movieapp.service.movie.entity.ArtistEntity;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.entity.ShowEntity;
import upgrad.movieapp.service.movie.model.MovieSearchQuery;
import upgrad.movieapp.service.movie.model.ShowSearchQuery;

@RestController
@RequestMapping(BASE_URL)
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ShowService showService;

    @Autowired
    private CityService cityService;

    @RequestMapping(method = GET, path = "/movies", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<MoviesSummaryResponse> getMovies(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                           @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                                           @RequestParam(value = "title", required = false) String title,
                                                           @RequestParam(value = "status", required = false) String status,
                                                           @RequestParam(value = "start_date", required = false) String startDate,
                                                           @RequestParam(value = "end_date", required = false) String endDate,
                                                           @RequestParam(value = "genre", required = false) String genre,
                                                           @RequestParam(value = "artists", required = false) String artists,
                                                           @RequestParam(value = "min_rating", required = false) Float minRating,
                                                           @RequestParam(value = "max_rating", required = false) Float maxRating,
                                                           @RequestParam(value = "sort", required = false) String sort) {

        final MovieSearchQuery movieSearchQuery = MovieTransformer.toSearchQuery(page, limit, title, status,
                startDate, endDate, genre, artists, minRating, maxRating, sort);
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

    @RequestMapping(method = GET, path = "/movies/{movie_id}/shows", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArtistsSummaryResponse> getMovieShows(@PathVariable("movie_id") final String movieUuid,
                                                                @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                                @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                                                @RequestParam(value = "movie_title", required = false) String movieTitle,
                                                                @RequestParam(value = "genre", required = false) String genre,
                                                                @RequestParam(value = "ticket_availability", required = false) Integer ticketAvailability,
                                                                @RequestParam(value = "city_code", required = false) String cityCode,
                                                                @RequestParam(value = "language", required = false) String language,
                                                                @RequestParam(value = "start_time", required = false) String showTimingStart,
                                                                @RequestParam(value = "end_time", required = false) String showTimingEnd,
                                                                @RequestParam(value = "min_price", required = false) BigDecimal minPrice,
                                                                @RequestParam(value = "max_price", required = false) BigDecimal maxPrice)
            throws ApplicationException {

        final ShowSearchQuery searchQuery = ShowTransformer.toSearchQuery(page, limit, movieTitle, genre, ticketAvailability,
                cityCode, language, showTimingStart, showTimingEnd, minPrice, maxPrice, MovieShowStatusType.ACTIVE.name());
        searchQuery.movieUuid(movieUuid);
        final SearchResult<ShowEntity> movieShows = showService.findShows(searchQuery);

        final MovieShowsSummaryResponse movieShowsSummaryResponse = ShowTransformer.toMovieShowsSummaryResponse(page, limit, movieShows);
        movieShowsSummaryResponse.getShows().forEach(movieShowType -> {
            movieShowType.getTheatre().setCity(cityService.findCity(movieShowType.getTheatre().getCityCode()).getName());
        });

        return ResponseBuilder.ok().payload(movieShowsSummaryResponse).build();
    }

    @RequestMapping(method = GET, path = "/movies/{movie_id}/shows/{show_id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CreateMovieResponse> getMovieShowDetails(@PathVariable("movie_id") final String movieUuid,
                                                                   @PathVariable("show_id") final String showUuid) throws ApplicationException {

        final ShowEntity showEntity = showService.findShowByUuid(movieUuid, showUuid);
        final MovieShowType movieShowType = ShowTransformer.toMovieShowType(showEntity);
        movieShowType.getTheatre().setCity(cityService.findCity(movieShowType.getTheatre().getCityCode()).getName());

        return ResponseBuilder.ok().payload(movieShowType).build();
    }

}
