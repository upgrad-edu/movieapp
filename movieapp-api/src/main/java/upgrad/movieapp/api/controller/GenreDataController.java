package upgrad.movieapp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static upgrad.movieapp.api.data.ResourceConstants.BASE_URL;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upgrad.movieapp.api.controller.ext.ResponseBuilder;
import upgrad.movieapp.api.model.GenreType;
import upgrad.movieapp.api.model.GenresSummaryResponse;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.business.GenreService;
import upgrad.movieapp.service.movie.entity.GenreEntity;

@RestController
@RequestMapping(BASE_URL)
public class GenreDataController {

    @Autowired
    private GenreService genreService;


    @RequestMapping(method = GET, path = "/genres", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GenresSummaryResponse> getGenres() {

        final SearchResult<GenreEntity> searchResult = genreService.findAllGenres();
        return ResponseBuilder.ok().payload(toGenresSummaryResponse(searchResult)).build();
    }

    private static GenresSummaryResponse toGenresSummaryResponse(final SearchResult<GenreEntity> searchResult) {
        final GenresSummaryResponse genresSummaryResponse = new GenresSummaryResponse().totalCount(searchResult.getTotalCount());
        searchResult.getPayload().forEach(genreEntity -> {
            genresSummaryResponse.addGenresItem(new GenreType().id(UUID.fromString(genreEntity.getUuid())).genre(genreEntity.getGenre()).description(genreEntity.getDescription()));
        });
        return genresSummaryResponse;
    }

}
