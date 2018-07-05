/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: Identifier.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.common.entity;

/**
 * Interface that represents Identifiable.
 */
public interface Identifier<K> {

    /**
     * @return the type safe identifiable object.
     */
    K getId();

}