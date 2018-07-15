package upgrad.movieapp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static upgrad.movieapp.api.controller.transformer.TheatreTransformer.*;
import static upgrad.movieapp.api.data.ResourceConstants.BASE_ADMIN_URL;

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
import upgrad.movieapp.api.controller.transformer.TheatreTransformer;
import upgrad.movieapp.api.exception.RestException;
import upgrad.movieapp.api.model.CreateTheatreRequest;
import upgrad.movieapp.api.model.CreateTheatreResponse;
import upgrad.movieapp.api.model.MovieStatusType;
import upgrad.movieapp.api.model.MoviesSummaryResponse;
import upgrad.movieapp.api.model.UpdateTheatreRequest;
import upgrad.movieapp.api.model.UserDetailsResponse;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.business.TheatreService;
import upgrad.movieapp.service.movie.entity.TheatreEntity;
import upgrad.movieapp.service.movie.exception.MovieErrorCode;
import upgrad.movieapp.service.movie.model.MovieStatus;
import upgrad.movieapp.service.movie.model.TheatreSearchQuery;

@RestController
@RequestMapping(BASE_ADMIN_URL)
public class TheatreAdminController {

    @Autowired
    private TheatreService theatreService;

    @RequestMapping(method = GET, path = "/theatres", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<MoviesSummaryResponse> getTheatres(@RequestHeader("authorization") String accessToken,
                                                             @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                             @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                                             @RequestParam(value = "name", required = false) String name,
                                                             @RequestParam(value = "city_code", required = false) String cityCode,
                                                             @RequestParam(value = "status", required = false) String status) {

        final TheatreSearchQuery theatreSearchQuery = toSearchQuery(page, limit, name, cityCode, status);
        final SearchResult<TheatreEntity> searchResult = theatreService.findTheatres(theatreSearchQuery);
        return ResponseBuilder.ok().payload(toTheatresSummaryResponse(page, limit, searchResult)).build();
    }

    @RequestMapping(method = GET, path = "/theatres/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getTheatre(@RequestHeader("authorization") String accessToken,
                                                          @PathVariable("id") final String theatreUuid)
            throws ApplicationException {

        final TheatreEntity movieEntity = theatreService.findTheatreByUuid(theatreUuid);
        return ResponseBuilder.ok().payload(TheatreTransformer.toTheatreSummary(movieEntity)).build();
    }

    @RequestMapping(method = POST, path = "/theatres", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CreateTheatreResponse> createTheatre(@RequestHeader("authorization") String accessToken,
                                                               @RequestBody final CreateTheatreRequest newMovieRequest) throws ApplicationException {

        final TheatreEntity newTheatre = TheatreTransformer.toEntity(newMovieRequest);
        final TheatreEntity createdTheatre = theatreService.createTheatre(newTheatre);
        return ResponseBuilder.created().payload(toCreateTheatreResponse(createdTheatre)).build();
    }

    @RequestMapping(method = PUT, path = "/theatres/{id}", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateTheatre(@RequestHeader("authorization") String accessToken,
                                        @PathVariable("id") final String theatreUuid,
                                        @RequestBody final UpdateTheatreRequest updateTheatreRequest) throws ApplicationException {

        final TheatreEntity updatedTheatreEntity = TheatreTransformer.toEntity(updateTheatreRequest);
        theatreService.updateTheatre(theatreUuid, updatedTheatreEntity);
        return ResponseBuilder.ok().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/theatres/{id}")
    public ResponseEntity deleteTheatre(@RequestHeader("authorization") String accessToken,
                                        @PathVariable("id") final String theatreUuid) throws ApplicationException {
        theatreService.deleteTheatre(theatreUuid);
        return ResponseBuilder.ok().build();
    }

}
