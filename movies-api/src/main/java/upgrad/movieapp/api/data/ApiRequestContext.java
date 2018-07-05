/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: ApiRequestContext.java
 * Date: Oct 3, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package upgrad.movieapp.api.data;

import java.time.ZonedDateTime;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import upgrad.movieapp.service.common.data.RequestContext;

/**
 * API specific implementation of {@link RequestContext}.
 */
public class ApiRequestContext implements RequestContext {

    private static final long serialVersionUID = -5330618609289024808L;

    private String requestId;

    private ZonedDateTime requestTime;

    private String clientId;

    private String clientIpAddress;

    private String userId;

    private String requestedResource;

    @Override
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String getRequestId() {
        return requestId;
    }

    @Override
    public void setRequestTime(ZonedDateTime requestTime) {
        this.requestTime = requestTime;
    }

    @Override
    public ZonedDateTime getRequestTime() {
        return requestTime;
    }

    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public void setOriginIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    @Override
    public String getOriginIpAddress() {
        return clientIpAddress;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setAccessedResource(String requestedResource) {
        this.requestedResource = requestedResource;
    }

    @Override
    public String getAccessedResource() {
        return requestedResource;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof RequestContext)) {
            return false;
        }

        final RequestContext that = (RequestContext) obj;

        return Objects.equals(this.getRequestId(), that.getRequestId())
                && Objects.equals(this.getClientId(), that.getClientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClientId(), this.getRequestId());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}