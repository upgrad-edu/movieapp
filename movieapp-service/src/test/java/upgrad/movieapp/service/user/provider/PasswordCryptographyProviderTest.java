/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: PasswordCryptographyProviderTest.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.user.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class PasswordCryptographyProviderTest {

    private static final String RAW_PASSWORD = "movieapp@123";

    private final PasswordCryptographyProvider cryptographyProvider = new PasswordCryptographyProvider();

    @Test
    public void test1() {

        String[] output = cryptographyProvider.encrypt(RAW_PASSWORD);
        String saltForDB = output[0];
        String passwordForDB = output[1];

        assertNotNull("PasswordUtilTest.test1() saltForDB check", saltForDB);
        assertNotNull("PasswordUtilTest.test1() passwordForDB check", passwordForDB);

    }

    @Test
    public void test2() {

        String[] output = cryptographyProvider.encrypt(RAW_PASSWORD);
        String saltForDB = output[0];
        String passwordForDB = output[1];

        String rehashedPWD = cryptographyProvider.encrypt(RAW_PASSWORD, saltForDB);
        assertEquals(rehashedPWD, passwordForDB);

    }

    @Test
    public void test3() {

        String[] output = cryptographyProvider.encrypt(RAW_PASSWORD);
        String saltForDB = output[0];
        String passwordForDB = output[1];

        String rehashedPWD = cryptographyProvider.encrypt("Ness123", saltForDB);

        assertNotEquals(rehashedPWD, passwordForDB, "PasswordUtilTest.test3() password comparision");

    }

}