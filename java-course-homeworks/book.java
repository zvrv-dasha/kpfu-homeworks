package com.company;

interface Edition {
    public void print();
}

abstract class Book implements Edition{
    public abstract int getListCount();
    public abstract String getNameBook();
}

class Cyclopedia extends Book{
    String nameBook;
    int listCount;

    public Cyclopedia() {
        this.nameBook = "None";
        this.listCount = 0;

    }

    public Cyclopedia(String name, int count) {
        this.nameBook = name;
        this.listCount = count;
    }

    @Override
    public int getListCount() {
        return this.listCount;
    }

    @Override
    public String getNameBook() {
        return this.nameBook;
    }

    @Override
    public void print() {
        System.out.println("Name book is: " + this.getNameBook() + '\n' +
                "List's count = " + this.getListCount() + '\n');
    }
}

public class Main {
    public static void main(String[] args) {
        Book cyclopedia = new Cyclopedia();
        Cyclopedia cyclopedia2 = new Cyclopedia("Math analysis", 999);
        cyclopedia.print();
        cyclopedia2.print();
    }
}