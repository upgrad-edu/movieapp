/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: EntityEqualsBuilder.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.common.entity.ext;

import upgrad.movieapp.service.common.entity.Identifier;

/**
 * Builder class for the construction of equality check for two different entities.
 */
public final class EntityEqualsBuilder<K> {

    @SuppressWarnings("unchecked")
    public boolean equalsById(final Identifier<K> thisIdentifiableEntity, final Object thatObject) {

        if (thatObject == null) {
            return false;
        }

        if (thisIdentifiableEntity == thatObject) {
            return true;
        }

        if (!(thatObject instanceof Identifier)) {
            return false;
        }

        final Identifier<K> thatIdentifiableEntity = (Identifier<K>) thatObject;
        if ((thisIdentifiableEntity.getId() == null && thatIdentifiableEntity.getId() != null)
                || (thatIdentifiableEntity.getId() == null && thisIdentifiableEntity.getId() != null)) {
            return false;
        }
        return thisIdentifiableEntity.getId().equals(thatIdentifiableEntity.getId());
    }

}
