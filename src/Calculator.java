import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) throws IllegalAccessException {

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String[] tokens = input.split("");

        if (tokens.length != 3) {
            throw new IllegalAccessException("Неправильный формат ввода");
        }
        String operand1 = tokens[0];
        String operator = tokens[1];
        String operand2 = tokens[2];

        int num1, num2;

        boolean isRoman = false;

        try {
            num1 = Integer.parseInt(operand1);
            num2 = Integer.parseInt(operand2);
        } catch (NumberFormatException e) {

            // Если числа нельзя преобразовать в int, то проверяем, являются ли они римскими цифрами

            num1 = convertRomanToArabic(operand1);
            num2 = convertRomanToArabic(operand2);

            isRoman = true;
        }

        int result;
        switch (operator) {

            case "+":
                result = num1 + num2;
                break;

            case "-":
                result = num1 - num2;
                break;

            case "*":
                result = num1 * num2;
                break;

            case "/":
                result = num1 / num2;
                break;

            default:
                throw new IllegalAccessException("Неправильный оператор");
        }

        String output;
        if (isRoman) {
            output = convertArabicToRoman(result);

        } else {

            output = String.valueOf(result);
        }
        System.out.println(output);

    }

    private static int convertRomanToArabic(String romanNumber) {
        // Создаем отображение римских символов и соответствующих им арабских чисел

        Map<Character, Integer> romanToArabic = new HashMap<>();

        romanToArabic.put('I', 1);
        romanToArabic.put('V', 5);
        romanToArabic.put('X', 10);
        romanToArabic.put('L', 50);
        romanToArabic.put('C', 100);
        romanToArabic.put('D', 500);
        romanToArabic.put('M', 1000);

        int arabicNumber = 0;
        int prevValue = 0;

        // Проходим по символам римского числа и вычисляем соответсвующее арабское число

        for (int i = romanNumber.length() - 1; i >= 0; i--) {
            int currentValue = romanToArabic.get(romanNumber.charAt(i));
            if (currentValue < prevValue) {
                arabicNumber -= currentValue;

            } else {

                arabicNumber += currentValue;
            }
            prevValue = currentValue;
        }
        return arabicNumber;
    }

    private static String convertArabicToRoman(int arabicNumber) {

        // Создаем отоброжение арабских чисел и соответсвующих им римских символов

        Map<Integer, String> arabicToRoman = new HashMap<>();

        arabicToRoman.put(1, "I");
        arabicToRoman.put(4, "IV");
        arabicToRoman.put(5, "V");
        arabicToRoman.put(9, "IX");
        arabicToRoman.put(10, "X");
        arabicToRoman.put(40, "XL");
        arabicToRoman.put(50, "L");
        arabicToRoman.put(90, "XC");
        arabicToRoman.put(100, "C");
        arabicToRoman.put(400, "CD");
        arabicToRoman.put(500, "D");
        arabicToRoman.put(900, "CM");
        arabicToRoman.put(1000, "M");

        StringBuilder romanNumber = new StringBuilder();

        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        // Проходим по арабским числам и вычесляем соответствующее римское число

        for (int arabicValue : arabicValues) {

            while (arabicNumber >= arabicValue) {
                romanNumber.append(arabicToRoman.get(arabicValue));
                arabicNumber = arabicValue;
            }
        }

        return romanNumber.toString();
    }
}