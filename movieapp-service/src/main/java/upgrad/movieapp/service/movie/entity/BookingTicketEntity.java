
package upgrad.movieapp.service.movie.entity;

import static upgrad.movieapp.service.common.entity.Entity.SCHEMA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import upgrad.movieapp.service.common.entity.Identifier;
import upgrad.movieapp.service.common.entity.ImmutableEntity;
import upgrad.movieapp.service.common.entity.ext.EntityEqualsBuilder;
import upgrad.movieapp.service.common.entity.ext.EntityHashCodeBuilder;

@Entity
@Table(name = "BOOKING_TICKETS", schema = SCHEMA)
@NamedQueries({
        @NamedQuery(name = BookingTicketEntity.BY_SHOW_AND_TICKET_NUMBERS, query = "SELECT bt.ticketNumber FROM BookingTicketEntity bt WHERE bt.ticketNumber IN (:ticketNumbers) " +
                " AND bt.booking.show.uuid = :showUuid AND bt.booking.status = 'CONFIRMED'")
})
public class BookingTicketEntity extends ImmutableEntity implements Identifier<Long>, Serializable {

    public static final String BY_SHOW_AND_TICKET_NUMBERS = "BookingTicketEntity.byShowAndTicketNumbers";

    private static final long serialVersionUID = 7932286494206403090L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "BOOKING_ID")
    private ShowBookingEntity booking;

    @Column(name = "TICKET_NUMBER")
    @Size(max = 3)
    private String ticketNumber;

    @Override
    public Long getId() {
        return id;
    }

    public ShowBookingEntity getBooking() {
        return booking;
    }

    public void setBooking(ShowBookingEntity booking) {
        this.booking = booking;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    @Override
    public boolean equals(Object obj) {
        return new EntityEqualsBuilder<Long>().equalsById(this, obj);
    }

    @Override
    public int hashCode() {
        return new EntityHashCodeBuilder<Long>().hashCodeById(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
