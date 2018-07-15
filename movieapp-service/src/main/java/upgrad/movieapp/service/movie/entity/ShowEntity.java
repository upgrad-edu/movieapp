
package upgrad.movieapp.service.movie.entity;

import static upgrad.movieapp.service.common.entity.Entity.SCHEMA;

import java.io.Serializable;
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

@Entity
@Table(name = "SHOWS", schema = SCHEMA)
public class ShowEntity extends MutableEntity implements Identifier<Long>, UniversalUniqueIdentifier<String>, Serializable {

    private static final long serialVersionUID = 7921286494206403090L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "UUID")
    @Size(max = 36)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "MOVIE_ID")
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "THEATRE_ID")
    private TheatreEntity theatre;

    @Column(name = "SHOW_DATETIME")
    private ZonedDateTime timing;

    @Column(name = "LANG")
    @NotNull
    @Size(max = 50)
    private String language;

    @Column(name = "UNIT_PRICE")
    @Digits(integer = 2, fraction = 2)
    private Float unitPrice;

    @Column(name = "TOTAL_SEATS")
    @Digits(integer = 3, fraction = 0)
    private Integer totalSeats;

    @Column(name = "AVAILABLE_SEATS")
    @Digits(integer = 3, fraction = 0)
    private Integer availableSeats;

    @Column(name = "ACTIVE")
    @NotNull
    private boolean active;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "SHOW_ID")
    private List<ShowBookingEntity> bookings = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }

    public TheatreEntity getTheatre() {
        return theatre;
    }

    public void setTheatre(TheatreEntity theatre) {
        this.theatre = theatre;
    }

    public ZonedDateTime getTiming() {
        return timing;
    }

    public void setTiming(ZonedDateTime timing) {
        this.timing = timing;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<ShowBookingEntity> getBookings() {
        return bookings;
    }

    public void setBookings(List<ShowBookingEntity> bookings) {
        this.bookings = bookings;
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
