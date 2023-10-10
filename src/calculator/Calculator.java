package calculator;

import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) throws Exception {
        Scanner scaner = new Scanner(System.in);
        System.out.println("Введите числа от 1 до 10 или римские числа:");
        String exp = scaner.nextLine();
        System.out.println(calcul(exp));

    }
    public static String calcul(String exp) throws Exception {
        int num1, num2;
        String oper;
        String result;
        boolean isRoman;
        String[] operands = exp.split("[+-/*]");
        if (operands.length != 2) throw new IllegalArgumentException("Некорректное выражение.");

        if (Roman.isRoman(operands[0]) && Roman.isRoman(operands[1])) {
            num1 = Roman.convertToArabian(operands[0]);
            num2 = Roman.convertToArabian(operands[1]);
            isRoman = true;
        }
        else if (!Roman.isRoman(operands[0]) && !Roman.isRoman(operands[1])) {
            num1 = Integer.parseInt(operands[0]);
            num2 = Integer.parseInt(operands[1]);
            isRoman = false;
        }
        else {
            throw new Exception("Числа должны быть в одном формате.");
        }
        if (( num1 > 10 || num2 > 10)) {
            throw new Exception("Числа не могут быть более 10.");
        }
        oper = detectOperation(exp);
        int arabian = 0;
        arabian = calc(num1, num2, oper);

        if (isRoman) {
            if (arabian <= 0) {
                throw new Exception("Римское число не может быть меньше нуля.");
            }
            result = Roman.convertToRoman(arabian);
        } else {
            result = String.valueOf(arabian);
        } return result;
    }
    static String detectOperation (String exp) {
        if(exp.contains("+")) return "+";
        else if(exp.contains("-")) return "-";
        else if(exp.contains("*")) return "*";
        else if(exp.contains("/")) return "/";
        else return null;
    }
    static int calc (int a, int b, String operand) {
        return switch (operand) {
            case "-" -> a - b;
            case "+" -> a + b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> throw new IllegalArgumentException("Проверьте знак операции");
        };
    }
}
