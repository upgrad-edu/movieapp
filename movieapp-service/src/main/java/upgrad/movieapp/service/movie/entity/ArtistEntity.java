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
import upgrad.movieapp.service.common.entity.MutableEntity;
import upgrad.movieapp.service.common.entity.UniversalUniqueIdentifier;
import upgrad.movieapp.service.common.entity.ext.EntityEqualsBuilder;
import upgrad.movieapp.service.common.entity.ext.EntityHashCodeBuilder;

@Entity
@Table(name = "ARTISTS", schema = SCHEMA)
@NamedQueries({
        @NamedQuery(name = ArtistEntity.COUNT_BY_ALL, query = "SELECT count(a.id) FROM ArtistEntity a"),
        @NamedQuery(name = ArtistEntity.BY_ALL, query = "SELECT a FROM ArtistEntity a"),
        @NamedQuery(name = ArtistEntity.COUNT_BY_TYPE, query = "SELECT count(a.id) FROM ArtistEntity a WHERE a.type IN (:types)"),
        @NamedQuery(name = ArtistEntity.BY_TYPE, query = "SELECT a FROM ArtistEntity a WHERE a.type IN (:types)")
})
public class ArtistEntity extends MutableEntity implements Identifier<Long>, UniversalUniqueIdentifier<String>, Serializable {

    private static final long serialVersionUID = 7821286494206402080L;

    public static final String COUNT_BY_ALL = "ArtistEntity.countByAll";
    public static final String BY_ALL = "ArtistEntity.byAll";
    public static final String COUNT_BY_TYPE = "ArtistEntity.countByType";
    public static final String BY_TYPE = "ArtistEntity.byType";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "UUID")
    @Size(max = 36)
    private String uuid;

    @Column(name = "FIRST_NAME")
    @NotNull
    @Size(max = 50)
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotNull
    @Size(max = 50)
    private String lastName;

    @Column(name = "TYPE")
    @NotNull
    @Size(max = 50)
    private String type;

    @Column(name = "PROFILE_DESCRIPTION")
    @Size(max = 2000)
    private String profileDescription;

    @Column(name = "PROFILE_PICTURE_URL")
    @Size(max = 2000)
    private String profilePictureUrl;

    @Column(name = "WIKI_URL")
    @Size(max = 2000)
    private String wikiUrl;

    @Column(name = "ACTIVE")
    @NotNull
    private boolean active;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
