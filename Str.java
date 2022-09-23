import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Str {
    private static Scanner scanner = new Scanner(System.in);

    public static String inputf(String message, Object... args) {
        printf(" ".toCharArray(), "".toCharArray(), message, args);
        return input();
    }

    public static String input(String message) {
        print(" ".toCharArray(), "".toCharArray(), message);
        return input();
    }

    public static String input() {
        return scanner.nextLine();
    }

    public static void printf(String string, Object... args) {
        print(fmt(string, args));
    }

    public static void printf(char[] separationChars, char[] endChars, String string, Object... args) {
        print(separationChars, endChars, fmt(string, args));
    }

    public static void print(Integer... values) {
        Function<Integer, String> func = s -> Integer.toString(s);
        Str.<Integer>print(values, func);
    }

    public static void print(Float... values) {
        Function<Float, String> func = s -> Float.toString(s);
        Str.<Float>print(values, func);
    }

    public static void print(Double... values) {
        Function<Double, String> func = s -> Double.toString(s);
        Str.<Double>print(values, func);
    }

    public static void print(Boolean... values) {
        Function<Boolean, String> func = s -> Boolean.toString(s);
        Str.<Boolean>print(values, func);
    }

    private static <T> void print(T[] values, Function<T, String> function) {
        String valueStrings[] = new String[values.length];

        for (int i = 0; i < values.length; i++) {
            valueStrings[i] = function.apply(values[i]);
        }

        print(valueStrings);
    }

    public static void print(String... values) {
        print(" ".toCharArray(), "\n".toCharArray(), values);
    }

    public static void print(char[] separationChars, char[] endChars, String... values) {
        String string = "";
        for (int i = 0; i < values.length; i++) {
            string += values[i];

            if (i < values.length - 1)
                string += fromCharArray(separationChars);
        }
        
        System.out.print(string + fromCharArray(endChars));
    }

    /**
     * Converts a char array to a string
     * @param charArray The char array to convert to a string
     * @return The string formed from the char array
     */
    public static String fromCharArray(char[] charArray) {
        StringBuilder sb = new StringBuilder();
 
        for (Character ch : charArray) {
            sb.append(ch);
        }

        return sb.toString();
    }

    /**
     * Capitalizes the first letter of a string
     * @param string The string to capitalize
     * @return The new string with the capitalization
     */
    public static String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    /**
     * Capitalizes the first letter of every word in a string (and converts the rest to lower case)
     * @param string The string to convert to start case
     * @return The new string with the capitalization
     */
    public static String toStartCase(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') {
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    /**
     * Capitalizes the first letter of every word in a string (and converts the rest to lower case),
     * except certain articles and prepositions
     * @param string The string to convert to title case
     * @return The new string with the capitalization
     */
    public static String toTitleCase(String string) {
        String[] splitStr = string.toLowerCase().split(" ");
        List<String> exceptions = Arrays.asList("and", "the", "a", "an", "for", "to", "yet",
                                                "but", "at", "by", "as", "if", "or", "nor", "so",
                                                "in", "of", "off", "on", "per", "up", "via");

        for(int i = 0; i < splitStr.length; i++) { 
            if(i > 0 && exceptions.contains(splitStr[i]))
                continue;
        
            splitStr[i] = Character.toUpperCase(splitStr[i].charAt(0)) + splitStr[i].substring(1);
        }

        return String.join(" ", splitStr);
    }

    public static String justifyLeft(String string, int width) {
        return fmt("{0," + -width + "}", string);
    }

    public static String justifyRight(String string, int width) {
        return fmt("{0," + width + "}", string);
    }

    public static String[] splitLines(String string) {
        return string.split("\\r?\\n");
    }

    public static String zfill(String string, int width) {
        return new String(new char[width - string.length()]).replace('\0', '0') + string;
    }

    /**
     * Returns the number of times a substring occurs in a string
     * @param string The string that includes the susbstring
     * @param substring The substring to count
     * @return The number of times a substring occurs in a string
     */
    public static int count(String string, String substring) {
        return string.split(substring, -1).length - 1;
    }

    /**
     * Returns true if all the characters in a string are in the alphabet
     * @param string The string to check
     * @return True if all the characters in a string are in the alphabet 
     */
    public static boolean isAlpha(String string) {
        return string.matches("[a-zA-Z]+");
    }

    /**
     * Returns true if all the characters in a string are alphanumeric
     * @param string The string to check
     * @return True if all the characters in a string are alphanumeric
     */
    public static boolean isAlphaNum(String string) {
        return string.matches("[a-zA-Z0-9]+");
    }

    /**
     * Returns true if all the characters in a string are ascii characters
     * @param string The string to check
     * @return True if all the characters in a string are ascii characters
     */
    public static boolean isAscii(String string) {
        return Charset.forName("US-ASCII").newEncoder().canEncode(string);
    }

    /**
     * Returns true if all the characters in a string are digits
     * @param string The string to check
     * @return True if all the characters in a string are digits
     */
    public static boolean isDigit(String string) {
        return string.matches("^\\d+$");
    }

    /**
     * Returns true if all the characters in a string are decimals
     * @param string The string to check
     * @return True if all the characters in a string are decimals
     */
    public static boolean isDecimal(String string) {
        return string.matches("^\\d+?\\.?\\d+?$");
    }

    /**
     * Returns true if all the characters in a string are lower case
     * @param string The string to check
     * @return True if all the characters in a string are lower case
     */
    public static boolean isLower(String string) {
        char ch;
        boolean isLower = true;
        for (int i = 0; i < string.length(); i++) {
            ch = string.charAt(i);
            if (Character.isUpperCase(ch)) {
                isLower = false;
                break;
            }
        };

        return isLower;
    }

    /**
     * Returns true if all the characters in a string are upper case
     * @param string The string to check
     * @return True if all the characters in a string are upper case
     */
    public static boolean isUpper(String string) {
        char ch;
        boolean isUpper = true;
        for (int i = 0; i < string.length(); i++) {
            ch = string.charAt(i);
            if (Character.isLowerCase(ch)) {
                isUpper = false;
                break;
            }
        };

        return isUpper;
    }

    /**
     * Returns true if the string is a valid java identifier
     * (contains only letters, numbers, and underscores, but doesn't start with a number).
     * @param string The string to check
     * @return True if the string is a valid java identifier
     */
    public static boolean isValidJavaIdentifier(String string) {
        if (string.isEmpty()) {
            return false;
        }
        if (!Character.isJavaIdentifierStart(string.charAt(0))) {
            return false;
        }
        for (int i = 1; i < string.length(); i++) {
            if (!Character.isJavaIdentifierPart(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if all characters in the string are whitespaces (and the string is not empty)
     * @param string The string to check
     * @return True if all characters in the string are whitespaces
     */
    public static boolean isWhiteSpace(String string) {
        return !string.isEmpty() && string.trim().length() == 0;
    }

    /**
     * Converts the value of objects to strings based on the formats specified and inserts them into another string.
     * Replaces the format item in a specified string with the string representation of a corresponding object in a specified array.
     * <p>
     * A format item has this syntax: <code>{index[,alignment][:formatString]}</code>
     * 
     * <p>
     * <b>Example Implementation:</b>
     * <pre>
     * {@code}
     *int number = 5;
     *int solution = 10;
     *
     *String str = Str.format("The solution to {0} + {0} is {1}", number, solution);
     *Str.print(str);
     *
     *>> "The solution to 5 + 5 is 10"
     * </pre>
     * 
     * <ul>
     *  <li><b>Format Specifiers</b>
     *      <ul>
     *          <li><b>Control spacing <code>{index,width}</code>: </b>
     *          You can define the width of the string that is inserted into the result string by using syntax such as
     *          <code>{0,12}</code> or <code>{0, 12}</code>, which inserts a 12-character string.
     *          In this case, the string representation of the first object is right-aligned in the 12-character field.
     *          (If the string representation of the first object is more than 12 characters in length,
     *          though, the preferred field width is ignored, and the entire string is inserted into the result string.)
     *          By default, strings are right-aligned within their field if you specify a field width.
     *          To left-align strings in a field, you preface the field width with a negative sign,
     *          such as <code>{0,-12}</code> to define a 12-character left-aligned field.
     *          </li>
     *          <li><b>Fixed-Point ("F" or "f") <code>{index:F[no. of decimal digits]}</code>: </b>
     *          The fixed-point ("F") format specifier converts a number to a string of the form "-ddd.ddd…"
     *          where each "d" indicates a digit (0-9). The string starts with a minus sign if the number is negative.
     *          The precision specifier indicates the desired number of decimal places. If the precision specifier is omitted,
     *          the number of decimal digits defaults to 2.
     *          Example: <code>1234.567 ("F") -> 1234.57</code> or <code>1234 ("F1") -> 1234.0</code>
     *          </li>
     *          <li><b>Numeric format specifier ("N" or "n") <code>{index:N[no. of decimal digits]}</code>: </b>
     *          The numeric ("N") format specifier converts a number to a string of the form "-d,ddd,ddd.ddd…",
     *          where "-" indicates a negative number symbol if required, "d" indicates a digit (0-9),
     *          "," indicates a group separator, and "." indicates a decimal point symbol.
     *          The precision specifier indicates the desired number of digits after the decimal point.
     *          If the precision specifier is omitted, the number of decimal places is 2.
     *          Example: <code>1234.567 ("N") -> 1,234.57</code> or <code>1234 ("N1") -> 1,234.0</code>
     *          </li>
     *          <li><b>Decimal format specifier ("D" or "d") <code>{index:D[minimum no. of digits]}</code>: </b>
     *          The "D" (or decimal) format specifier converts a number to a string of decimal digits (0-9),
     *          prefixed by a minus sign if the number is negative. This format is supported only for integral types.
     *          The precision specifier indicates the minimum number of digits desired in the resulting string.
     *          If required, the number is padded with zeros to its left to produce the number of digits
     *          given by the precision specifier. If no precision specifier is specified,
     *          the default is the minimum value required to represent the integer without leading zeros.
     *          Example: <code>1234 ("D") -> 1234</code> or <code>-1234 ("D6") -> -001234</code>
     *          </li>
     *          <li><b>Exponential format specifier ("E" or "e") <code>{index:E[no. of decimal digits]}</code>: </b>
     *          The exponential ("E") format specifier converts a number to a string of the form "-d.ddd…E+dd" or "-d.ddd…e+dd",
     *          where each "d" indicates a digit (0-9). The string starts with a minus sign if the number is negative.
     *          Exactly one digit always precedes the decimal point.
     *          The precision specifier indicates the desired number of digits after the decimal point.
     *          If the precision specifier is omitted, a default of six digits after the decimal point is used.
     *          Example: <code>1052.0329112756 ("E") -> 1.052033E+03</code> or <code>-1052.0329112756 ("e2") -> -1.05e+03</code>
     *          </li>
     *          <li><b>Percent format specifier ("P" or "p" or "%") <code>{index:P[no. of decimal digits]}</code>
     *          or <code>{index%}</code> or <code>{%index}</code>: </b>
     *          The percent ("P") format specifier multiplies a number by 100 and converts it to a string
     *          that represents a percentage. The precision specifier indicates the desired number of decimal places.
     *          If the precision specifier is omitted, the number of decimal places is 2.
     *          Example: <code>1 ("P") -> 100.00%</code> or <code>-0.39678 ("P1") -> -39.7%</code> or <code>-0.39 ("%") -> -39.00%</code>
     *          </li>
     *          <li><b>Currency format specifier ("C" or "c" or "$") <code>{index:C}</code>
     *          or <code>{index$}</code> or <code>{$index}</code>: </b>
     *          The "C" (or currency) format specifier converts a number to a string that represents a currency amount.
     *          The default format is <code>$####.##</code> where <code>####</code> can be any length.
     *          Example: <code>123.456 ("C") -> $123.46</code> or <code>1234 ("$") -> $1234.00</code>
     *          </li>
     *          <li><b>Temperature format specifier ("TC" or "TF" or "tc" or "tf") <code>{index:TF}</code>
     *          or <code>{index:TC}</code>: </b>
     *          The temperature ("TC" or "TF") format specifier converts a number to a string that represents a temperature.
     *          The default number of decimal digits is 1 or the number of decimal digits in the float argument.
     *          The default format is <code>#.# °C</code> or <code>#.# °F</code>.
     *          Example: <code>100 ("TC") -> 100.0 °C</code> or <code>-212.7685 ("TF") -> -212.7685 °F</code>
     *          </li>
     *      </li>
     *  </ul>
     * </ul>
     * 
     * @param string The string that includes the format items
     * @param args The objects to insert into the string
     * @return The formatted string
     */
    public static String fmt(String string, Object... args) {
        String newString = string;

        String alignment = "";
        String fixedPoint = "";
        String number = "";
        String decimal = "";
        String exponential = "";
        String percent = "";
        boolean currency = false;
        boolean tempC = false;
        boolean tempF = false;

        Matcher m = Pattern.compile("\\{(.*?)\\d+(.*?)\\}").matcher(string);
        while (m.find()) {
            String match = m.group();

            alignment = "";
            Matcher am = Pattern.compile("\\,\s?\\-?\\d+").matcher(match);
            while (am.find())
                alignment = am.group();

            fixedPoint = "";
            Matcher fm = Pattern.compile("\\:\s?[fF](\\d+)?").matcher(match);
            while (fm.find())
                fixedPoint = fm.group();

            number = "";
            Matcher nm = Pattern.compile("\\:\s?[nN](\\d+)?").matcher(match);
            while (nm.find())
                number = nm.group();
            
            decimal = "";
            Matcher dm = Pattern.compile("\\:\s?[dD](\\d+)?").matcher(match);
            while (dm.find())
                decimal = dm.group();

            exponential = "";
            Matcher em = Pattern.compile("\\:\s?[eE](\\d+)?").matcher(match);
            while (em.find())
                exponential = em.group();

            percent = "";
            Matcher pm = Pattern.compile("\\:\s?[pP](\\d+)?|%").matcher(match);
            while (pm.find())
                percent = pm.group();
            percent = percent.replace("%", ":P");
            
            currency = Pattern.compile("\\:\s?[cC]|\\$").matcher(match).find();
            tempC = Pattern.compile("\\:\s?[tT][cC]").matcher(match).find();
            tempF = Pattern.compile("\\:\s?[tT][fF]").matcher(match).find();

            String updatedMatch = match .replace(alignment, "")
                                        .replace(fixedPoint, "")
                                        .replace(number, "")
                                        .replace(decimal, "")
                                        .replace(exponential, "")
                                        .replace(percent, "");
            int index = Integer.parseInt(updatedMatch.replaceAll("[^0-9]", ""));
            String value = args[index].toString();

            if (fixedPoint != "") {
                fixedPoint = fixedPoint.replaceAll("[^0-9]", "");
                if (fixedPoint == "")
                    fixedPoint = "2";
                String newFixedPoint = String.format("%." + fixedPoint + "f", Double.parseDouble(value));
                value = value.replace(value.replaceAll("\\s+",""), newFixedPoint);
            }

            else if (number != "") {
                number = number.replaceAll("[^0-9]", "");
                if (number == "")
                    number = "2";
                String newNumber = String.format("%,." + number + "f", Double.parseDouble(value));
                value = value.replace(value.replaceAll("\\s+",""), newNumber);
            }

            else if (decimal != "") {
                decimal = decimal.replaceAll("[^0-9]", "");
                if (decimal != "") {
                    String newDecimal = String.format("%0" + decimal + "d", Integer.parseInt(value));
                    value = value.replace(value.replaceAll("\\s+",""), newDecimal);
                }
            }

            else if (exponential != "") {
                String exponentialFormat = exponential.replaceAll("[^eE]", "");
                exponential = exponential.replaceAll("[^0-9]", "");
                if (exponential == "")
                    exponential = "6";
                String newExponential = String.format("%." + exponential + exponentialFormat, Double.parseDouble(value));
                value = value.replace(value.replaceAll("\\s+",""), newExponential);
            }

            else if (percent != "") {
                NumberFormat formatter = NumberFormat.getPercentInstance();
                percent = percent.replaceAll("[^0-9]", "");
                if (percent == "")
                    percent = "2";
                    formatter.setMinimumFractionDigits(Integer.parseInt(percent));
                value = formatter.format(Float.parseFloat(value));
            }

            else if (currency) {
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                value = formatter.format(Float.parseFloat(value));
            }

            else if (tempC)
                value += " °C";
            else if (tempF)
                value += " °F";

            if (alignment != "") {
                String alignmentSign = alignment.contains("-") ? "-" : "";
                alignment = alignment.replaceAll("[^0-9]", "");
                value = String.format("%" + alignmentSign + alignment + "s", value);
            }
        
            newString = newString.replace(match, value);
        }

        return newString;
    }
}