package upgrad.movieapp.service.movie.entity;

import static upgrad.movieapp.service.common.entity.Entity.SCHEMA;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "MOVIES", schema = SCHEMA)
public class MovieEntity extends MutableEntity implements Identifier<Integer>, UniversalUniqueIdentifier<Integer>, Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID")
    @NotNull
    private Integer uuid;

    @Column(name = "NAME")
    @NotNull
    @Size(max = 100)
    private String title;

    @Column(name = "GENRES")
    @NotNull
    @Size(max = 2000)
    private String genres;

    @Column(name = "STORYLINE")
    @NotNull
    @Size(max = 2000)
    private String storyline;

    @Column(name = "POSTER_URL")
    @NotNull
    @Size(max = 2000)
    private String posterUrl;

    @Column(name = "TRAILER_URL")
    @NotNull
    @Size(max = 2000)
    private String trailerUrl;

    @Column(name = "OFFICIAL_WEBSITE_URL")
    @Size(max = 2000)
    private String offocialWebsiteUrl;

    @Column(name = "WIKI_URL")
    @Size(max = 2000)
    private String wikiUrl;

    @Column(name = "RELEASE_AT")
    private ZonedDateTime releaseAt;

    @Column(name = "CENSOR_BOARD_RATING")
    @Size(max = 3)
    private String censorBoardRating;

    @Column(name = "CRITICS_RATING")
    @Size(max = 3)
    private Float criticsRating;

    @Column(name = "USERS_RATING")
    @Size(max = 3)
    private Float usersRating;

    @Column(name = "STATUS")
    @Size(max = 3)
    private String status;

    @OneToMany
    @JoinColumn(name = "MOVIE_ID")
    private List<MovieArtistEntity> artists;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getStoryline() {
        return storyline;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getOffocialWebsiteUrl() {
        return offocialWebsiteUrl;
    }

    public void setOffocialWebsiteUrl(String offocialWebsiteUrl) {
        this.offocialWebsiteUrl = offocialWebsiteUrl;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    public ZonedDateTime getReleaseAt() {
        return releaseAt;
    }

    public void setReleaseAt(ZonedDateTime releaseAt) {
        this.releaseAt = releaseAt;
    }

    public String getCensorBoardRating() {
        return censorBoardRating;
    }

    public void setCensorBoardRating(String censorBoardRating) {
        this.censorBoardRating = censorBoardRating;
    }

    public Float getCriticsRating() {
        return criticsRating;
    }

    public void setCriticsRating(Float criticsRating) {
        this.criticsRating = criticsRating;
    }

    public Float getUsersRating() {
        return usersRating;
    }

    public void setUsersRating(Float usersRating) {
        this.usersRating = usersRating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        return new EntityEqualsBuilder<Integer>().equalsById(this, obj);
    }

    @Override
    public int hashCode() {
        return new EntityHashCodeBuilder<Integer>().hashCodeById(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
