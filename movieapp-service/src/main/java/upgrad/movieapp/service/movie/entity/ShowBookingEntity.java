
package upgrad.movieapp.service.movie.entity;

import static upgrad.movieapp.service.common.entity.Entity.SCHEMA;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import upgrad.movieapp.service.common.entity.Identifier;
import upgrad.movieapp.service.common.entity.MutableEntity;
import upgrad.movieapp.service.common.entity.UniversalUniqueIdentifier;
import upgrad.movieapp.service.common.entity.ext.EntityEqualsBuilder;
import upgrad.movieapp.service.common.entity.ext.EntityHashCodeBuilder;
import upgrad.movieapp.service.user.entity.UserEntity;

@Entity
@Table(name = "SHOW_BOOKINGS", schema = SCHEMA)
@NamedQueries({
        @NamedQuery(name = ShowBookingEntity.BY_BOOKING_REF, query = "SELECT sb FROM ShowBookingEntity sb WHERE sb.bookingReference = :bookingReference"),
        @NamedQuery(name = ShowBookingEntity.BY_CUSTOMER_AND_UUID, query = "SELECT sb FROM ShowBookingEntity sb WHERE sb.uuid = :bookingUuid AND sb.customer.uuid = :customerUuid")
})
public class ShowBookingEntity extends MutableEntity implements Identifier<Long>, UniversalUniqueIdentifier<String>, Serializable {

    public static final String BY_BOOKING_REF = "ShowBookingEntity.byBookingReference";
    public static final String BY_CUSTOMER_AND_UUID = "ShowBookingEntity.byCustomerAndUuid";

    private static final long serialVersionUID = 7932286494207403090L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "UUID")
    @Size(max = 36)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private UserEntity customer;

    @ManyToOne
    @JoinColumn(name = "SHOW_ID")
    private ShowEntity show;

    @Column(name = "BOOKING_REF")
    @Size(max = 30)
    @NotNull
    private String bookingReference;

    @Column(name = "COUPON_CODE")
    @Size(max = 10)
    private String couponCode;

    @Column(name = "TOTAL_SEATS")
    @Digits(integer = 3, fraction = 0)
    @NotNull
    private Integer totalSeats;

    @Column(name = "TOTAL_PRICE")
    @Digits(integer = 8, fraction = 2)
    @NotNull
    private BigDecimal totalPrice;

    @Column(name = "BOOKING_AT")
    @NotNull
    private ZonedDateTime bookingAt;

    @Column(name = "CANCELLED_AT")
    private ZonedDateTime cancelledAt;

    @Column(name = "STATUS")
    @Size(max = 30)
    @NotNull
    private String status;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "BOOKING_ID")
    private List<BookingTicketEntity> bookingTickets = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public UserEntity getCustomer() {
        return customer;
    }

    public void setCustomer(UserEntity customer) {
        this.customer = customer;
    }

    public ShowEntity getShow() {
        return show;
    }

    public void setShow(ShowEntity show) {
        this.show = show;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ZonedDateTime getBookingAt() {
        return bookingAt;
    }

    public void setBookingAt(ZonedDateTime bookingAt) {
        this.bookingAt = bookingAt;
    }

    public ZonedDateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(ZonedDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public List<BookingTicketEntity> getBookingTickets() {
        return bookingTickets;
    }

    public void setBookingTickets(List<BookingTicketEntity> bookingTickets) {
        this.bookingTickets = bookingTickets;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @PrePersist
    public void prePersist() {
        this.uuid = UUID.randomUUID().toString();
        super.prePersist();
    }

}
