package upgrad.movieapp.service.movie.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

import upgrad.movieapp.service.common.model.SearchQuery;

public class ShowSearchQuery extends SearchQuery {

    private String movieUuid;
    private String movieTitle;
    private Set<String> genres;
    private Integer ticketAvailability;
    private String cityCode;
    private String language;
    private ZonedDateTime showTimingStart;
    private ZonedDateTime showTimingEnd;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean active;

    public ShowSearchQuery(final int page, final int limit) {
        super(page, limit);
    }

    public String getMovieUuid() {
        return movieUuid;
    }

    public ShowSearchQuery movieUuid(String movieUuid) {
        this.movieUuid = movieUuid;
        return this;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public ShowSearchQuery movieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
        return this;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public ShowSearchQuery genres(Set<String> genres) {
        this.genres = genres;
        return this;
    }

    public Integer getTicketAvailability() {
        return ticketAvailability;
    }

    public ShowSearchQuery ticketAvailability(Integer ticketAvailability) {
        this.ticketAvailability = ticketAvailability;
        return this;
    }

    public String getCityCode() {
        return cityCode;
    }

    public ShowSearchQuery cityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public ShowSearchQuery language(String language) {
        this.language = language;
        return this;
    }

    public ZonedDateTime getShowTimingStart() {
        return showTimingStart;
    }

    public ShowSearchQuery showTimingStart(ZonedDateTime showTimingStart) {
        this.showTimingStart = showTimingStart;
        return this;
    }

    public ZonedDateTime getShowTimingEnd() {
        return showTimingEnd;
    }

    public ShowSearchQuery showTimingEnd(ZonedDateTime showTimingEnd) {
        this.showTimingEnd = showTimingEnd;
        return this;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public ShowSearchQuery minPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public ShowSearchQuery maxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public ShowSearchQuery active(Boolean active) {
        this.active = active;
        return this;
    }
}
