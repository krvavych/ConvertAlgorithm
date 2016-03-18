package ua.com.fielden.money.conversion;

import java.math.BigDecimal;

public class Converter {
    private final String[] digits = { "одна", "дві", "три", "чотири", "п’ять", "шість", "сім", "вісім", "дев’ять" };
    private final String[] firstDozen = { "одинадцять", "дванадцять", "тринадцять", "чотирнадцять", "п’ятнадцять", "шістнадцять", "сімнадцять", "вісімнадцять", "дев’ятнадцять" };
    private final String[] dozens = { "десять", "двадцять", "тридцять", "сорок", "п’ятдесят", "шістдесят", "сімдесят", "вісімдесят", "дев’яносто" };
    private final String[] hundreds = { "сто", "двісті", "триста", "чотириста", "п’ятсот", "шістсот", "сімсот", "вісімсот", "дев’ятсот" };
    private final String[] thousend = { "тисяча ", "тисячі ", "тисяч " };
    private final String[] hryvnia = { "гривня ", "гривні ", "гривень " };
    private final String[] kop = { "копійка", "копійки", "копійок" };

    public String convertNumbersToWriting(final Money digitalRepresentation) throws IllegalArgumentException {
        final String wholePart = digitalRepresentation.getWholePart();
        final String fractionalPart = digitalRepresentation.getFractionalPart();
        return convert(wholePart, fractionalPart).replaceAll("\\s+$", "");
    }

    private String convert(final String wholePart, final String fractionalPart) throws IllegalArgumentException {

        final String[] ending = choseCorrectEndsToNumeral(wholePart, fractionalPart);
        final String fractionalPartConverted = convertDozens(fractionalPart);

        if (wholePart == "0" && fractionalPart == "00") {
            return "";
        } else if (wholePart.length() == 1) {
            if (wholePart == "0") {
                return fractionalPartConverted + ending[2];
            }
            return convertDigits(wholePart, digits) + ending[1] + fractionalPartConverted + ending[2];
        } else if (wholePart.length() == 2) {
            return convertDozens(wholePart) + ending[1] + fractionalPartConverted + ending[2];
        } else if (wholePart.length() == 3) {
            return convertHundreds(wholePart) + ending[1] + fractionalPartConverted + ending[2];
        } else if (wholePart.length() == 4) {
            return convertDigits(wholePart.substring(0, 1), digits) + ending[0] + convertHundreds(wholePart.substring(1, 4)) + ending[1] + fractionalPartConverted
                    + ending[2];
        } else if (wholePart.length() == 5) {
            return convertDozens(wholePart.substring(0, 2)) + ending[0] + convertHundreds(wholePart.substring(2, 5)) + ending[1] + fractionalPartConverted + ending[2];
        } else {
            return convertHundreds(wholePart.substring(0, 3)) + ending[0] + convertHundreds(wholePart.substring(3, 6)) + ending[1] + fractionalPartConverted
                    + ending[2];
        }

    }

    private String[] choseCorrectEndsToNumeral(final String wholePart, final String fractionalPart) {
        final String[] ending = new String[3];
        ending[0] = (wholePart.length() > 3) ? (chooseVariantOfEnding(wholePart.substring(0, wholePart.length() - 3), thousend)) : "";
        ending[1] = (wholePart.equals("0")) ? "" : ((wholePart.length() < 4) ? chooseVariantOfEnding(wholePart, hryvnia)
                : (chooseVariantOfEnding(wholePart.substring((wholePart.length() - 3), wholePart.length()), hryvnia)));
        ending[2] = (!(fractionalPart.equals("00")) && !(fractionalPart.equals(""))) ? chooseVariantOfEnding(fractionalPart, kop) : "";
        return ending;
    }

    private String chooseVariantOfEnding(final String numeric, final String[] array) {
        if (numeric.length() == 1) {
            return chooseWordVariant(numeric, array);
        } else if (numeric.length() == 2) {
            final String firstDigit = numeric.substring(0, 1);
            final String secondDigit = numeric.substring(1);

            if ((firstDigit.equals("1")) || (!(firstDigit.equals("1")) && !(firstDigit.equals("0")) && (secondDigit.equals("0")))) {
                return array[2];
            } else {
                return chooseWordVariant(secondDigit, array);
            }
        } else {
            final String thirdDigit = numeric.substring(2);
            if (thirdDigit.equals("0")) {
                return array[2];
            } else {
                return chooseWordVariant(thirdDigit, array);
            }
        }
    }

    private String chooseWordVariant(final String digit, final String[] array) {
        return (((digit.equals("1")) ? array[0] : ((digit.equals("2")) || (digit.equals("3")) || (digit.equals("4")) ? array[1] : array[2])));
    }

    private String convertDigits(final String numeric, final String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (numeric.hashCode() == ((i + 1) + "").hashCode()) {
                return array[i] + " ";
            }
        }
        return "";
    }

    private String convertDozens(final String numeric) {
        final String firstDigit = numeric.substring(0, 1);
        final String secondDigit = numeric.substring(1);
        if ((firstDigit.equals("1")) && !(secondDigit.equals("0"))) {
            return convertDigits(secondDigit, firstDozen);
        }
        for (int i = 0; i < dozens.length; i++) {
            if ((firstDigit.equals("0"))) {
                return convertDigits(secondDigit, digits);
            } else if (((firstDigit.equals((i + 1) + ""))) && ((secondDigit.equals("0")))) {
                return dozens[i] + " ";
            } else if (((firstDigit.equals(((i + 1) + "")))) && (!(secondDigit.equals("0")))) {
                return dozens[i] + " " + convertDigits(secondDigit, digits);
            }
        }
        return "";
    }

    private String convertHundreds(final String numeric) {
        final String firstDigit = numeric.substring(0, 1);
        final String secondDigit = numeric.substring(1, 2);
        final String thirdDigit = numeric.substring(2);

        for (int i = 0; i < hundreds.length; i++) {
            if (firstDigit.equals("0")) {
                return convertDozens(secondDigit + thirdDigit);
            } else if (firstDigit.equals(((i + 1) + "")) && (secondDigit.equals("0")) && (thirdDigit.equals("0"))) {
                return hundreds[i] + " ";
            } else if ((firstDigit.equals(((i + 1) + ""))) && (secondDigit.equals("0")) && !(thirdDigit.equals("0"))) {
                return hundreds[i] + " " + convertDigits(thirdDigit, digits);
            } else if ((firstDigit.equals(((i + 1) + ""))) && !((secondDigit.equals("0")))) {
                return hundreds[i] + " " + convertDozens(secondDigit + thirdDigit);
            }
        }
        return "";
    }

    public static void main(final String[] args) throws IllegalArgumentException {
        final Money newMoney = new Money(new BigDecimal(""));
        if (newMoney.getFractionalPart() != "") {
            System.out.println(newMoney.getWholePart() + "." + newMoney.getFractionalPart());
        } else {
            System.out.println(newMoney.getWholePart());
        }
        System.out.println(new Converter().convertNumbersToWriting(newMoney));
    }

}
