/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: EntityHashCodeBuilder.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.common.entity.ext;

import upgrad.movieapp.service.common.entity.Identifier;

/**
 * Builder class for the construction of hashCode for a particular entity.
 */
public final class EntityHashCodeBuilder<K> {

    public int hashCodeById(final Identifier<K> thisIdentifiableEntity) {
        if (thisIdentifiableEntity.getId() != null) {
            return thisIdentifiableEntity.getId().hashCode();
        } else {
            return System.identityHashCode(thisIdentifiableEntity);
        }
    }

}
