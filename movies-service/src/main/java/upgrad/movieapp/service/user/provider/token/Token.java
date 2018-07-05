/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: Token.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.user.provider.token;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Specification that defines the generated token.
 */
public class Token {

    private String clientId;
    private String clientIpAddress;
    private String userId;
    private String userFirstname;
    private String userLastname;
    private String userEmailAddress;
    private String userMobilePhone;
    private ZonedDateTime userLastLoginAt;
    private ZonedDateTime issuedTime;
    private ZonedDateTime expirationTime;
    private Integer roleId;
    private String roleName;
    private Set<Integer> permissionIds;

    private Token(){}

    public String getClientId() {
        return clientId;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

     public String getUserLastname() {
        return userLastname;
    }

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

     public String getUserMobilePhone() {
        return userMobilePhone;
    }

    public ZonedDateTime getUserLastLoginAt() {
        return userLastLoginAt;
    }

    public ZonedDateTime getIssuedTime() {
        return issuedTime;
    }

    public ZonedDateTime getExpirationTime() {
        return expirationTime;
    }

     public Integer getRoleId() {
        return roleId;
    }

   public String getRoleName() {
        return roleName;
    }

   public Set<Integer> getPermissionIds() {
        return permissionIds;
    }

  @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Token)) {
            return false;
        }

        final Token that = (Token) obj;
        return Objects.equals(this.getUserId(), that.getUserId())
                && Objects.equals(this.getClientId(), that.getClientId())
                && Objects.equals(this.getClientIpAddress(), that.getClientIpAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId(), this.getClientId(), this.getClientIpAddress());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public static final class Builder {

        private final Token token = new Token();

        public Builder clientId(String clientId) {
            token.clientId = clientId;
            return this;
        }

        public Builder clientIpAddress(String clientIpAddress) {
            token.clientIpAddress = clientIpAddress;
            return this;
        }

        public Builder userId(String userId) {
            token.userId = userId;
            return this;
        }

        public Builder userFirstname(String userFirstname) {
            token.userFirstname = userFirstname;
            return this;
        }

        public Builder userLastname(String userLastname) {
            token.userLastname = userLastname;
            return this;
        }

        public Builder userEmailAddress(String userEmailAddress) {
            token.userEmailAddress = userEmailAddress;
            return this;
        }

        public Builder userMobilePhone(String userMobilePhone) {
            token.userMobilePhone = userMobilePhone;
            return this;
        }

        public Builder lastLoginAt(ZonedDateTime userLastLoginAt) {
            token.userLastLoginAt = userLastLoginAt;
            return this;
        }

        public Builder issuedTime(ZonedDateTime issuedTime) {
            token.issuedTime = issuedTime;
            return this;
        }

        public Builder expirationTime(ZonedDateTime expirationTime) {
            token.expirationTime = expirationTime;
            return this;
        }

        public Builder roleId(Integer roleId) {
            token.roleId = roleId;
            return this;
        }

        public Builder roleName(String roleName) {
            token.roleName = roleName;
            return this;
        }

        public Builder permissionIds(Set<Integer> permissionIds) {
            token.permissionIds = permissionIds;
            return this;
        }

        public Token build() {
            return token;
        }

    }

}