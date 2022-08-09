package com.company;

import java.util.Scanner;

class Student {
    String first_name;
    String last_name;
    int result;

    public Student() {
        this.first_name = "";
        this.last_name = "";
        this.result = 0;

    }

    public Student(String first_name, String last_name, int res) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.result = result;
    }
}

class Result {
    Student[] student;

    void setStudents() {
        System.out.println("Enter number of students, participating in competition:");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        student = new Student[n];
        System.out.println("\nEnter student's initials and his result:");
        for (int i = 0; i < n; i++) {
            student[i] = new Student(scanner.next(), scanner.next(), scanner.nextInt());
        }
    }

    void sort() {
        int min, value;
        String s1, s2;
        for (int i = 0; i < student.length - 1; i++) {
            min = student[i].result;
            for (int j = i + 1; j < student.length; j++) {
                if (student[j].result < min) {
                    s1 = student[i].first_name;
                    s2 = student[i].last_name;
                    value = student[i].result;
                    student[i].result = student[j].result;
                    student[i].first_name = student[j].first_name;
                    student[i].last_name = student[j].last_name;
                    student[j].first_name = s1;
                    student[j].last_name = s2;
                    student[j].result = value;
                }
            }
        }
    }

    void getWinner() {
        this.sort();
        System.out.println("Min value " + student[0].first_name + " " +
            student[0].last_name + " " + student[0].result + " min.");
    }

    void getTopThreeWinners() {
        this.sort();
        System.out.println("Min value in third competitors:");
        for (int i = 0; i < 3; i++) {
            System.out.println("№" + (i + 1) + " " +
                student[i].first_name + " " + student[i].last_name +
                    " " + student[i].result + " min.");
        }
    }

    double getAverageTime() {
        int sumTime = 0;
        for (int i = 0; i < student.length; i++) {
            sumTime += student[i].result;
        }
        return (double) sumTime / student.length;
    }

    int getCountOfStudents() {
        return student.length;
    }

    public static void main(String[] args) {
        Result a = new Result();
        a.setStudents();
        a.getWinner();
        a.getTopThreeWinners();
        System.out.println("average time = " + a.getAverageTime());
        System.out.println("Student's count = " + a.getCountOfStudents());
    }
}