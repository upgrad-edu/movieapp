package upgrad.movieapp.api.controller.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.util.CollectionUtils;
import upgrad.movieapp.api.model.CensorBoardRatingType;
import upgrad.movieapp.api.model.CreateMovieRequest;
import upgrad.movieapp.api.model.CreateMovieResponse;
import upgrad.movieapp.api.model.MovieStatusType;
import upgrad.movieapp.api.model.MovieSummaryType;
import upgrad.movieapp.api.model.MoviesSummaryResponse;
import upgrad.movieapp.api.model.UpdateMovieRequest;
import upgrad.movieapp.service.common.data.DateTimeProvider;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.MovieEntity;

public final class MovieTransformer {

    public static MoviesSummaryResponse toMoviesSummaryResponse(final int page, final int limit, final SearchResult<MovieEntity> searchResult) {

        final MoviesSummaryResponse moviesSummaryResponse = new MoviesSummaryResponse().totalCount(searchResult.getTotalCount()).page(page).limit(limit);
        for (MovieEntity movieEntity : searchResult.getPayload()) {
            moviesSummaryResponse.addMoviesItem(toMovieSummary(movieEntity));
        }

        return moviesSummaryResponse;
    }

    public static MovieSummaryType toMovieSummary(MovieEntity movieEntity) {
        final MovieSummaryType movieSummaryType = new MovieSummaryType().id(UUID.fromString(movieEntity.getUuid()))
                .title(movieEntity.getTitle())
                .genres(toGenres(movieEntity))
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

    public static MovieEntity toEntity(CreateMovieRequest movieRequest) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setTitle(movieRequest.getTitle());

        addGenres(movieEntity, movieRequest.getGenres());

        movieEntity.setStoryline(movieRequest.getStoryline());
        movieEntity.setPosterUrl(movieRequest.getPosterUrl());
        movieEntity.setTrailerUrl(movieRequest.getTrailerUrl());
        movieEntity.setWikiUrl(movieRequest.getWikiUrl());
        if (movieRequest.getCensorBoardRating() != null) {
            movieEntity.setCensorBoardRating(movieRequest.getCensorBoardRating().name());
        }
        movieEntity.setCriticsRating(movieRequest.getCriticsRating());
        movieEntity.setDuration(movieRequest.getDuration());
        if (movieRequest.getReleaseDate() != null) {
            movieEntity.setReleaseAt(DateTimeProvider.parse(movieRequest.getReleaseDate()));
        }

        addArtists(movieEntity, movieRequest.getArtists());

        return movieEntity;
    }

    public static MovieEntity toEntity(UpdateMovieRequest movieRequest) {

        final MovieEntity movieEntity = new MovieEntity();
        movieEntity.setTitle(movieRequest.getTitle());

        addGenres(movieEntity, movieRequest.getGenres());

        movieEntity.setStoryline(movieRequest.getStoryline());
        movieEntity.setPosterUrl(movieRequest.getPosterUrl());
        movieEntity.setTrailerUrl(movieRequest.getTrailerUrl());
        movieEntity.setWikiUrl(movieRequest.getWikiUrl());
        if (movieRequest.getCensorBoardRating() != null) {
            movieEntity.setCensorBoardRating(movieRequest.getCensorBoardRating().name());
        }
        movieEntity.setCriticsRating(movieRequest.getCriticsRating());
        movieEntity.setDuration(movieRequest.getDuration());
        if (movieRequest.getReleaseDate() != null) {
            movieEntity.setReleaseAt(DateTimeProvider.parse(movieRequest.getReleaseDate()));
        }

        if (movieRequest.getStatus() != null) {
            movieEntity.setStatus(movieRequest.getStatus().name());
        }

        addArtists(movieEntity, movieRequest.getArtists());

        return movieEntity;
    }

    public static CreateMovieResponse toCreateMovieResponse(MovieEntity movieEntity) {
        return new CreateMovieResponse().id(UUID.fromString(movieEntity.getUuid())).status(toStatus(movieEntity.getStatus()));
    }

    private static MovieStatusType toStatus(final String status) {
        return MovieStatusType.valueOf(status);
    }

    private static CensorBoardRatingType toCensorBoardRating(final String censorBoardRating) {
        return CensorBoardRatingType.valueOf(censorBoardRating);
    }

    private static List<String> toGenres(final MovieEntity movieEntity) {
        final List<String> genres = new ArrayList<>();
        movieEntity.getGenres().forEach(movieGenreEntity -> {
            genres.add(movieGenreEntity.getGenre().getGenre());
        });
        return genres;
    }

    private static void addGenres(final MovieEntity movieEntity, final List<String> genres) {
        if (!CollectionUtils.isEmpty(genres)) {
            genres.forEach(genreUuid -> {
                movieEntity.addGenreUuid(genreUuid);
            });
        }
    }

    private static void addArtists(final MovieEntity movieEntity, final List<String> artists) {
        if (!CollectionUtils.isEmpty(artists)) {
            artists.forEach(artistUuid -> {
                movieEntity.addArtistUuid(artistUuid);
            });
        }
    }

}
