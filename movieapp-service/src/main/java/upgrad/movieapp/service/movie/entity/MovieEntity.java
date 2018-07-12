package upgrad.movieapp.service.movie.entity;

import static upgrad.movieapp.service.common.entity.Entity.SCHEMA;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
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
@NamedQueries({
        @NamedQuery(name = MovieEntity.COUNT_BY_ALL, query = "select count(m.id) from MovieEntity m"),
        @NamedQuery(name = MovieEntity.BY_ALL, query = "select m from MovieEntity m"),
        @NamedQuery(name = MovieEntity.COUNT_BY_STATUS, query = "select count(m.id) from MovieEntity m where m.status = :status"),
        @NamedQuery(name = MovieEntity.BY_STATUS, query = "select m from MovieEntity m where m.status = :status")
})
public class MovieEntity extends MutableEntity implements Identifier<Integer>, UniversalUniqueIdentifier<String>, Serializable {

    public static final String COUNT_BY_ALL = "MovieEntity.countByAll";
    public static final String BY_ALL = "MovieEntity.byAll";
    public static final String COUNT_BY_STATUS = "MovieEntity.countByStatus";
    public static final String BY_STATUS = "MovieEntity.byStatus";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID")
    @NotNull
    private String uuid;

    @Column(name = "TITLE")
    @NotNull
    @Size(max = 100)
    private String title;

    @Column(name = "GENRES")
    @NotNull
    @Size(max = 2000)
    private String genres;

    @Column(name = "DURATION")
    @NotNull
    private Integer duration;

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
    private String websiteUrl;

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
    public String getUuid() {
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
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

    public List<MovieArtistEntity> getArtists() {
        return artists;
    }

    public void setArtists(List<MovieArtistEntity> artists) {
        this.artists = artists;
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

    @PrePersist
    public void prePersist() {
        this.uuid = UUID.randomUUID().toString();
        super.prePersist();
    }

}
