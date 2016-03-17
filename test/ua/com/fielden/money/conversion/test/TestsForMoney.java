package ua.com.fielden.money.conversion.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

import ua.com.fielden.money.conversion.Money;

public class TestsForMoney {

    @Test
    public void it_should_be_impossible_to_create_too_big_number() throws IllegalAccessException {
        try {
            new Money(new BigDecimal("199999999999999"));
            fail();
        } catch (final Exception e) {
            assertEquals("Too big number of digits! (Maxinum value is 999999.99)", e.getMessage());
        }
    }

    @Test
    public void negative_number_should_not_be_permited() throws IllegalAccessException {
        try {
            new Money(new BigDecimal("-19.9"));
            fail();
        } catch (final Exception e) {
            assertEquals("Money should be positive!", e.getMessage());
        }
    }

}
