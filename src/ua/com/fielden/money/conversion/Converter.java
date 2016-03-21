package ua.com.fielden.money.conversion;

public class Converter {

    private final String[] thousend = { "тисяча ", "тисячі ", "тисяч " };
    private final String[] hryvnia = { "гривня ", "гривні ", "гривень " };
    private final String[] kop = { "копійка", "копійки", "копійок" };

    public String convertNumbersToWriting(final Money digitalRepresentation) {
        final String wholePart = digitalRepresentation.getWholePart();
        final String fractionalPart = digitalRepresentation.getFractionalPart();
        return convert(wholePart, fractionalPart).replaceAll("\\s+$", "");//sometimes white space in the end should be removed(when there is no fractional part)
    }

    private String convert(final String wholePart, final String fractionalPart) {
        final ConverterOfHundreds converterOfHundreds = new ConverterOfHundreds();

        final String[] ending = choseCorrectEndsToNumeral(wholePart, fractionalPart);
        final String fractionalPartConverted = converterOfHundreds.convertDigits(fractionalPart);

        if (wholePart == "0" && fractionalPart == "00") {
            return "";
        }

        final int integerRepresentation = Integer.parseInt(wholePart);
        final String firstThreeDigits = Integer.toString(integerRepresentation / 1000);
        final String secondThreeDigits = Integer.toString(integerRepresentation % 1000);

        return converterOfHundreds.convertDigits(firstThreeDigits) + ending[0] + converterOfHundreds.convertDigits(secondThreeDigits) + ending[1] + fractionalPartConverted
                + ending[2];
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

}
