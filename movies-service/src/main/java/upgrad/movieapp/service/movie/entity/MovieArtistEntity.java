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
import javax.persistence.Table;

import upgrad.movieapp.service.common.entity.Identifier;
import upgrad.movieapp.service.common.entity.ImmutableEntity;

@Entity
@Table(name = "MOVIE_ARTISTS", schema = SCHEMA)
public class MovieArtistEntity extends ImmutableEntity implements Identifier<Integer>, Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "MOVIE_ID")
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "ARTIST_ID")
    private ArtistEntity artist;

    @Override
    public Integer getId() {
        return id;
    }


    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }

    public ArtistEntity getArtist() {
        return artist;
    }

    public void setArtist(ArtistEntity artist) {
        this.artist = artist;
    }

}