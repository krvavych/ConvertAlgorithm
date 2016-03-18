package ua.com.fielden.money.conversion.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import ua.com.fielden.money.conversion.Converter;
import ua.com.fielden.money.conversion.Money;

public class ConversionTest {

    @Test
    public void test_for_creation_fractional_part() {
        assertEquals("", new Converter().convertNumbersToWriting(new Money(new BigDecimal("0.0"))));
        assertEquals("", new Converter().convertNumbersToWriting(new Money(new BigDecimal("0.00"))));
        assertEquals("одна копійка", new Converter().convertNumbersToWriting(new Money(new BigDecimal("0.01"))));
        assertEquals("шістдесят копійок", new Converter().convertNumbersToWriting(new Money(new BigDecimal("0.6"))));
        assertEquals("тридцять копійок", new Converter().convertNumbersToWriting(new Money(new BigDecimal("0.30"))));
        assertEquals("дванадцять копійок", new Converter().convertNumbersToWriting(new Money(new BigDecimal("0.12"))));
        assertEquals("сімдесят чотири копійки", new Converter().convertNumbersToWriting(new Money(new BigDecimal("0.74"))));
    }

    @Test
    public void test_for_creation_digits() {
        assertEquals("", new Converter().convertNumbersToWriting(new Money(new BigDecimal("0"))));
        assertEquals("одна гривня", new Converter().convertNumbersToWriting(new Money(new BigDecimal("1.00"))));
        assertEquals("три гривні", new Converter().convertNumbersToWriting(new Money(new BigDecimal("3"))));
        assertEquals("вісім гривень", new Converter().convertNumbersToWriting(new Money(new BigDecimal("8"))));
    }

    @Test
    public void test_for_creation_dozens() {
        assertEquals("десять гривень", new Converter().convertNumbersToWriting(new Money(new BigDecimal("10.00"))));
        assertEquals("тринадцять гривень", new Converter().convertNumbersToWriting(new Money(new BigDecimal("13"))));
        assertEquals("двадцять чотири гривні", new Converter().convertNumbersToWriting(new Money(new BigDecimal("24"))));
        assertEquals("дев’яносто гривень", new Converter().convertNumbersToWriting(new Money(new BigDecimal("90"))));
    }

    @Test
    public void test_for_creation_hundreds() {
        assertEquals("сто гривень", new Converter().convertNumbersToWriting(new Money(new BigDecimal("100"))));
        assertEquals("п’ятсот чотири гривні", new Converter().convertNumbersToWriting(new Money(new BigDecimal("504.00"))));
        assertEquals("триста тридцять дев’ять гривень", new Converter().convertNumbersToWriting(new Money(new BigDecimal("339"))));
    }

    @Test
    public void test_for_creation_thousands() {
        assertEquals("одна тисяча гривень", new Converter().convertNumbersToWriting(new Money(new BigDecimal("1000"))));
        assertEquals("двадцять тисяч гривень", new Converter().convertNumbersToWriting(new Money(new BigDecimal("20000"))));
        assertEquals("дванадцять тисяч гривень", new Converter().convertNumbersToWriting(new Money(new BigDecimal("12000.00"))));
        assertEquals("двісті три тисячі гривень", new Converter().convertNumbersToWriting(new Money(new BigDecimal("203000"))));
        assertEquals("дев’ятсот тридцять одна тисяча гривень", new Converter().convertNumbersToWriting(new Money(new BigDecimal("931000"))));
    }

    @Test
    public void nouns_should_be_used_correct() {
        final Money money1 = new Money(new BigDecimal("191"));
        final Money money2 = new Money(new BigDecimal("102000"));
        final Money money3 = new Money(new BigDecimal("19.05"));
        assertTrue(new Converter().convertNumbersToWriting(money1).contains("гривня"));
        assertTrue(new Converter().convertNumbersToWriting(money2).contains("тисячі"));
        assertTrue(new Converter().convertNumbersToWriting(money3).contains("копійок"));
    }

}
