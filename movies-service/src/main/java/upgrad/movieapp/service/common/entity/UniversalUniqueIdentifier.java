/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: UniversalUniqueIdentifier.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.common.entity;

/**
 * Interface that represents Universal unique identifier (UUID).
 */
public interface UniversalUniqueIdentifier<K> {

    /**
     * @return the type safe universal unique identifier.
     */
    K getUuid();

}