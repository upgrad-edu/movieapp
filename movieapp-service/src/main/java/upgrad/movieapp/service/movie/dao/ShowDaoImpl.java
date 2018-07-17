package upgrad.movieapp.service.movie.dao;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import upgrad.movieapp.service.common.dao.BaseDaoImpl;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.GenreEntity_;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.entity.MovieEntity_;
import upgrad.movieapp.service.movie.entity.MovieGenreEntity;
import upgrad.movieapp.service.movie.entity.MovieGenreEntity_;
import upgrad.movieapp.service.movie.entity.ShowEntity;
import upgrad.movieapp.service.movie.entity.ShowEntity_;
import upgrad.movieapp.service.movie.entity.TheatreEntity;
import upgrad.movieapp.service.movie.entity.TheatreEntity_;
import upgrad.movieapp.service.movie.model.ShowSearchQuery;

@Repository
public class ShowDaoImpl extends BaseDaoImpl<ShowEntity> implements ShowDao {

    @Override
    public SearchResult<ShowEntity> findShows(final ShowSearchQuery searchQuery) {

        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<ShowEntity> payloadQuery = builder.createQuery(ShowEntity.class);
        final Root<ShowEntity> from = payloadQuery.from(ShowEntity.class);

        final Predicate[] payloadPredicates = buildPredicates(searchQuery, builder, from);

        payloadQuery.select(from).where(payloadPredicates);
        payloadQuery.orderBy(builder.asc(from.get(ShowEntity_.startTime)));

        final List<ShowEntity> payload = entityManager.createQuery(payloadQuery)
                .setFirstResult(getOffset(searchQuery.getPage(), searchQuery.getLimit()))
                .setMaxResults(searchQuery.getLimit())
                .getResultList();

        final CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        final Root<ShowEntity> countFrom = countQuery.from(ShowEntity.class);

        final Predicate[] countPredicates = buildPredicates(searchQuery, builder, countFrom);
        countQuery.select(builder.count(countFrom)).where(countPredicates);
        final Integer totalCount = entityManager.createQuery(countQuery).getSingleResult().intValue();

        return new SearchResult(totalCount, payload);
    }

    @Override
    public Integer countConflictingShows(final String movieUuid, final String theatreUuid, final ZonedDateTime startTime, final ZonedDateTime endTime) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        final CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        final Root<ShowEntity> countFrom = countQuery.from(ShowEntity.class);

        final List<Predicate> countPredicates = new ArrayList<>();
        countPredicates.add(builder.equal(countFrom.join(ShowEntity_.movie).get(MovieEntity_.uuid), movieUuid));
        countPredicates.add(builder.equal(countFrom.join(ShowEntity_.theatre).get(TheatreEntity_.uuid), theatreUuid));

        final Expression<ZonedDateTime> startTimeExpression = countFrom.get(ShowEntity_.startTime);
        final Expression<ZonedDateTime> endTimeExpression = countFrom.get(ShowEntity_.endTime);

//        countPredicates.add(
//                builder.or(builder.between(startTimeExpression, startTime, endTime),
//                        builder.between(endTimeExpression, startTime, endTime),
//                        builder.and(
//                                builder.greaterThanOrEqualTo(startTimeExpression, startTime),
//                                builder.lessThanOrEqualTo(endTimeExpression, endTime))));

        countPredicates.add(
                builder.or(builder.between(startTimeExpression, startTime, endTime),
                        builder.between(endTimeExpression, startTime, endTime)));

        countQuery.select(builder.count(countFrom)).where(countPredicates.toArray(new Predicate[]{}));
        return entityManager.createQuery(countQuery).getSingleResult().intValue();
    }

    @Override
    public void updateTicketsAvailability(final ShowEntity showEntity) {
        entityManager.lock(showEntity, LockModeType.PESSIMISTIC_WRITE);
        entityManager.merge(showEntity);
    }

    private Predicate[] buildPredicates(final ShowSearchQuery searchQuery, final CriteriaBuilder builder, final Root<ShowEntity> root) {

        final List<Predicate> predicates = new ArrayList<>();

        final Join<ShowEntity, MovieEntity> movieEntityJoin = root.join(ShowEntity_.movie);

        if (StringUtils.isNotEmpty(searchQuery.getMovieUuid())) {
            predicates.add(builder.equal(movieEntityJoin.get(MovieEntity_.uuid), searchQuery.getMovieUuid()));
        }

        if (StringUtils.isNotEmpty(searchQuery.getMovieTitle())) {
            predicates.add(builder.like(builder.lower(movieEntityJoin.get(MovieEntity_.title)), like(searchQuery.getMovieTitle().toLowerCase())));
        }

        final Set<String> genres = searchQuery.getGenres();
        if (CollectionUtils.isNotEmpty(genres)) {
            final ListJoin<MovieEntity, MovieGenreEntity> join = movieEntityJoin.join(MovieEntity_.genres);

            Set<String> genresLowerCase = genres.stream().map(genre -> {
                return genre.toLowerCase();
            }).collect(Collectors.toSet());
            predicates.add(builder.lower(join.get(MovieGenreEntity_.genre).get(GenreEntity_.genre)).in(genresLowerCase));
        }

        if (searchQuery.getTicketAvailability() != null) {
            final Expression<Integer> availableSeats = root.get(ShowEntity_.AVAILABLE_SEATS);
            predicates.add(builder.greaterThanOrEqualTo(availableSeats, searchQuery.getTicketAvailability()));
        }

        if (StringUtils.isNotEmpty(searchQuery.getCityCode())) {
            final Join<ShowEntity, TheatreEntity> theatreEntityJoin = root.join(ShowEntity_.theatre);
            predicates.add(builder.equal(theatreEntityJoin.get(TheatreEntity_.cityCode), searchQuery.getCityCode()));
        }

        if (StringUtils.isNotEmpty(searchQuery.getLanguage())) {
            predicates.add(builder.equal(root.get(ShowEntity_.language), searchQuery.getLanguage()));
        }

        if (searchQuery.getShowTimingStart() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(ShowEntity_.startTime), searchQuery.getShowTimingStart()));
        }

        if (searchQuery.getShowTimingEnd() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get(ShowEntity_.startTime), searchQuery.getShowTimingEnd()));
        }

        if (searchQuery.getMinPrice() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(ShowEntity_.unitPrice), searchQuery.getMinPrice()));
        }

        if (searchQuery.getMaxPrice() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get(ShowEntity_.unitPrice), searchQuery.getMaxPrice()));
        }

        if(searchQuery.getActive() != null) {
            predicates.add(builder.equal(root.get(ShowEntity_.active), searchQuery.getActive().booleanValue()));
        }

        return predicates.toArray(new Predicate[]{});
    }

}
