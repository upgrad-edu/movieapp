
package upgrad.movieapp.service.movie.entity;

import static upgrad.movieapp.service.common.entity.Entity.SCHEMA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import upgrad.movieapp.service.common.entity.Identifier;
import upgrad.movieapp.service.common.entity.ImmutableEntity;
import upgrad.movieapp.service.common.entity.ext.EntityEqualsBuilder;
import upgrad.movieapp.service.common.entity.ext.EntityHashCodeBuilder;

@Entity
@Table(name = "CITIES", schema = SCHEMA)
@NamedQueries({
        @NamedQuery(name = CityEntity.COUNT_BY_ALL, query = "SELECT count(c.id) FROM CityEntity c"),
        @NamedQuery(name = CityEntity.BY_ALL, query = "SELECT c FROM CityEntity c"),
        @NamedQuery(name = CityEntity.BY_CODE, query = "SELECT c FROM CityEntity c WHERE c.code = :code")
})
public class CityEntity extends ImmutableEntity implements Identifier<Long>, Serializable {

    private static final long serialVersionUID = 5021286494206402080L;

    public static final String COUNT_BY_ALL = "CityEntity.countByAll";
    public static final String BY_ALL = "CityEntity.byAll";
    public static final String BY_CODE = "CityEntity.byCode";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "CODE")
    @Size(max = 3)
    private String code;

    @Column(name = "NAME")
    @NotNull
    @Size(max = 50)
    private String name;

    @Override
    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
