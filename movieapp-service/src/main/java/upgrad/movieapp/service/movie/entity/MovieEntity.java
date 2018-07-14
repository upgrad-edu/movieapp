package upgrad.movieapp.service.movie.entity;

import static upgrad.movieapp.service.common.entity.Entity.SCHEMA;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@Table(name = "MOVIES", schema = SCHEMA)
public class MovieEntity extends MutableEntity implements Identifier<Integer>, UniversalUniqueIdentifier<String>, Serializable {

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

    @Column(name = "WIKI_URL")
    @Size(max = 2000)
    private String wikiUrl;

    @Column(name = "RELEASE_AT")
    private ZonedDateTime releaseAt;

    @Column(name = "CENSOR_BOARD_RATING")
    @Size(max = 3)
    private String censorBoardRating;

    @Column(name = "RATING")
    @Digits(integer = 2, fraction = 2)
    private Float rating;

    @Column(name = "STATUS")
    @Size(max = 30)
    private String status;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "MOVIE_ID")
    private List<MovieGenreEntity> genres = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "MOVIE_ID")
    private List<MovieArtistEntity> artists = new ArrayList<>();

    @Transient
    private final Set<String> genreUuids = new HashSet<>();

    @Transient
    private final Set<String> artistUuids = new HashSet<>();

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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MovieGenreEntity> getGenres() {
        return genres;
    }

    public void setGenres(List<MovieGenreEntity> genres) {
        this.genres = genres;
    }

    public List<MovieArtistEntity> getArtists() {
        return artists;
    }

    public void setArtists(List<MovieArtistEntity> artists) {
        this.artists = artists;
    }

    public Set<String> getArtistUuids() {
        return artistUuids;
    }

    public Set<String> getGenreUuids() {
        return genreUuids;
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

    public void addGenre(final GenreEntity genreEntity) {
        final MovieGenreEntity movieGenreEntity = new MovieGenreEntity();
        movieGenreEntity.setMovie(this);
        movieGenreEntity.setGenre(genreEntity);
        genres.add(movieGenreEntity);
    }

    public void addArtist(final ArtistEntity artistEntity) {
        final MovieArtistEntity movieArtistEntity = new MovieArtistEntity();
        movieArtistEntity.setMovie(this);
        movieArtistEntity.setArtist(artistEntity);
        artists.add(movieArtistEntity);
    }

    public void addGenreUuid(final String genreUuid) {
        genreUuids.add(genreUuid);
    }

    public void addArtistUuid(final String artistUuid) {
        artistUuids.add(artistUuid);
    }

}
