package upgrad.movieapp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static upgrad.movieapp.api.data.ResourceConstants.BASE_URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upgrad.movieapp.api.controller.ext.ResponseBuilder;
import upgrad.movieapp.api.model.CitiesSummaryResponse;
import upgrad.movieapp.api.model.CityType;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.business.CityService;
import upgrad.movieapp.service.movie.entity.CityEntity;

@RestController
@RequestMapping(BASE_URL)
public class CityDataController {

    @Autowired
    private CityService cityService;


    @RequestMapping(method = GET, path = "/cities", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CitiesSummaryResponse> getCities() {

        final SearchResult<CityEntity> searchResult = cityService.findAllCities();
        return ResponseBuilder.ok().payload(toCitiesSummaryResponse(searchResult)).build();
    }

    private static CitiesSummaryResponse toCitiesSummaryResponse(final SearchResult<CityEntity> searchResult) {
        final CitiesSummaryResponse citiesSummaryResponse = new CitiesSummaryResponse().totalCount(searchResult.getTotalCount());
        searchResult.getPayload().forEach(genreEntity -> {
            citiesSummaryResponse.addCitiesItem(new CityType().code(genreEntity.getCode()).name(genreEntity.getName()));
        });
        return citiesSummaryResponse;
    }

}
