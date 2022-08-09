package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Random random = new Random();
        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(10);
            System.out.print(array[i] + " ");
        }
        System.out.println();

        int k = scanner.nextInt();
        Thread[] t = new Thread[k];
        int j;
        for (j = 0; j < k; j++) {
            int finalJ = j;
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    int total = 0;

                    if (array.length == k) {
                        total += array[finalJ];
                        System.out.println("Thread " + (finalJ + 1) + ": Result " + " = " + total);
                    }

                    if (array.length < k) {
                        if (finalJ < array.length)
                            total += array[finalJ];
                        System.out.println("Thread " + (finalJ + 1) + ": Result " + " = " + total);
                    }

                    if (array.length % k == 0 && array.length > k) {
                        if (finalJ == 0) {
                            for (int i = finalJ; i < array.length / k; i++) {
                                total += array[i];
                            }
                            System.out.println("Thread 1: Result " + " = " + total);
                        } else {
                            for (int i = array.length / (k - finalJ + 1); i < array.length / (k - finalJ); i++) {
                                total += array[i];
                            }
                            System.out.println("Thread " + (finalJ + 1) + ": Result " + " = " + total);
                        }
                    } else {
                        if (array.length > k) {
                            if (finalJ + 2 < k) {
                                total += array[finalJ];
                                System.out.println("Thread " + (finalJ + 1) + ": Result " + " = " + total);
                            } else {
                                for (int i = finalJ + 1; i < array.length; i++)
                                    total += array[i];
                                System.out.println("Thread " + (finalJ + 2) + ": Result " + " = " + total);
                            }
                        }
                    }
                }
            });
            t[finalJ] = t1;
        }
        for (int i = 0; i < k; i++) {
            t[i].start();
        }
    }
}