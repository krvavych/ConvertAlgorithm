package ua.com.fielden.money.conversion;

import java.math.BigDecimal;

public class Money {
    private final String wholePart;
    private final String fractionalPart;

    public Money(final BigDecimal numeric) throws IllegalAccessException {
        final String stringRepresentation = numeric.toString();

        if(stringRepresentation.contains("-")){
            throw new IllegalAccessException("Money should be positive!");
        }

        if (stringRepresentation.contains(".")){
            final String[] parts = stringRepresentation.split("\\.");
            this.wholePart = parts[0];
            this.fractionalPart = parts[1];
            if(fractionalPart.length()>2){
                throw new IllegalAccessException("Too long fractional part!");
            }
        }else {
            this.wholePart = stringRepresentation;
            this.fractionalPart = "";
        }
    }

    public String getWholePart() {
        return this.wholePart;
    }

    public String getFractionalPart() {
        if (this.fractionalPart.length() == 1) {
            return this.fractionalPart + "0";
        }
        return this.fractionalPart;
    }

    public static void main(final String[] args) throws IllegalAccessException {
        final Money newMoney = new Money(new BigDecimal("44.15"));
        System.out.println(newMoney.getWholePart());
        System.out.println(newMoney.getFractionalPart());

    }

}
