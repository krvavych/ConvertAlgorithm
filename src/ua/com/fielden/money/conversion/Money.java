package ua.com.fielden.money.conversion;

import java.math.BigDecimal;

public class Money {
    private final BigDecimal digitalRepresentation;

    public Money(final BigDecimal numeric) {
        this.digitalRepresentation = numeric;
    }

    public String getWholePart() {
        return digitalRepresentation.toBigInteger().toString();
    }

    public String getFractionalPart() {
        final String fractionalPart = digitalRepresentation.subtract(new BigDecimal(digitalRepresentation.toBigInteger())).toString();
        if (fractionalPart.length() == 1) {
            return "";
        } else if (fractionalPart.length() == 3) {
            return (fractionalPart + "0").substring(2, 4);
        } else {
            return fractionalPart.substring(2, 4);
        }
    }

    public static void main(final String[] args) {
        final Money newMoney = new Money(new BigDecimal("44"));
        System.out.println(newMoney.getWholePart());
        System.out.println(newMoney.getFractionalPart());
    }

}
