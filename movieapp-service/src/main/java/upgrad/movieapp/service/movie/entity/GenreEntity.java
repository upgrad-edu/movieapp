/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserEntity.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.movie.entity;

import static upgrad.movieapp.service.common.entity.Entity.SCHEMA;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import upgrad.movieapp.service.common.entity.Identifier;
import upgrad.movieapp.service.common.entity.ImmutableEntity;
import upgrad.movieapp.service.common.entity.UniversalUniqueIdentifier;
import upgrad.movieapp.service.common.entity.ext.EntityEqualsBuilder;
import upgrad.movieapp.service.common.entity.ext.EntityHashCodeBuilder;

@Entity
@Table(name = "GENRES", schema = SCHEMA)
@NamedQueries({
        @NamedQuery(name = GenreEntity.COUNT_BY_ALL, query = "SELECT count(g.id) FROM GenreEntity g"),
        @NamedQuery(name = GenreEntity.BY_ALL, query = "SELECT g FROM GenreEntity g"),
        @NamedQuery(name = GenreEntity.BY_UUIDS, query = "SELECT g FROM GenreEntity g WHERE uuid IN (:genreUuids)")
})
public class GenreEntity extends ImmutableEntity implements Identifier<Long>, UniversalUniqueIdentifier<String>, Serializable {

    private static final long serialVersionUID = 7921286494206402080L;

    public static final String COUNT_BY_ALL = "GenreEntity.countByAll";
    public static final String BY_ALL = "GenreEntity.byAll";
    public static final String BY_UUIDS = "GenreEntity.byUUids";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "UUID")
    @Size(max = 36)
    private String uuid;

    @Column(name = "GENRE")
    @NotNull
    @Size(max = 50)
    private String genre;

    @Column(name = "DESCRIPTION")
    @Size(max = 2000)
    private String description;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
