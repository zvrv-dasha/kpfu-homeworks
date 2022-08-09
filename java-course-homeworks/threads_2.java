package com.company;

import java.util.Random;
import java.util.Scanner;

class Total {
    int n;
    Random random = new Random();
    public int sum;
    public int[] array;

    public Total(int n) {
        this.n = n;
        this.array = new int[this.n];
        for (int i = 0; i < this.n; i++) {
            this.array[i] = random.nextInt(100);
        }
    }

    public void show() {
        for (int i = 0; i < this.n; i++) {
            System.out.print(this.array[i] + " ");
        }
        System.out.println();
    }

    public int summary() {
        this.sum = 0;
        for (int i = 0; i < this.n; i++) {
            this.sum += this.array[i];
        }
        return this.sum;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Total total = new Total(n);
        total.show();

        for (int i = 0; i < 2; i++) {
            new Threads("thread " + (i + 1), total).start();
        }
    }
}

class Threads extends Thread {
    Total total;

    Threads(String name, Total total) {
        super(name);
        this.total = total;
    }

    @Override
    public void run() {
        System.out.println("Result " + this.getName() + " = " + this.total.summary());
    }
}