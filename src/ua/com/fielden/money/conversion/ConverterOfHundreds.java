package ua.com.fielden.money.conversion;

public class ConverterOfHundreds {

    private final String[] ones = { "одна", "дві", "три", "чотири", "п’ять", "шість", "сім", "вісім", "дев’ять" };
    private final String[] firstTen = { "одинадцять", "дванадцять", "тринадцять", "чотирнадцять", "п’ятнадцять", "шістнадцять", "сімнадцять", "вісімнадцять", "дев’ятнадцять" };
    private final String[] tens = { "десять", "двадцять", "тридцять", "сорок", "п’ятдесят", "шістдесят", "сімдесят", "вісімдесят", "дев’яносто" };
    private final String[] hundreds = { "сто", "двісті", "триста", "чотириста", "п’ятсот", "шістсот", "сімсот", "вісімсот", "дев’ятсот" };

    public String convertDigits(final String numeric) {
        if (numeric.length() == 1) {
            return convertDigits(numeric, ones);
        } else if (numeric.length() == 2) {
            return convertTens(numeric);
        }else {
            return convertHundreds(numeric);
        }
    }

    private String convertDigits(final String numeric, final String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (numeric.equals((i + 1) + "")) {
                return array[i] + " ";
            }
        }
        return "";
    }

    private String convertTens(final String numeric) {
        final String firstDigit = numeric.substring(0, 1);
        final String secondDigit = numeric.substring(1);
        if (firstDigit.equals("1") && !secondDigit.equals("0")) {
            return convertDigits(secondDigit, firstTen);
        }
        for (int i = 0; i < tens.length; i++) {
            if (firstDigit.equals("0")) {
                return convertDigits(secondDigit, ones);
            } else if (firstDigit.equals((i + 1) + "") && secondDigit.equals("0")) {
                return tens[i] + " ";
            } else if (firstDigit.equals((i + 1) + "") && !secondDigit.equals("0")) {
                return tens[i] + " " + convertDigits(secondDigit, ones);
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
                return convertTens(secondDigit + thirdDigit);
            } else if (firstDigit.equals((i + 1) + "") && secondDigit.equals("0") && thirdDigit.equals("0")) {
                return hundreds[i] + " ";
            } else if (firstDigit.equals((i + 1) + "") && secondDigit.equals("0") && !thirdDigit.equals("0")) {
                return hundreds[i] + " " + convertDigits(thirdDigit, ones);
            } else if (firstDigit.equals((i + 1) + "") && !secondDigit.equals("0")) {
                return hundreds[i] + " " + convertTens(secondDigit + thirdDigit);
            }
        }
        return "";
    }

}
