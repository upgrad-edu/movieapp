package upgrad.movieapp.service.user.model;

import java.io.Serializable;

public class UserRole implements Serializable {

    private final int uuid;
    private final String name;

    public UserRole(final int uuid, final String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public int getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

}
