package upgrad.movies.api.controller.transformer;

import upgrad.movies.api.model.CreateUserRequest;
import upgrad.movies.api.model.CreateUserResponse;
import upgrad.movies.api.model.SignupUserRequest;
import upgrad.movies.api.model.SignupUserResponse;
import upgrad.movies.api.model.UpdateUserRequest;
import upgrad.movies.api.model.UserDetailsResponse;
import upgrad.movies.api.model.UserStatusType;
import upgrad.movies.api.model.UserSummaryType;
import upgrad.movies.api.model.UsersSummaryResponse;
import upgrad.movies.service.common.model.SearchResult;
import upgrad.movies.service.user.entity.UserEntity;
import upgrad.movies.service.user.model.UserStatus;

public final class UserTransformer {

    public static UserEntity toEntity(SignupUserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setLastName(userRequest.getLastName());
        userEntity.setEmail(userRequest.getEmailAddress());
        userEntity.setPassword(userRequest.getPassword());
        userEntity.setMobilePhone(userRequest.getMobileNumber());
        return userEntity;
    }

    public static UserEntity toEntity(CreateUserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setLastName(userRequest.getLastName());
        userEntity.setEmail(userRequest.getEmailAddress());
        userEntity.setMobilePhone(userRequest.getMobileNumber());
        return userEntity;
    }

    public static UserEntity toEntity(UpdateUserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setLastName(userRequest.getLastName());
        userEntity.setEmail(userRequest.getEmailAddress());
        userEntity.setMobilePhone(userRequest.getMobileNumber());
        return userEntity;
    }

    public static CreateUserResponse toCreateUserResponse(UserEntity userEntity) {
        return new CreateUserResponse().id(userEntity.getUuid()).status(toStatus(userEntity.getStatus()));
    }

    public static SignupUserResponse toSignupResponse(UserEntity userEntity) {
        return new SignupUserResponse().id(userEntity.getUuid()).status(toStatus(userEntity.getStatus()));
    }

    public static UserDetailsResponse toUserDetailsResponse(UserEntity userEntity) {
        return new UserDetailsResponse().id(userEntity.getUuid())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .emailAddress(userEntity.getEmail())
                .mobileNumber(userEntity.getMobilePhone())
                .status(toStatus(userEntity.getStatus()))
                .role(RoleTransformer.toRoleDetailsType(userEntity.getRole()));
    }

    public static UsersSummaryResponse toUsersSummaryResponse(final int page, final int limit, final SearchResult<UserEntity> searchResult) {

        UsersSummaryResponse usersSummaryResponse = new UsersSummaryResponse().totalCount(searchResult.getTotalCount()).page(page).limit(limit);

        for(UserEntity userEntity : searchResult.getPayload()) {
            UserSummaryType summaryType = new UserSummaryType().id(userEntity.getUuid()).firstName(userEntity.getFirstName())
                    .lastName(userEntity.getLastName()).emailAddress(userEntity.getEmail())
                    .status(toStatus(userEntity.getStatus()))
                    .role(RoleTransformer.toRoleType(userEntity.getRole()));
            usersSummaryResponse.addUsersItem(summaryType);
        }

        return usersSummaryResponse;
    }

    private static UserStatusType toStatus(final int statusCode) {
        return UserStatusType.valueOf(UserStatus.get(statusCode).name());
    }

}
