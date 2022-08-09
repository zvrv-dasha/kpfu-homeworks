//Задача 1. Задаются границы диапазона в виде целых или вещественных чисел.
// Вводим с консоли некоторое целое или вещественное число.
// Если вводимое число не попадает в заданный интервал, то выбрасываем исключение.
// При этом создаются собственные классы  исключений  на оба случая непопадания (меньше или больше).
// Предусмотреть ввод 10 чисел в цикле с перехватом исключения.

package com.company;
import java.io.IOException;
import java.util.*;

class BetterException extends IOException {
    final int max = 20;
    public BetterException() {}
    void exc(double num) throws BetterException {
        if (num > max) {
            throw new BetterException();
        }
    }
}

class LessException extends IOException {
    final int min = 10;
    public LessException() {}
    void exc(double num) throws LessException {
        if (num < min) {
            throw new LessException();
        }
    }
}

public class Main {
    public static void main(String args[]) {
        int n = 10;
        BetterException bexc = new BetterException();
        LessException lexc = new LessException();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 10 numbers");
        for (int i = 0; i < n; i++) {
            double num = scanner.nextDouble();
            try {
                bexc.exc(num);
                lexc.exc(num);
            }
            catch (BetterException exc) {
                System.out.println("Error: Input number is more than max");
            }
            catch (LessException exc) {
                System.out.println("Error: Input number is less than min");
            }
        }
    }
}


//Задача 2. Написать функцию вычисления модуля разности двух чисел.
// При появлении отрицательного значения, стоящего под модулем, выбросить собственное исключение.

package com.company;
import java.io.IOException;
import java.util.*;

class NegativeException extends IOException {
    final int max = 20;
    public NegativeException() {}
    void exc(int num1, int num2) throws NegativeException {
        if ((num1 - num2) < 0) {
            throw new NegativeException();
        }
    }
}

public class Main {
    public static void main(String args[]) {
        NegativeException nexc = new NegativeException();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 2 numbers");
        int num1 = scanner.nextInt();
        int num2 = scanner.nextInt();
        try {
            nexc.exc(num1, num2);
            System.out.println("Sub = " + (num1 - num2));
        }
        catch (NegativeException exc) {
            System.out.println("Error: Sum is negative, sub = " + (num1 - num2));
        }
    }
}
