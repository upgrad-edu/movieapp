package upgrad.movieapp.api.controller.transformer;

import static upgrad.movieapp.service.common.data.DateTimeProvider.formatDate;
import static upgrad.movieapp.service.common.data.DateTimeProvider.parseDate;
import static upgrad.movieapp.service.movie.exception.MovieErrorCode.MVI_002;
import static upgrad.movieapp.service.movie.exception.MovieErrorCode.MVI_006;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import upgrad.movieapp.api.exception.RestException;
import upgrad.movieapp.api.model.CensorBoardRatingType;
import upgrad.movieapp.api.model.CreateMovieRequest;
import upgrad.movieapp.api.model.CreateMovieResponse;
import upgrad.movieapp.api.model.MovieStatusType;
import upgrad.movieapp.api.model.MovieSummaryType;
import upgrad.movieapp.api.model.MoviesSummaryResponse;
import upgrad.movieapp.api.model.SortByType;
import upgrad.movieapp.api.model.UpdateMovieRequest;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.model.MovieSearchQuery;
import upgrad.movieapp.service.movie.model.MovieSortBy;
import upgrad.movieapp.service.movie.model.MovieStatus;

public final class MovieTransformer {

    public static MovieSearchQuery toSearchQuery(final int page, final int limit, final String title, final String status, final String startDate,
                                                 final String endDate, final String genre, final String artists, final Float minRatng, final Float maxRating,
                                                 final String sortBy) {

        final MovieSearchQuery searchQuery = new MovieSearchQuery(page, limit);
        if (StringUtils.isNotEmpty(title)) {
            searchQuery.title(title);
        }
        if (StringUtils.isNotEmpty(status)) {
            final EnumSet<MovieStatus> movieStatuses = EnumSet.noneOf(MovieStatus.class);
            final String[] statuses = StringUtils.split(status, ",");
            for (String splitStatus : statuses) {
                movieStatuses.add(toMovieStatus(splitStatus.toUpperCase()));
            }
            searchQuery.statuses(movieStatuses);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            searchQuery.releaseDateFrom(parseDate(startDate));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            searchQuery.releaseDateTo(parseDate(endDate));
        }
        if (StringUtils.isNotEmpty(genre)) {
            final String[] genres = StringUtils.split(genre, ",");
            searchQuery.genres(new HashSet<>(Arrays.asList(genres)));
        }
        if(StringUtils.isNotEmpty(artists)) {
            final String[] artistNames = StringUtils.split(artists, ",");
            final Set<String> artistSet = new HashSet<>();
            for (String artistName: artistNames) {
                artistSet.add(artistName);
            }
            searchQuery.artists(artistSet);
        }
        if (minRatng != null) {
            searchQuery.ratingMin(minRatng);
        }
        if (maxRating != null) {
            searchQuery.ratingMax(maxRating);
        }
        if (StringUtils.isNotEmpty(sortBy)) {
            final Set<MovieSortBy> movieSortBy = new HashSet<>();
            final String[] sortByFields = StringUtils.split(sortBy, ",");
            for (String sortByField : sortByFields) {
                movieSortBy.add(toMovieSortBy(sortByField.toUpperCase()));
            }
            searchQuery.sortBy(movieSortBy);
        }

        return searchQuery;
    }

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
                .releaseDate(formatDate(movieEntity.getReleaseAt()))
                .censorBoardRating(toCensorBoardRating(movieEntity.getCensorBoardRating()))
                .rating(movieEntity.getRating())
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
        movieEntity.setRating(movieRequest.getRating());
        movieEntity.setDuration(movieRequest.getDuration());
        if (movieRequest.getReleaseDate() != null) {
            movieEntity.setReleaseAt(parseDate(movieRequest.getReleaseDate()));
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
        movieEntity.setRating(movieRequest.getRating());
        movieEntity.setDuration(movieRequest.getDuration());
        if (movieRequest.getReleaseDate() != null) {
            movieEntity.setReleaseAt(parseDate(movieRequest.getReleaseDate()));
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

    private static MovieStatus toMovieStatus(final String status) {
        try {
            return MovieStatus.valueOf(status);
        } catch (IllegalArgumentException exc) {
            throw new RestException(MVI_002, status, StringUtils.join(MovieStatus.values(), ","));
        }
    }

    private static MovieSortBy toMovieSortBy(final String sortByField) {
        try {
            return MovieSortBy.valueOf(SortByType.valueOf(sortByField).name());
        } catch (IllegalArgumentException exc) {
            throw new RestException(MVI_006, StringUtils.join(SortByType.values(), ","));
        }
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
