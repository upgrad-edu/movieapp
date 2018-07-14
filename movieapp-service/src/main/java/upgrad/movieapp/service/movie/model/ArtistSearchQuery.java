package upgrad.movieapp.service.movie.model;

import java.util.EnumSet;

import upgrad.movieapp.service.common.model.SearchQuery;

public class ArtistSearchQuery extends SearchQuery {

    private String name;
    private EnumSet<ArtistRoleType> roleTypes;

    public ArtistSearchQuery(final int page, final int limit) {
        super(page, limit);
    }

    public ArtistSearchQuery name(final String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public ArtistSearchQuery roleTypes(final EnumSet<ArtistRoleType> roleTypes) {
        this.roleTypes = roleTypes;
        return this;
    }

    public EnumSet<ArtistRoleType> getRoleTypes() {
        return roleTypes;
    }

}
