package upgrad.movieapp.service.movie.business;

import static upgrad.movieapp.service.common.data.DateTimeProvider.formatDate;
import static upgrad.movieapp.service.common.data.DateTimeProvider.formatTime;

import java.time.ZonedDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.exception.EntityNotFoundException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.dao.ShowDao;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.entity.ShowEntity;
import upgrad.movieapp.service.movie.entity.TheatreEntity;
import upgrad.movieapp.service.movie.exception.ShowErrorCode;
import upgrad.movieapp.service.movie.model.ShowSearchQuery;

@Service
public class ShowServiceImpl implements ShowService {

    private static final int BREAK_DURATION = 15; //in minutes

    @Autowired
    private MovieService movieService;

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private ShowDao showDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ShowEntity createShow(final String movieUuid, final String theatreUuid, final ShowEntity newShow) throws ApplicationException {

        final MovieEntity movieEntity = movieService.findMovieByUuid(movieUuid);
        newShow.setMovie(movieEntity);

        final TheatreEntity theatreEntity = theatreService.findTheatreByUuid(theatreUuid);
        newShow.setTheatre(theatreEntity);

        final ZonedDateTime showStartTime = newShow.getStartTime();
        final ZonedDateTime showEndTime = showStartTime.plusMinutes(movieEntity.getDuration()).plusMinutes(BREAK_DURATION);

        final Integer conflictingShows = showDao.countConflictingShows(movieUuid, theatreUuid, showStartTime, showEndTime);
        if (conflictingShows > 0) {
            throw new ApplicationException(ShowErrorCode.SHW_003, movieEntity.getTitle(), theatreEntity.getName(),
                    formatTime(showStartTime), formatDate(showStartTime));
        }

        newShow.setEndTime(showEndTime);
        newShow.setAvailableSeats(newShow.getTotalSeats());
        newShow.setActive(true);

        return showDao.create(newShow);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateShow(final String showUuid, final String updatedTheatreUuid, final ShowEntity updatedShow) throws ApplicationException {

        final ShowEntity existingShow = findExistingShow(showUuid);

        TheatreEntity theatreEntity = existingShow.getTheatre();
        if (StringUtils.isNotEmpty(updatedTheatreUuid) && !updatedTheatreUuid.equals(theatreEntity.getUuid())) {
            theatreEntity = theatreService.findTheatreByUuid(updatedTheatreUuid);
            existingShow.setTheatre(theatreEntity);
        }

        final MovieEntity movieEntity = existingShow.getMovie();

        final ZonedDateTime showStartTime = updatedShow.getStartTime();
        if (showStartTime != null) {
            final ZonedDateTime showEndTime = showStartTime.plusMinutes(movieEntity.getDuration()).plusMinutes(BREAK_DURATION);
            final Integer conflictingShows = showDao.countConflictingShows(movieEntity.getUuid(), theatreEntity.getUuid(), showStartTime, showEndTime);
            if (conflictingShows > 0) {
                throw new ApplicationException(ShowErrorCode.SHW_003, movieEntity.getTitle(), theatreEntity.getName(),
                        formatTime(showStartTime), formatDate(showStartTime));
            }
            existingShow.setStartTime(showStartTime);
            existingShow.setEndTime(showEndTime);
        }

        if (updatedShow.getTotalSeats() != null) {
            final int diffTotalSeats = updatedShow.getTotalSeats() - existingShow.getTotalSeats();
            existingShow.setTotalSeats(updatedShow.getTotalSeats());
            existingShow.setAvailableSeats(existingShow.getAvailableSeats() + diffTotalSeats);
        }
        if (StringUtils.isNotEmpty(updatedShow.getLanguage())) {
            existingShow.setLanguage(updatedShow.getLanguage());
        }
        if (updatedShow.getUnitPrice() != null) {
            existingShow.setUnitPrice(updatedShow.getUnitPrice());
        }

        existingShow.setActive(true);
        showDao.update(existingShow);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deactivateShow(final String movieUuid, final String showUuid) throws ApplicationException {
        movieService.findMovieByUuid(movieUuid); //to confirm of the movie is valid

        final ShowEntity existingShow = findExistingShow(showUuid);
        existingShow.setActive(false);
        showDao.update(existingShow);
    }

    @Override
    public SearchResult<ShowEntity> findShows(final ShowSearchQuery searchQuery) {
        return showDao.findShows(searchQuery);
    }

    @Override
    public ShowEntity findShowByUuid(final String movieUuid, final String showUuid) throws ApplicationException {
        movieService.findMovieByUuid(movieUuid); //to confirm of the movie is valid
        return findExistingShow(showUuid);
    }

    @Override
    public ShowEntity findShowByUuid(final String showUuid) throws ApplicationException {
        return findExistingShow(showUuid);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateTicketsAvailability(final ShowEntity bookedShow, final Integer totalBookedTickets) {
        bookedShow.setAvailableSeats(bookedShow.getAvailableSeats() - totalBookedTickets);
        showDao.updateTicketsAvailability(bookedShow);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void revertTicketsAvailability(final ShowEntity bookedShow, final Integer totalBookedTickets) {
        bookedShow.setAvailableSeats(bookedShow.getAvailableSeats() + totalBookedTickets);
        showDao.updateTicketsAvailability(bookedShow);
    }

    private ShowEntity findExistingShow(final String showUuid) throws ApplicationException {
        final ShowEntity showEntity = showDao.findByUUID(showUuid);
        if (showEntity == null) {
            throw new EntityNotFoundException(ShowErrorCode.SHW_001, showUuid);
        }
        return showEntity;
    }

}
