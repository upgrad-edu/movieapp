package upgrad.movieapp.service.movie.model;

import upgrad.movieapp.service.common.model.SearchQuery;

public class TheatreSearchQuery extends SearchQuery {

    private String name;
    private String cityCode;
    private Boolean active;

    public TheatreSearchQuery(final int page, final int limit) {
        super(page, limit);
    }

    public String getName() {
        return name;
    }

    public TheatreSearchQuery name(String name) {
        this.name = name;
        return this;
    }

    public String getCityCode() {
        return cityCode;
    }

    public TheatreSearchQuery cityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public Boolean isActive() {
        return active;
    }

    public TheatreSearchQuery active(Boolean active) {
        this.active = active;
        return this;
    }
}
