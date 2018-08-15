package upgrad.movieapp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static upgrad.movieapp.api.controller.transformer.ArtistTransformer.toArtistType;
import static upgrad.movieapp.api.controller.transformer.ArtistTransformer.toArtistsSummaryResponse;
import static upgrad.movieapp.api.data.ResourceConstants.BASE_URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import upgrad.movieapp.api.controller.ext.ResponseBuilder;
import upgrad.movieapp.api.model.ArtistType;
import upgrad.movieapp.api.model.ArtistsSummaryResponse;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.business.ArtistService;
import upgrad.movieapp.service.movie.entity.ArtistEntity;
import upgrad.movieapp.service.movie.model.ArtistSearchQuery;

@RestController
@RequestMapping(BASE_URL)
public class ArtistDataController {

    @Autowired
    private ArtistService artistService;


    @RequestMapping(method = GET, path = "/artists", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArtistsSummaryResponse> getArtists(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                             @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                                             @RequestParam(value = "name", required = false) String name) {

        final ArtistSearchQuery searchQuery = new ArtistSearchQuery(page, limit).name(name);
        final SearchResult<ArtistEntity> searchResult = artistService.findArtists(searchQuery);
        return ResponseBuilder.ok().payload(toArtistsSummaryResponse(page, limit, searchResult)).build();
    }

    @RequestMapping(method = GET, path = "/artists/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ArtistType> getArtist(@PathVariable("id") final String artistUuid)
            throws ApplicationException {

        final ArtistEntity artistEntity = artistService.findArtistByUuid(artistUuid);
        return ResponseBuilder.ok().payload(toArtistType(artistEntity)).build();
    }

}
