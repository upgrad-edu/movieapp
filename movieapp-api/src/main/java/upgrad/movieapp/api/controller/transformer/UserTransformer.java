package upgrad.movieapp.api.controller.transformer;

import upgrad.movieapp.api.model.CreateUserResponse;
import upgrad.movieapp.api.model.SignupUserResponse;
import upgrad.movieapp.api.model.UserDetailsResponse;
import upgrad.movieapp.api.model.UserStatusType;
import upgrad.movieapp.api.model.UserSummaryType;
import upgrad.movieapp.api.model.UsersSummaryResponse;
import upgrad.movieapp.api.model.CreateUserRequest;
import upgrad.movieapp.api.model.SignupUserRequest;
import upgrad.movieapp.api.model.UpdateUserRequest;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.user.entity.UserEntity;

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

    private static UserStatusType toStatus(final String status) {
        return UserStatusType.valueOf(status);
    }

}
