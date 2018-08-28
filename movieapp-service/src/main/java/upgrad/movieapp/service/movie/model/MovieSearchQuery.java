package upgrad.movieapp.service.movie.model;

import java.time.ZonedDateTime;
import java.util.EnumSet;
import java.util.Set;

import upgrad.movieapp.service.common.model.SearchQuery;

public class MovieSearchQuery extends SearchQuery {

    private String title;
    private EnumSet<MovieStatus> statuses;
    private Set<String> genres;
    private Set<String> artists;
    private Float ratingMin;
    private Float ratingMax;
    private ZonedDateTime releaseDateFrom;
    private ZonedDateTime releaseDateTo;
    private Set<MovieSortBy> sortBy;

    public MovieSearchQuery(final int page, final int limit) {
        super(page, limit);
    }

    public String getTitle() {
        return title;
    }

    public MovieSearchQuery title(String title) {
        this.title = title;
        return this;
    }

    public EnumSet<MovieStatus> getStatuses() {
        return statuses;
    }

    public MovieSearchQuery statuses(EnumSet<MovieStatus> statuses) {
        this.statuses = statuses;
        return this;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public MovieSearchQuery genres(Set<String> genres) {
        this.genres = genres;
        return this;
    }

    public Set<String> getArtists() {
        return artists;
    }

    public MovieSearchQuery artists(Set<String> artists) {
        this.artists = artists;
        return this;
    }

    public Float getRatingMin() {
        return ratingMin;
    }

    public MovieSearchQuery ratingMin(Float ratingMin) {
        this.ratingMin = ratingMin;
        return this;
    }

    public Float getRatingMax() {
        return ratingMax;
    }

    public MovieSearchQuery ratingMax(Float ratingMax) {
        this.ratingMax = ratingMax;
        return this;
    }

    public ZonedDateTime getReleaseDateFrom() {
        return releaseDateFrom;
    }

    public MovieSearchQuery releaseDateFrom(ZonedDateTime releaseDateFrom) {
        this.releaseDateFrom = releaseDateFrom;
        return this;
    }

    public ZonedDateTime getReleaseDateTo() {
        return releaseDateTo;
    }

    public MovieSearchQuery releaseDateTo(ZonedDateTime releaseDateTo) {
        this.releaseDateTo = releaseDateTo;
        return this;
    }

    public Set<MovieSortBy> getSortBy() {
        return sortBy;
    }

    public MovieSearchQuery sortBy(Set<MovieSortBy> sortBy) {
        this.sortBy = sortBy;
        return this;
    }

}
