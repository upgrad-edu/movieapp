package upgrad.movieapp.service.movie.dao;

import static upgrad.movieapp.service.movie.entity.GenreEntity.BY_ALL;
import static upgrad.movieapp.service.movie.entity.GenreEntity.COUNT_BY_ALL;

import java.util.List;

import org.springframework.stereotype.Repository;
import upgrad.movieapp.service.common.dao.BaseDaoImpl;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.GenreEntity;

@Repository
public class GenreDaoImpl extends BaseDaoImpl<GenreEntity> implements GenreDao {

    @Override
    public SearchResult<GenreEntity> findAll() {
        final int totalCount = entityManager.createNamedQuery(COUNT_BY_ALL, Long.class).getSingleResult().intValue();
        final List<GenreEntity> payload = entityManager.createNamedQuery(BY_ALL, GenreEntity.class).getResultList();
        return new SearchResult(totalCount, payload);
    }

}
