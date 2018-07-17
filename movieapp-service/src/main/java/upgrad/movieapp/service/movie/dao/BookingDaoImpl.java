package upgrad.movieapp.service.movie.dao;

import static upgrad.movieapp.service.movie.entity.BookingTicketEntity.BY_SHOW_AND_TICKET_NUMBERS;
import static upgrad.movieapp.service.movie.entity.ShowBookingEntity.BY_BOOKING_REF;
import static upgrad.movieapp.service.movie.entity.ShowBookingEntity.BY_CUSTOMER_AND_UUID;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import upgrad.movieapp.service.common.dao.BaseDaoImpl;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.ShowBookingEntity;
import upgrad.movieapp.service.movie.entity.ShowBookingEntity_;
import upgrad.movieapp.service.movie.model.BookingSearchQuery;
import upgrad.movieapp.service.movie.model.BookingStatus;
import upgrad.movieapp.service.user.entity.UserEntity_;

@Repository
public class BookingDaoImpl extends BaseDaoImpl<ShowBookingEntity> implements BookingDao {

    @Override
    public ShowBookingEntity findByCustomer(final String customerUuid, final String bookingUuid) {
        try {
            return entityManager.createNamedQuery(BY_CUSTOMER_AND_UUID, ShowBookingEntity.class)
                    .setParameter("bookingUuid", bookingUuid)
                    .setParameter("customerUuid", customerUuid)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public ShowBookingEntity findByBookingReference(final String bookingRef) {
        try {
            return entityManager.createNamedQuery(BY_BOOKING_REF, ShowBookingEntity.class)
                    .setParameter(ShowBookingEntity_.bookingReference.getName(), bookingRef)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public SearchResult<ShowBookingEntity> findBookings(final BookingSearchQuery searchQuery) {

        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<ShowBookingEntity> payloadQuery = builder.createQuery(ShowBookingEntity.class);
        final Root<ShowBookingEntity> from = payloadQuery.from(ShowBookingEntity.class);

        final Predicate[] payloadPredicates = buildPredicates(searchQuery, builder, from);

        payloadQuery.select(from).where(payloadPredicates);
        payloadQuery.orderBy(builder.desc(from.get(ShowBookingEntity_.bookingAt)));

        final List<ShowBookingEntity> payload = entityManager.createQuery(payloadQuery)
                .setFirstResult(getOffset(searchQuery.getPage(), searchQuery.getLimit()))
                .setMaxResults(searchQuery.getLimit())
                .getResultList();

        final CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        final Root<ShowBookingEntity> countFrom = countQuery.from(ShowBookingEntity.class);

        final Predicate[] countPredicates = buildPredicates(searchQuery, builder, countFrom);
        countQuery.select(builder.count(countFrom)).where(countPredicates);
        final Integer totalCount = entityManager.createQuery(countQuery).getSingleResult().intValue();

        return new SearchResult(totalCount, payload);
    }

    @Override
    public List<String> findBookedTickets(final String showUuid, final Set<String> ticketNumbers) {
        return entityManager.createNamedQuery(BY_SHOW_AND_TICKET_NUMBERS, String.class)
                .setParameter("showUuid", showUuid)
                .setParameter("ticketNumbers", ticketNumbers)
                .getResultList();
    }

    private Predicate[] buildPredicates(final BookingSearchQuery searchQuery, final CriteriaBuilder builder, final Root<ShowBookingEntity> root) {

        final List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotEmpty(searchQuery.getCustomerUuid())) {
            predicates.add(builder.equal(root.join(ShowBookingEntity_.customer).get(UserEntity_.uuid), searchQuery.getCustomerUuid()));
        }

        if (StringUtils.isNotEmpty(searchQuery.getBookingReference())) {
            predicates.add(builder.like(builder.lower(root.get(ShowBookingEntity_.bookingReference)), like(searchQuery.getBookingReference().toLowerCase())));
        }

        if (CollectionUtils.isNotEmpty(searchQuery.getStatuses())) {
            final EnumSet<BookingStatus> bookingStatuses = searchQuery.getStatuses();
            final Set<String> statuses = bookingStatuses.stream().map(bookingStatus -> {
                return bookingStatus.name();
            }).collect(Collectors.toSet());

            predicates.add(root.get(ShowBookingEntity_.status).in(statuses));
        }
        return predicates.toArray(new Predicate[]{});
    }

}
