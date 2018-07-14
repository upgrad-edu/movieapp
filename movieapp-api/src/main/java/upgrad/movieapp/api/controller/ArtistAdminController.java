package upgrad.movieapp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static upgrad.movieapp.api.controller.transformer.ArtistTransformer.toArtistType;
import static upgrad.movieapp.api.controller.transformer.ArtistTransformer.toArtistsSummaryResponse;
import static upgrad.movieapp.api.data.ResourceConstants.BASE_ADMIN_URL;
import static upgrad.movieapp.service.movie.exception.ArtistErrorCode.ART_002;

import java.util.EnumSet;

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
import upgrad.movieapp.api.controller.transformer.ArtistTransformer;
import upgrad.movieapp.api.exception.RestException;
import upgrad.movieapp.api.model.ArtistType;
import upgrad.movieapp.api.model.ArtistsSummaryResponse;
import upgrad.movieapp.api.model.CreateArtistRequest;
import upgrad.movieapp.api.model.CreateArtistResponse;
import upgrad.movieapp.api.model.UpdateArtistRequest;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.business.ArtistService;
import upgrad.movieapp.service.movie.business.MovieService;
import upgrad.movieapp.service.movie.entity.ArtistEntity;
import upgrad.movieapp.service.movie.model.ArtistRoleType;
import upgrad.movieapp.service.movie.model.ArtistSearchQuery;

@RestController
@RequestMapping(BASE_ADMIN_URL)
public class ArtistAdminController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ArtistService artistService;


    @RequestMapping(method = GET, path = "/artists", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArtistsSummaryResponse> getArtists(@RequestHeader("authorization") String accessToken,
                                                             @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                             @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                                             @RequestParam(value = "name", required = false) String name,
                                                             @RequestParam(value = "type", required = false) String roleType) {

        final ArtistSearchQuery searchQuery = new ArtistSearchQuery(page, limit).name(name);
        if (StringUtils.isNotEmpty(roleType)) {
            final EnumSet<ArtistRoleType> artistRoleTypes = EnumSet.noneOf(ArtistRoleType.class);
            String[] roleTypes = StringUtils.split(roleType, ",");
            for (String roleTypeSplit : roleTypes) {
                artistRoleTypes.add(toRoleType(roleTypeSplit.toUpperCase()));
            }
            searchQuery.roleTypes(artistRoleTypes);
        }

        final SearchResult<ArtistEntity> searchResult = artistService.findArtists(searchQuery);
        return ResponseBuilder.ok().payload(toArtistsSummaryResponse(page, limit, searchResult)).build();
    }

    @RequestMapping(method = GET, path = "/artists/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArtistType> getArtist(@RequestHeader("authorization") String accessToken,
                                                @PathVariable("id") final String artistUuid)
            throws ApplicationException {

        final ArtistEntity artistEntity = artistService.findArtistByUuid(artistUuid);
        return ResponseBuilder.ok().payload(toArtistType(artistEntity)).build();
    }

    @RequestMapping(method = POST, path = "/artists", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CreateArtistResponse> createArtist(@RequestHeader("authorization") String accessToken,
                                                             @RequestBody final CreateArtistRequest newArtistRequest) throws ApplicationException {

        final ArtistEntity newArtist = ArtistTransformer.toEntity(newArtistRequest);
        final ArtistEntity createdArtist = artistService.createArtist(newArtist);
        return ResponseBuilder.created().payload(ArtistTransformer.toCreateArtistResponse(createdArtist)).build();
    }

    @RequestMapping(method = PUT, path = "/artists/{id}", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateArtist(@RequestHeader("authorization") String accessToken,
                                       @PathVariable("id") final String artistUuid,
                                       @RequestBody final UpdateArtistRequest updatedArtistRequest) throws ApplicationException {

        final ArtistEntity updatedArtistEntity = ArtistTransformer.toEntity(updatedArtistRequest);
        artistService.updateArtist(artistUuid, updatedArtistEntity);
        return ResponseBuilder.ok().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/artists/{id}")
    public ResponseEntity deleteArtist(@RequestHeader("authorization") String accessToken,
                                       @PathVariable("id") final String artistUuid) throws ApplicationException {
        artistService.deleteArtist(artistUuid);
        return ResponseBuilder.ok().build();
    }

    private ArtistRoleType toRoleType(final String roleType) {

        try {
            return ArtistRoleType.valueOf(upgrad.movieapp.api.model.ArtistRoleType.valueOf(roleType).name());
        } catch (IllegalArgumentException exc) {
            throw new RestException(ART_002, roleType, StringUtils.join(upgrad.movieapp.api.model.ArtistRoleType.values(), ","));
        }
    }

}
