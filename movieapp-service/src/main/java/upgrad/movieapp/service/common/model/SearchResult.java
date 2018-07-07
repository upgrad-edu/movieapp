package upgrad.movieapp.service.common.model;

import java.util.Collection;

public class SearchResult<T> {

    private final int totalCount;
    private final Collection<T> payload;

    public SearchResult(final int totalCount, final Collection<T> payload) {
        this.totalCount = totalCount;
        this.payload = payload;
    }


    public int getTotalCount() {
        return totalCount;
    }

    public Collection<T> getPayload() {
        return payload;
    }
}
