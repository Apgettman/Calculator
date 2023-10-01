import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] splitInput = input.split(" ");

        if (splitInput.length != 3) {
            throw new IllegalArgumentException("Некорректный формат ввода");
        }

        String firstOperand = splitInput[0];
        String operator = splitInput[1];
        String secondOperand = splitInput[2];

        if (!isOperandValid(firstOperand) || !isOperandValid(secondOperand)) {
            throw new IllegalArgumentException("Операнды должны быть числами от 1 до 10");
        }

        boolean isArabic = isArabic(firstOperand) && isArabic(secondOperand);
        boolean isRoman = isRoman(firstOperand) && isRoman(secondOperand);

        if (!isArabic && !isRoman) {
            throw new IllegalArgumentException("Операнды должны быть либо арабскими, либо римскими числами");
        }

        int result;
        if (isArabic) {
            int firstNumber = Integer.parseInt(firstOperand);
            int secondNumber = Integer.parseInt(secondOperand);
            result = calculate(firstNumber, operator, secondNumber);
        } else {
            int firstNumber = convertToArabic(firstOperand);
            int secondNumber = convertToArabic(secondOperand);
            result = calculate(firstNumber, operator, secondNumber);
        }

        String output;
        if (isArabic) {
            output = String.valueOf(result);
        } else {
            output = convertToRoman(result);
        }
        System.out.println(output);
    }

    private static boolean isOperandValid(String operand) {
        try {
            int number = Integer.parseInt(operand);
            return number >= 1 && number <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isArabic(String operand) {
        try {
            Integer.parseInt(operand);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isRoman(String operand) {
        String romanNumerals = "IVXLCDM";
        for (char c : operand.toCharArray()) {
            if (!romanNumerals.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    private static int calculate(int firstOperand, String operator, int secondOperand) {
        switch (operator) {
            case "+":
                return firstOperand + secondOperand;
            case "-":
                return firstOperand - secondOperand;
            case "*":
                return firstOperand * secondOperand;
            case "/":
                return firstOperand / secondOperand;
            default:
                throw new IllegalArgumentException("Некорректный оператор");
        }
    }

    private static int convertToArabic(String romanNumeral) {
        int result = 0;

        int index = 0;
        while (index < romanNumeral.length()) {
            int currentDigit = getValue(romanNumeral.charAt(index));
            if (index + 1 < romanNumeral.length()) {
                int nextDigit = getValue(romanNumeral.charAt(index + 1));
                if (currentDigit >= nextDigit) {
                    result += currentDigit;
                    index++;
                } else {
                    result += nextDigit - currentDigit;
                    index += 2;
                }
            } else {
                result += currentDigit;
                index++;
            }
        }

        return result;
    }

    private static int getValue(char romanDigit) {
        switch (romanDigit) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                throw new IllegalArgumentException("Некорректные символы в римском числе");
        }
    }

    private static String convertToRoman(int arabicNumber) {
        if (arabicNumber < 1 || arabicNumber > 3999) {
            throw new IllegalArgumentException("Арабское число должно быть от 1 до 3999");
        }

        StringBuilder romanNumber = new StringBuilder();
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        int index = 0;
        while (arabicNumber > 0) {
            int quotient = arabicNumber / values[index];
            for (int i = 0; i < quotient; i++) {
                romanNumber.append(symbols[index]);
                arabicNumber -= values[index];
            }
            index++;
        }

        return romanNumber.toString();
    }
}