package upgrad.movieapp.api.controller.transformer;

import static upgrad.movieapp.api.model.TheatreStatusType.ACTIVE;
import static upgrad.movieapp.api.model.TheatreStatusType.INACTIVE;
import static upgrad.movieapp.service.movie.exception.TheatreErrorCode.THR_003;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import upgrad.movieapp.api.exception.RestException;
import upgrad.movieapp.api.model.CreateTheatreRequest;
import upgrad.movieapp.api.model.CreateTheatreResponse;
import upgrad.movieapp.api.model.TheatreStatusType;
import upgrad.movieapp.api.model.TheatreSummaryType;
import upgrad.movieapp.api.model.TheatresSummaryResponse;
import upgrad.movieapp.api.model.UpdateTheatreRequest;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.TheatreEntity;
import upgrad.movieapp.service.movie.model.TheatreSearchQuery;

public final class TheatreTransformer {

    public static TheatreSearchQuery toSearchQuery(final int page, final int limit, final String name, final String cityCode, final String status) {

        final TheatreSearchQuery searchQuery = new TheatreSearchQuery(page, limit);
        if (StringUtils.isNotEmpty(name)) {
            searchQuery.name(name);
        }
        if (StringUtils.isNotEmpty(status)) {
            searchQuery.active(ACTIVE == toStatus(status));
        }
        if (StringUtils.isNotEmpty(cityCode)) {
            searchQuery.cityCode(cityCode);
        }
        return searchQuery;
    }

    public static TheatresSummaryResponse toTheatresSummaryResponse(final int page, final int limit, final SearchResult<TheatreEntity> searchResult) {

        final TheatresSummaryResponse moviesSummaryResponse = new TheatresSummaryResponse().totalCount(searchResult.getTotalCount()).page(page).limit(limit);
        for (TheatreEntity theatreEntity : searchResult.getPayload()) {
            moviesSummaryResponse.addTheatresItem(toTheatreSummary(theatreEntity));
        }

        return moviesSummaryResponse;
    }

    public static TheatreSummaryType toTheatreSummary(TheatreEntity theatreEntity) {
        final TheatreSummaryType theatreSummaryType = new TheatreSummaryType().id(UUID.fromString(theatreEntity.getUuid()))
                .name(theatreEntity.getName())
                .postalAddress(theatreEntity.getPostalAddress())
                .cityCode(theatreEntity.getCityCode())
                .city(theatreEntity.getCity())
                .status(toEnum(theatreEntity.isActive()));
        return theatreSummaryType;
    }

    public static TheatreEntity toEntity(CreateTheatreRequest request) {
        final TheatreEntity entity = new TheatreEntity();
        entity.setName(request.getName());
        entity.setPostalAddress(request.getPostalAddress());
        entity.setCityCode(request.getCityCode());
        return entity;
    }

    public static TheatreEntity toEntity(UpdateTheatreRequest request) {
        final TheatreEntity entity = new TheatreEntity();
        entity.setName(request.getName());
        entity.setPostalAddress(request.getPostalAddress());
        entity.setCityCode(request.getCityCode());
        return entity;
    }

    public static CreateTheatreResponse toCreateTheatreResponse(TheatreEntity entity) {
        return new CreateTheatreResponse().id(UUID.fromString(entity.getUuid())).status(toEnum(entity.isActive()));
    }

    private static TheatreStatusType toStatus(final String status) {
        try {
            return TheatreStatusType.valueOf(status);
        } catch (IllegalArgumentException exc) {
            throw new RestException(THR_003, status, StringUtils.join(TheatreStatusType.values(), ","));
        }
    }

    private static TheatreStatusType toEnum(final boolean active) {
        return active ? ACTIVE : INACTIVE;
    }

}
