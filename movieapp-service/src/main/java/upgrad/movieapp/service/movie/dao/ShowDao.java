package upgrad.movieapp.service.movie.dao;

import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.dao.BaseDao;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.ShowEntity;
import upgrad.movieapp.service.movie.model.ShowSearchQuery;

public interface ShowDao extends BaseDao<ShowEntity> {

    SearchResult<ShowEntity> findShows(@NotNull ShowSearchQuery searchQuery);

    Integer countConflictingShows(@NotNull String movieUuid, @NotNull String theatreUuid, @NotNull ZonedDateTime startTime, @NotNull ZonedDateTime endTime);

    void updateTicketsAvailability(@NotNull ShowEntity showEntity);

}
