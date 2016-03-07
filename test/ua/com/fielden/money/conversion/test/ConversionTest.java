package ua.com.fielden.money.conversion.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

import ua.com.fielden.money.conversion.Converter;
import ua.com.fielden.money.conversion.Money;

public class ConversionTest {

    @Test
    public void it_should_be_impossible_to_convert_wrong_data() {
        final Money money = new Money(new BigDecimal("199999999999999"));
        try {
            new Converter().convertNumbersToWriting(money);
            fail();
        } catch (final Exception e) {
            assertEquals("Too big number of digits!", e.getMessage());
        }
    }

    @Test
    public void negative_number_should_not_be_permited() throws IllegalAccessException {
        final Money money = new Money(new BigDecimal("-19"));
        try {
            new Converter().convertNumbersToWriting(money);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertEquals("Money should be positive!", ex.getMessage());
        }
    }

    @Test
    public void conversion_should_be_correct() throws IllegalAccessException {
        assertEquals("двадцять одна гривня п’ять копійок", new Converter().convertNumbersToWriting(new Money(new BigDecimal("21.057"))));
        assertEquals("двадцять дві тисячі сто гривень тридцять п’ять копійок", new Converter().convertNumbersToWriting(new Money(new BigDecimal("22100.35"))));
        assertEquals("сто гривень сімдесят три копійки", new Converter().convertNumbersToWriting(new Money(new BigDecimal("100.73"))));
        assertEquals("триста тисяч п’ятсот гривень дванадцять копійок", new Converter().convertNumbersToWriting(new Money(new BigDecimal("300500.12"))));
        assertEquals("дев’ятсот одна тисяча три гривні дев’яносто одна копійка", new Converter().convertNumbersToWriting(new Money(new BigDecimal("901003.91"))));
        assertEquals("", new Converter().convertNumbersToWriting(new Money(new BigDecimal("0"))));
        assertEquals("одна гривня ", new Converter().convertNumbersToWriting(new Money(new BigDecimal("1"))));
        assertEquals("тридцять шість гривень ", new Converter().convertNumbersToWriting(new Money(new BigDecimal("36.00"))));
        assertEquals("одна тисяча сто дві гривні одна копійка", new Converter().convertNumbersToWriting(new Money(new BigDecimal("1102.01"))));
        assertEquals("двадцять три копійки", new Converter().convertNumbersToWriting(new Money(new BigDecimal("0.23"))));
    }

    @Test
    public void the_same_numbers_should_be_equal() throws IllegalAccessException {
        final Money money1 = new Money(new BigDecimal("19"));
        final Money money2 = new Money(new BigDecimal("19.00"));
        assertEquals(new Converter().convertNumbersToWriting(money1), new Converter().convertNumbersToWriting(money2));
    }

    @Test
    public void nouns_should_be_used_correct () throws IllegalAccessException{
        final Money money1 = new Money(new BigDecimal("191"));
        final Money money2 = new Money(new BigDecimal("102000"));
        final Money money3 = new Money(new BigDecimal("19.05"));
        assertTrue(new Converter().convertNumbersToWriting(money1).contains("гривня"));
        assertTrue(new Converter().convertNumbersToWriting(money2).contains("тисячі"));
        assertTrue(new Converter().convertNumbersToWriting(money3).contains("копійок"));
    }

    @Test
    public void  numerals_should_be_used_correct () throws IllegalAccessException{
        final Money money1 = new Money(new BigDecimal("101"));
        final Money money2 = new Money(new BigDecimal("11"));
        final Money money3 = new Money(new BigDecimal("22"));
        final Money money4 = new Money(new BigDecimal("12"));
        final Money money5 = new Money(new BigDecimal("09"));
        final Money money6 = new Money(new BigDecimal("90"));
        final Money money7 = new Money(new BigDecimal("0010"));
        assertTrue(new Converter().convertNumbersToWriting(money1).contains("одна"));
        assertTrue(new Converter().convertNumbersToWriting(money2).contains("одинадцять"));
        assertTrue(new Converter().convertNumbersToWriting(money3).contains("дві"));
        assertTrue(new Converter().convertNumbersToWriting(money4).contains("дванадцять"));
        assertTrue(new Converter().convertNumbersToWriting(money5).contains("дев’ять"));
        assertTrue(new Converter().convertNumbersToWriting(money6).contains("дев’яносто"));
        assertTrue(new Converter().convertNumbersToWriting(money7).contains("десять"));
    }
}
