package upgrad.movieapp.api.controller.transformer;

import java.util.function.Function;

import upgrad.movieapp.api.model.CreateUserRequest;
import upgrad.movieapp.api.model.CreateUserResponse;
import upgrad.movieapp.api.model.UserDetailsResponse;
import upgrad.movieapp.api.model.UserStatusType;
import upgrad.movieapp.service.user.entity.UserEntity;

public final class UserTransformerFunctions {

    public static Function<CreateUserRequest, UserEntity> toEntity() {
        return userRequest -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setFirstName(userRequest.getFirstName());
            userEntity.setLastName(userRequest.getLastName());
            userEntity.setEmail(userRequest.getEmailAddress());
            userEntity.setMobilePhone(userRequest.getMobileNumber());
            return userEntity;
        };
    }

    public static Function<UserEntity, CreateUserResponse> toCreateUserResponse() {
        return userEntity ->
                new CreateUserResponse().id(userEntity.getUuid()).status(toStatus(userEntity.getStatus()));
    }

    public static Function<UserEntity, UserDetailsResponse> toUserDetailsResponse() {
        return userEntity ->
                new UserDetailsResponse().id(userEntity.getUuid())
                        .firstName(userEntity.getFirstName())
                        .lastName(userEntity.getLastName())
                        .emailAddress(userEntity.getEmail())
                        .mobileNumber(userEntity.getMobilePhone())
                        .status(toStatus(userEntity.getStatus()))
                        .role(RoleTransformer.toRoleDetailsType(userEntity.getRole()));
    }

    private static UserStatusType toStatus(final String status) {
        return UserStatusType.valueOf(status);
    }

}
