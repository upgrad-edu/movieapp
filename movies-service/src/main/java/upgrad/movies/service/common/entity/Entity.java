/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: Entity.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movies.service.common.entity;

/**
 * Interface that need to be implemented by the entity classes.
 */
public interface Entity {

    String SCHEMA = "proman";

    @Override
    boolean equals(Object that);

    @Override
    int hashCode();

    @Override
    String toString();

}