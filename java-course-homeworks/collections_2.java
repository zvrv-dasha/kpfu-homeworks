// Построить диаграмму распределения случайных чисел, т.е. сосчитать количество выпадений каждого выдаваемого
// генератором случайных чисел значения из заданного диапазона. Общее количество не менее 100000.
// (Предварительно построить оболочку для числа вхождений). Подобрать нужную структуру данных.

package com.company;

import java.util.*;

class NumCounter {
    public static void parseNum(Map<Integer, Integer> numbs, int num) {
        numbs.computeIfPresent(num, (key, value) -> value + 1);
        numbs.putIfAbsent(num, 1);
    }
}

public class Main {
    public static void main(String[] args) {
        Map<Integer, Integer> numbs = new HashMap<>();
        int num;
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            num = random.nextInt(100);
            NumCounter.parseNum(numbs, num);
        }
        for (Map.Entry<Integer, Integer> numb : numbs.entrySet()) {
            Integer key = numb.getKey();
            Integer value = numb.getValue();
            System.out.println("Number " + key + " appears " + value + " times.");
        }
    }
}


//Вычислить сколько раз каждая буква встречается в заданном тексте.

package com.company;

import java.util.*;

class CharCounter {

    public static void parseString(String s) {
        Map<String, Integer> letters = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            String sub = (s.substring(i, i + 1));
            letters.computeIfPresent(sub, (key, value) -> value + 1);
            letters.putIfAbsent(sub, 1);
        }

        for (Map.Entry<String, Integer> let : letters.entrySet()) {
            String key = let.getKey();
            Integer value = let.getValue();
            System.out.println("Letter " + key + " appears " + value + " times.");
        }
    }
}
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        CharCounter.parseString(s);
    }
}