package ua.com.fielden.money.conversion;

import java.math.BigDecimal;

public class Converter {
    private final String[] digits = { "одна", "дві", "три", "чотири", "п’ять", "шість", "сім", "вісім", "дев’ять" };
    private final String[] firstDecade = { "одинадцять", "дванадцять", "тринадцять", "чотирнадцять", "п’ятнадцять", "шістнадцять", "сімнадцять", "вісімнадцять", "дев’ятнадцять" };
    private final String[] decades = { "десять", "двадцять", "тридцять", "сорок", "п’ятдесят", "шістдесят", "сімдесят", "вісімдесят", "дев’яносто" };
    private final String[] hundreds = { "сто", "двісті", "триста", "чотириста", "п’ятсот", "шістсот", "сімсот", "вісімсот", "дев’ятсот" };
    private final String[] thousend = { "тисяча", "тисячі", "тисяч" };
    private final String[] hryvnia = { "гривня", "гривні", "гривень" };
    private final String[] kop = { "копійка", "копійки", "копійок" };

    public String convertNumbersToWriting(final Money digitalRepresentation) throws IllegalAccessException {
        final String wholePart = digitalRepresentation.getWholePart();
        final String fractionalPart = digitalRepresentation.getFractionalPart();
        if (isEqual(wholePart.substring(0, 1), "-")) {
            throw new IllegalArgumentException("Money should be positive!");
        }
        return convert(wholePart, fractionalPart);
    }

    private String convert(final String wholePart, final String fractionalPart) throws IllegalAccessException {

        final String firstEnd = (wholePart.length() > 3) ? (chooseVariant(wholePart.substring(0, wholePart.length() - 3), thousend)) : "";
        final String secondEnd = (wholePart.length() < 4) ? chooseVariant(wholePart, hryvnia)
                : (chooseVariant(wholePart.substring((wholePart.length() - 3), wholePart.length()), hryvnia));
        final String thirdEnd = (!isEqual(fractionalPart, "00") && !isEqual(fractionalPart, "")) ? chooseVariant(fractionalPart, kop) : "";

        String fractionalPartConverted = "";
        if (fractionalPart != "") {
            fractionalPartConverted = convertDecades(fractionalPart);
        }

        if (wholePart == "0" && fractionalPart == "") {
            return "";
        } else if (wholePart.length() == 1) {
            if (wholePart == "0") {
                return fractionalPartConverted + thirdEnd;
            }
            return convertDigits(wholePart, digits) + secondEnd + " " + fractionalPartConverted + thirdEnd;
        } else if (wholePart.length() == 2) {
            return convertDecades(wholePart) + secondEnd + " " + fractionalPartConverted + thirdEnd;
        } else if (wholePart.length() == 3) {
            return convertHundreds(wholePart) + secondEnd + " " + fractionalPartConverted + thirdEnd;
        } else if (wholePart.length() == 4) {
            return convertDigits(wholePart.substring(0, 1), digits) + firstEnd + " " + convertHundreds(wholePart.substring(1, 4)) + secondEnd + " " + fractionalPartConverted
                    + thirdEnd;
        } else if (wholePart.length() == 5) {
            return convertDecades(wholePart.substring(0, 2)) + firstEnd + " " + convertHundreds(wholePart.substring(2, 5)) + secondEnd + " " + fractionalPartConverted + thirdEnd;
        } else if (wholePart.length() == 6) {
            return convertHundreds(wholePart.substring(0, 3)) + firstEnd + " " + convertHundreds(wholePart.substring(3, 6)) + secondEnd + " " + fractionalPartConverted + thirdEnd;
        } else {
            throw new IllegalAccessException("Too big number of digits!");
        }

    }

    private boolean isEqual(final String elem1, final String elem2) {
        if (elem1.hashCode() == elem2.hashCode()) {
            return true;
        } else {
            return false;
        }
    }

    private String chooseVariant(final String numeric, final String[] array) {
        if (numeric.length() == 1) {
            return checkDigit(numeric, array);
        } else if (numeric.length() == 2) {
            final String firstDigit = numeric.substring(0, 1);
            final String secondDigit = numeric.substring(1);
            if (isEqual(firstDigit, "1") || (!isEqual(firstDigit, "1") && !isEqual(firstDigit, "0") && isEqual(secondDigit, "0"))) {
                return array[2];
            } else {
                return checkDigit(secondDigit, array);
            }
        } else {
            final String thirdDigit = numeric.substring(2);
            if (isEqual(thirdDigit, "0")) {
                return array[2];
            } else {
                return checkDigit(thirdDigit, array);
            }
        }
    }

    private String checkDigit(final String digit, final String[] array) {
        return ((isEqual(digit, "1") ? array[0] : (isEqual(digit, "2") || isEqual(digit, "3") || isEqual(digit, "4") ? array[1] : array[2])));
    }

    private String convertDigits(final String numeric, final String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (numeric.hashCode() == ((i + 1) + "").hashCode()) {
                return array[i] + " ";
            }
        }
        return "";
    }

    private String convertDecades(final String numeric) {
        final String firstDigit = numeric.substring(0, 1);
        final String secondDigit = numeric.substring(1);
        if (isEqual(firstDigit, "1") && !isEqual(secondDigit, "0")) {
            return convertDigits(secondDigit, firstDecade);
        }
        for (int i = 0; i < decades.length; i++) {
            if (isEqual(firstDigit, "0")) {
                return convertDigits(secondDigit, digits);
            } else if ((isEqual(firstDigit, ((i + 1) + ""))) && (isEqual(secondDigit, "0"))) {
                return decades[i] + " ";
            } else if ((isEqual(firstDigit, ((i + 1) + ""))) && (!isEqual(secondDigit, "0"))) {
                return decades[i] + " " + convertDigits(secondDigit, digits);
            }
        }
        return "";
    }

    private String convertHundreds(final String numeric) {
        final String firstDigit = numeric.substring(0, 1);
        final String secondDigit = numeric.substring(1, 2);
        final String thirdDigit = numeric.substring(2);

        for (int i = 0; i < hundreds.length; i++) {
            if (isEqual(firstDigit, "0")) {
                return convertDecades(secondDigit + thirdDigit);
            } else if (isEqual(firstDigit, ((i + 1) + "")) && isEqual(secondDigit, "0") && isEqual(thirdDigit, "0")) {
                return hundreds[i] + " ";
            } else if (isEqual(firstDigit, ((i + 1) + "")) && isEqual(secondDigit, "0") && !isEqual(thirdDigit, "0")) {
                return hundreds[i] + " " + convertDigits(thirdDigit, digits);
            } else if (isEqual(firstDigit, ((i + 1) + "")) && !isEqual(secondDigit, "0")) {
                return hundreds[i] + " " + convertDecades(secondDigit + thirdDigit);
            }
        }
        return "";
    }

    public static void main(final String[] args) throws IllegalAccessException {
        final Money newMoney = new Money(new BigDecimal("0.99"));
        if (newMoney.getFractionalPart() != "") {
            System.out.println(newMoney.getWholePart() + "." + newMoney.getFractionalPart());
        } else {
            System.out.println(newMoney.getWholePart());
        }
        System.out.println(new Converter().convertNumbersToWriting(newMoney));
    }

}
