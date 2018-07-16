package upgrad.movieapp.api.controller.transformer;

import static upgrad.movieapp.api.model.MovieShowStatusType.ACTIVE;
import static upgrad.movieapp.api.model.MovieShowStatusType.INACTIVE;
import static upgrad.movieapp.service.common.data.DateTimeProvider.formatDateTime;
import static upgrad.movieapp.service.common.data.DateTimeProvider.parseDateTime;
import static upgrad.movieapp.service.movie.exception.ShowErrorCode.SHW_002;
import static upgrad.movieapp.service.movie.exception.ShowErrorCode.SHW_004;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import upgrad.movieapp.api.exception.RestException;
import upgrad.movieapp.api.model.CreateMovieShowRequest;
import upgrad.movieapp.api.model.CreateMovieShowResponse;
import upgrad.movieapp.api.model.LanguageType;
import upgrad.movieapp.api.model.MovieShowStatusType;
import upgrad.movieapp.api.model.MovieShowType;
import upgrad.movieapp.api.model.MovieShowsSummaryResponse;
import upgrad.movieapp.api.model.UpdateMovieShowRequest;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.ShowEntity;
import upgrad.movieapp.service.movie.model.ShowSearchQuery;

public final class ShowTransformer {

    private ShowTransformer() {
    }

    public static ShowSearchQuery toSearchQuery(final int page, final int limit, final String movieTitle, final String genre, final Integer ticketAvailability,
                                                final String cityCode, final String language, final String showTimingStart, final String showTimingEnd,
                                                final BigDecimal minPrice, final BigDecimal maxPrice, final String status) {

        final ShowSearchQuery searchQuery = new ShowSearchQuery(page, limit);

        if (StringUtils.isNotEmpty(movieTitle)) {
            searchQuery.movieTitle(movieTitle);
        }
        if (StringUtils.isNotEmpty(genre)) {
            final String[] genres = StringUtils.split(genre, ",");
            searchQuery.genres(new HashSet<>(Arrays.asList(genres)));
        }

        searchQuery.ticketAvailability(ticketAvailability);
        searchQuery.cityCode(cityCode);
        if (StringUtils.isNotEmpty(language)) {
            searchQuery.language(toLanguage(language.toUpperCase()).name());
        }

        if (StringUtils.isNotEmpty(showTimingStart)) {
            searchQuery.showTimingStart(parseDateTime(showTimingStart));
        }
        if (StringUtils.isNotEmpty(showTimingEnd)) {
            searchQuery.showTimingEnd(parseDateTime(showTimingEnd));
        }

        if (minPrice != null) {
            searchQuery.minPrice(minPrice);
        }
        if (maxPrice != null) {
            searchQuery.maxPrice(maxPrice);
        }
        if (StringUtils.isNotEmpty(status)) {
            searchQuery.active(ACTIVE == toStatus(status));
        }

        return searchQuery;
    }

    public static MovieShowsSummaryResponse toMovieShowsSummaryResponse(final int page, final int limit, final SearchResult<ShowEntity> searchResult) {
        final MovieShowsSummaryResponse movieShowsSummaryResponse = new MovieShowsSummaryResponse().page(page).limit(limit)
                .totalCount(searchResult.getTotalCount());
        searchResult.getPayload().forEach(showEntity -> {
            movieShowsSummaryResponse.addShowsItem(toMovieShowType(showEntity));
        });
        return movieShowsSummaryResponse;
    }

    public static MovieShowType toMovieShowType(final ShowEntity showEntity) {
        return new MovieShowType().id(UUID.fromString(showEntity.getUuid()))
                .showTiming(formatDateTime(showEntity.getStartTime()))
                .language(toLanguage(showEntity.getLanguage()))
                .theatre(TheatreTransformer.toTheatreSummary(showEntity.getTheatre()))
                .totalSeats(showEntity.getTotalSeats())
                .availableSeats(showEntity.getAvailableSeats())
                .unitPrice(showEntity.getUnitPrice())
                .status(toEnum(showEntity.isActive()));
    }

    public static ShowEntity toEntity(CreateMovieShowRequest request) {
        final ShowEntity showEntity = new ShowEntity();
        showEntity.setStartTime(parseDateTime(request.getShowTiming()));
        showEntity.setLanguage(request.getLanguage().name());
        showEntity.setTotalSeats(request.getTotalSeats());
        showEntity.setUnitPrice(request.getUnitPrice());
        return showEntity;
    }

    public static ShowEntity toEntity(UpdateMovieShowRequest request) {
        final ShowEntity showEntity = new ShowEntity();
        if (request.getShowTiming() != null) {
            showEntity.setStartTime(parseDateTime(request.getShowTiming()));
        }
        if (request.getLanguage() != null) {
            showEntity.setLanguage(request.getLanguage().name());
        }
        showEntity.setTotalSeats(request.getTotalSeats());
        showEntity.setUnitPrice(request.getUnitPrice());
        return showEntity;
    }

    public static CreateMovieShowResponse toCreateMovieShowResponse(ShowEntity showEntity) {
        return new CreateMovieShowResponse().id(UUID.fromString(showEntity.getUuid())).status(toEnum(showEntity.isActive()));
    }


    private static LanguageType toLanguage(final String language) {
        try {
            return LanguageType.valueOf(language);
        } catch (IllegalArgumentException exc) {
            throw new RestException(SHW_004, language, StringUtils.join(LanguageType.values(), ","));
        }
    }

    private static MovieShowStatusType toEnum(final boolean active) {
        return active ? ACTIVE : INACTIVE;
    }

    private static MovieShowStatusType toStatus(final String status) {
        try {
            return MovieShowStatusType.valueOf(status);
        } catch (IllegalArgumentException exc) {
            throw new RestException(SHW_002, status, StringUtils.join(MovieShowStatusType.values(), ","));
        }
    }

}
