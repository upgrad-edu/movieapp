package upgrad.movieapp.service.movie.business;

import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.ShowEntity;
import upgrad.movieapp.service.movie.model.ShowSearchQuery;

public interface ShowService {

    ShowEntity createShow(@NotNull String movieUuid, @NotNull String theatreUuid, @NotNull ShowEntity newShow) throws ApplicationException;

    void updateShow(@NotNull String showUuid, String updatedTheatreUuid, @NotNull ShowEntity newShow) throws ApplicationException;

    void deactivateShow(@NotNull String movieUuid, @NotNull String showUuid) throws ApplicationException;

    SearchResult<ShowEntity> findShows(@NotNull ShowSearchQuery searchQuery);

    ShowEntity findShowByUuid(@NotNull String movieUuid, @NotNull String showUuid) throws ApplicationException;

    ShowEntity findShowByUuid(@NotNull String showUuid) throws ApplicationException;

    void updateTicketsAvailability(@NotNull ShowEntity bookedShow, @NotNull Integer totalBookedTickets);

    void revertTicketsAvailability(@NotNull ShowEntity bookedShow, @NotNull Integer totalBookedTickets);

}
