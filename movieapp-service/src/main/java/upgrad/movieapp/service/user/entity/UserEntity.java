/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UserEntity.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.user.entity;

import static upgrad.movieapp.service.common.entity.Entity.SCHEMA;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.apache.commons.lang3.builder.ToStringStyle;
import upgrad.movieapp.service.common.entity.Identifier;
import upgrad.movieapp.service.common.entity.MutableEntity;
import upgrad.movieapp.service.common.entity.UniversalUniqueIdentifier;
import upgrad.movieapp.service.common.entity.ext.EntityEqualsBuilder;
import upgrad.movieapp.service.common.entity.ext.EntityHashCodeBuilder;

/**
 * Entity mapping for USERS table.
 */
@Entity
@Table(name = "USERS", schema = SCHEMA)
@NamedQueries({
        @NamedQuery(name = UserEntity.COUNT_BY_ALL, query = "select count(u.id) from UserEntity u"),
        @NamedQuery(name = UserEntity.BY_ALL, query = "select u from UserEntity u"),
        @NamedQuery(name = UserEntity.BY_EMAIL, query = "select u from UserEntity u where u.email = :email") ,
        @NamedQuery(name = UserEntity.COUNT_BY_STATUS, query = "select count(u.id) from UserEntity u where u.status = :status"),
        @NamedQuery(name = UserEntity.BY_STATUS, query = "select u from UserEntity u where u.status = :status")
    })
public class UserEntity extends MutableEntity implements Identifier<Long>, UniversalUniqueIdentifier<String>, Serializable {

    private static final long serialVersionUID = 7821286494206402080L;

    public static final String COUNT_BY_ALL = "UserEntity.countByAll";
    public static final String BY_ALL = "UserEntity.byAll";
    public static final String BY_EMAIL = "UserEntity.byEmail";
    public static final String COUNT_BY_STATUS = "UserEntity.countByStatus";
    public static final String BY_STATUS = "UserEntity.byStatus";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "UUID")
    @Size(max = 36)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private RoleEntity role;

    @Column(name = "EMAIL")
    @NotNull
    @Size(max = 200)
    private String email;

    @Column(name = "PASSWORD")
    @ToStringExclude
    private String password;

    @Column(name = "FIRST_NAME")
    @NotNull
    @Size(max = 200)
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotNull
    @Size(max = 200)
    private String lastName;

    @Column(name = "MOBILE_PHONE")
    @NotNull
    @Size(max = 50)
    private String mobilePhone;

    @Column(name = "STATUS")
    @NotNull
    private String status;

    @Column(name = "FAILED_LOGIN_COUNT")
    @Min(0)
    @Max(5)
    private int failedLoginCount;

    @Column(name = "LAST_PASSWORD_CHANGE_AT")
    private ZonedDateTime lastPasswordChangeAt;

    @Column(name = "LAST_LOGIN_AT")
    private ZonedDateTime lastLoginAt;

    @Column(name = "SALT")
    @NotNull
    @Size(max = 200)
    @ToStringExclude
    private String salt;

    @Column(name = "SUBSCRIPTIONS_CONSENT")
    @NotNull
    private boolean subscriptionsConsent;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public int getFailedLoginCount() {
        return failedLoginCount;
    }

    public void setFailedLoginCount(final int failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    public ZonedDateTime getLastPasswordChangeAt() {
        return lastPasswordChangeAt;
    }

    public void setLastPasswordChangeAt(final ZonedDateTime lastPwdChgDate) {
        this.lastPasswordChangeAt = lastPwdChgDate;
    }

    public ZonedDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(final ZonedDateTime lastLoginTime) {
        this.lastLoginAt = lastLoginTime;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean hasSubscriptionsConsent() {
        return subscriptionsConsent;
    }

    public void setSubscriptionsConsent(boolean subscriptionsConsent) {
        this.subscriptionsConsent = subscriptionsConsent;
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
