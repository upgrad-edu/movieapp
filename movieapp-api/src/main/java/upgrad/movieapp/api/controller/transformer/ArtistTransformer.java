package upgrad.movieapp.api.controller.transformer;

import java.util.UUID;

import upgrad.movieapp.api.model.ArtistRoleType;
import upgrad.movieapp.api.model.ArtistType;
import upgrad.movieapp.api.model.ArtistsSummaryResponse;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.ArtistEntity;

public final class ArtistTransformer {

    public static ArtistsSummaryResponse toArtistsSummaryResponse(final int page, final int limit, final SearchResult<ArtistEntity> searchResult) {

        final ArtistsSummaryResponse artistsSummaryResponse = new ArtistsSummaryResponse().totalCount(searchResult.getTotalCount()).page(page).limit(limit);
        for(ArtistEntity artistEntity : searchResult.getPayload()) {
            artistsSummaryResponse.addArtistsItem(toArtistType(artistEntity));
        }

        return artistsSummaryResponse;
    }

    public static ArtistType toArtistType(ArtistEntity artistEntity) {
        return new ArtistType().id(UUID.fromString(artistEntity.getUuid()))
                .firstName(artistEntity.getFirstName())
                .lastName(artistEntity.getLastName())
                .roleType(ArtistRoleType.valueOf(artistEntity.getType()))
                .profileDescription(artistEntity.getProfileDescription())
                .profileUrl(artistEntity.getProfilePictureUrl())
                .wikiUrl(artistEntity.getWikiUrl());
    }

}
