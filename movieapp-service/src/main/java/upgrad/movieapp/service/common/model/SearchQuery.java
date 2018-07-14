package upgrad.movieapp.service.common.model;

public class SearchQuery {

    private final int page;
    private final int limit;

    public SearchQuery(final int page, final int limit) {
        this.page = page;
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

}
