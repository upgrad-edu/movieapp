package upgrad.movieapp.api.controller.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import upgrad.movieapp.api.model.CensorBoardRatingType;
import upgrad.movieapp.api.model.MovieStatusType;
import upgrad.movieapp.api.model.MovieSummaryType;
import upgrad.movieapp.api.model.MoviesSummaryResponse;
import upgrad.movieapp.service.common.data.DateTimeProvider;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.MovieEntity;

public final class MovieTransformer {

    public static MoviesSummaryResponse toMoviesSummaryResponse(final int page, final int limit, final SearchResult<MovieEntity> searchResult) {

        final MoviesSummaryResponse moviesSummaryResponse = new MoviesSummaryResponse().totalCount(searchResult.getTotalCount()).page(page).limit(limit);
        for(MovieEntity movieEntity : searchResult.getPayload()) {
            moviesSummaryResponse.addMoviesItem(toMovieSummary(movieEntity));
        }

        return moviesSummaryResponse;
    }

    public static MovieSummaryType toMovieSummary(MovieEntity movieEntity) {
        final MovieSummaryType movieSummaryType = new MovieSummaryType().id(UUID.fromString(movieEntity.getUuid()))
                .title(movieEntity.getTitle())
                .genres(toGenres(movieEntity.getGenres()))
                .duration(movieEntity.getDuration())
                .storyline(movieEntity.getStoryline())
                .posterUrl(movieEntity.getPosterUrl())
                .trailerUrl(movieEntity.getTrailerUrl())
                .wikiUrl(movieEntity.getWikiUrl())
                .releaseDate(DateTimeProvider.format(movieEntity.getReleaseAt()))
                .censorBoardRating(toCensorBoardRating(movieEntity.getCensorBoardRating()))
                .criticsRating(movieEntity.getCriticsRating())
                .status(toStatus(movieEntity.getStatus()));
        movieEntity.getArtists().forEach(movieArtistEntity -> {
            movieSummaryType.addArtistsItem(ArtistTransformer.toArtistType(movieArtistEntity.getArtist()));
        });
        return movieSummaryType;
    }


    private static MovieStatusType toStatus(final String status) {
        return MovieStatusType.valueOf(status);
    }

    private static CensorBoardRatingType toCensorBoardRating(final String censorBoardRating) {
        return CensorBoardRatingType.valueOf(censorBoardRating);
    }

    private static List<String> toGenres(final String genres) {
        final List<String> splitGenreList = new ArrayList<>();
        final String[] splitGenres = genres.split(",");
        if(splitGenres != null && splitGenres.length > 0) {
            for (String genre : splitGenres) {
                splitGenreList.add(genre);
            }
        }
        return splitGenreList;
    }

}
