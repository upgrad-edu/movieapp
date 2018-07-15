package upgrad.movieapp.service.movie.business;

import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.TheatreEntity;
import upgrad.movieapp.service.movie.model.TheatreSearchQuery;

public interface TheatreService {

    SearchResult<TheatreEntity> findTheatres(@NotNull TheatreSearchQuery searchQuery);

    TheatreEntity findTheatreByUuid(@NotNull String theatreUuid) throws ApplicationException;

    TheatreEntity createTheatre(@NotNull TheatreEntity newTheatre) throws ApplicationException;

    void updateTheatre(@NotNull String theatreUuid, @NotNull TheatreEntity updatedTheatre) throws ApplicationException;

    void deleteTheatre(@NotNull String theatreUuid) throws ApplicationException;

}
