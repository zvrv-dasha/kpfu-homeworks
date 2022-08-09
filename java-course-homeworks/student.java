package com.company;


interface Enrollee {
    public void print();
}

abstract class Student implements Enrollee {
    public abstract String getNameStudent();

    public abstract int getMarksCount();
}

class ExtramuralStudent extends Student {
    String nameStudent;
    int marksCount;
    int[] marks;

    public ExtramuralStudent() {
        this.nameStudent = "None";
        this.marksCount = 0;
        this.marks = new int[this.marksCount];
    }

    public ExtramuralStudent(String name, int count, int[] array) {
        this.nameStudent = name;
        this.marksCount = count;
        this.marks = new int[this.marksCount];
        for (int i = 0; i < this.marksCount; i++) {
            try {
                this.marks[i] = array[i];
            } catch (Exception e) {
                System.out.println("Student's marks is empty");
            }
        }
    }

    @Override
    public String getNameStudent() {
        return this.nameStudent;
    }

    @Override
    public int getMarksCount() {
        return this.marksCount;
    }

    @Override
    public void print() {
        System.out.print("Name Student is: " + this.getNameStudent() + '\n' +
                "Marks's count = " + this.getMarksCount() + '\n' + "Marks: ");

        for (int i = 0; i < this.marksCount; i++) {
            System.out.print(this.marks[i] + " ");
        }
        System.out.println('\n');
    }
}

public class Main {
    public static void main(String[] args) {
        Student ExtramuralStudent = new ExtramuralStudent();
        ExtramuralStudent ExtramuralStudent2 = new ExtramuralStudent("Fedor",
                5, new int[]{1, 2, 4, 3, 0});
        ExtramuralStudent.print();
        ExtramuralStudent2.print();
    }
}