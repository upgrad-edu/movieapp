package upgrad.movieapp.service.user.entity;

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
@Table(name = "PERMISSIONS", schema = SCHEMA)
public class PermissionEntity extends ImmutableEntity implements Identifier<Integer>, UniversalUniqueIdentifier<Integer>, Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID")
    @NotNull
    private Integer uuid;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private GroupEntity group;

    @Column(name = "NAME")
    @NotNull
    @Size(max = 50)
    private String name;

    @Column(name = "DESCRIPTION")
    @NotNull
    @Size(max = 200)
    private String description;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getUuid() {
        return uuid;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        return new EntityEqualsBuilder<Integer>().equalsById(this, obj);
    }

    @Override
    public int hashCode() {
        return new EntityHashCodeBuilder<Integer>().hashCodeById(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
