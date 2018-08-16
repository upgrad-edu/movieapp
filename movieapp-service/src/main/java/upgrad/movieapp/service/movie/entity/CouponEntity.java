
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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import upgrad.movieapp.service.common.entity.Identifier;
import upgrad.movieapp.service.common.entity.ImmutableEntity;
import upgrad.movieapp.service.common.entity.ext.EntityEqualsBuilder;
import upgrad.movieapp.service.common.entity.ext.EntityHashCodeBuilder;

@Entity
@Table(name = "COUPONS", schema = SCHEMA)
@NamedQueries({
        @NamedQuery(name = CouponEntity.COUNT_BY_ALL, query = "SELECT count(c.id) FROM CouponEntity c"),
        @NamedQuery(name = CouponEntity.BY_ALL, query = "SELECT c FROM CouponEntity c"),
        @NamedQuery(name = CouponEntity.BY_CODE, query = "SELECT c FROM CouponEntity c WHERE c.code = :code")
})
public class CouponEntity extends ImmutableEntity implements Identifier<Long>, Serializable {

    private static final long serialVersionUID = 7921266494216402080L;

    public static final String COUNT_BY_ALL = "CouponEntity.countByAll";
    public static final String BY_ALL = "CouponEntity.byAll";
    public static final String BY_CODE = "CouponEntity.byCode";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "CODE")
    @Size(max = 10)
    private String code;

    @Column(name = "DISCOUNT_PERCENTAGE")
    @NotNull
    @Digits(integer = 3, fraction = 0)
    private Integer discountPercentage;

    @Column(name = "DESCRIPTION")
    @Size(max = 1000)
    private String description;

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

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
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

}
