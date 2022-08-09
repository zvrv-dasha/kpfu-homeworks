//1 задание
package com.company;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {

        LinkedList<String> Pets = new LinkedList<String>();

        Pets.add("Dog");
        Pets.add("Cat");
        Pets.addLast("Parrot"); // добавляем на последнее место
        Pets.addFirst("Hamster"); // добавляем на первое место
        Pets.add(1, "Rabbit"); // добавляем элемент по индексу 1

        System.out.printf("List has %d elements \n", Pets.size());
        for (String pet : Pets) {
            System.out.println(pet);
        }
        System.out.println("Got first and last elements");
        System.out.println(Pets.getFirst() + " " + Pets.getLast());
        System.out.println("Deleted last. It's a " + Pets.getLast());
        Pets.removeLast();
        for (String pet : Pets) {
            System.out.println(pet);
        }
        System.out.println("Deleted first. It's a " + Pets.getFirst());
        Pets.removeFirst();
        for (String pet : Pets) {
            System.out.println(pet);
        }
        System.out.println("Polled first. It's a " + Pets.getFirst());
        Pets.pollFirst();
        for (String pet : Pets) {
            System.out.println(pet);
        }
        Pets.addFirst("Snake");
        for (String pet : Pets) {
            System.out.println(pet);
        }
        Pets.offerLast("Crocodile");
        for (String pet : Pets) {
            System.out.println(pet);
        }
        Pets.offerLast("Penguin");
        for (String pet : Pets) {
            System.out.println(pet);
        }
    }
}

//2 задание

package com.company;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

class Person {
    private int id;
    private String secondName;
    private String firstName;
    private String lastName;
    private int age;

    public Person() {
        this.id = 0;
        this.secondName = "";
        this.firstName = "";
        this.lastName = "";
        this.age = 0;
    }

    public Person(int id, String secondName, String firstName, String lastName, int age) {
        this.id = id;
        this.secondName = secondName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFIO() {
        return this.secondName + " " +
                this.firstName + " " + this.lastName;
    }

    public String getInitials() {
        return this.secondName + " " +
                this.firstName.charAt(0) + "." +
                this.lastName.charAt(0) + ". - ";
    }

    public String getGroup() {
        return null;
    }

    public String getNameChair() {
        return null;
    }

    public int getAge() {
        return this.age;
    }
}

class Student extends Person {
    private String idGroup;

    public Student(int id, String secondName, String firstName, String lastName, int age, String idGroup) {
        super(id, secondName, firstName, lastName, age);
        this.idGroup = idGroup;
    }

    @Override
    public String getGroup() {
        return this.idGroup;
    }
}

class Teacher extends Person {
    public String nameChair;

    public Teacher(int id, String secondName, String firstName, String lastName, int age, String nameChair) {
        super(id, secondName, firstName, lastName, age);
        this.nameChair = nameChair;
    }

    @Override
    public String getNameChair() {
        return this.nameChair;
    }
}

public class Main {

    public static void main(String[] args) {
        LinkedList<Person> people = new LinkedList<Person>();
        Teacher t1 = new Teacher(1, "Petrov", "Petr", "Petrovich",
                64, "KPM");
        Teacher t2 = new Teacher(2, "Alekseev", "Alex", "Mihailovich",
                51, "KSAIT");
        Teacher t3 = new Teacher(3, "Kuzmichev", "Alex", "Borisovich",
                24, "KADIO");
        Teacher t4 = new Teacher(4, "Glebov", "Lev", "Glebovich",
                66, "KTP");
        Teacher t5 = new Teacher(5, "Simushkin", "Sergei", "Vladimirovich",
                5, "KMS");

        Student s1 = new Student(10, "Kamaliev", "Kamil", "Ferdausovich",
                22, "09-115");
        Student s2 = new Student(11, "Karachaev", "Amir", "Albertovich",
                22, "09-115");
        Student s3 = new Student(12, "Vorobkov", "Nick", "Andreevich",
                22, "09-115");
//        System.out.println(s3.getFIO());
        people.addFirst(s1);
        people.add(s2);
        people.add(s3);
        people.add(t1);
        people.add(t2);
        people.add(t3);
        people.add(t4);
        people.addLast(t5);
        for (Person person : people) {
            System.out.println(person.getFIO());
        }
        System.out.println("\n\n");

        Collections.sort(people, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return Collator.getInstance().compare(o1.getFIO(), o2.getFIO());
            }
        });

        int min = 200;
        int max = 0;
        int indexMin = -1;
        int indexMax = -1;
        for (Person person : people) {
            if (person.getClass().getSimpleName().equals("Student"))
                System.out.println(person.getInitials() +
                        "студент (группа: " + person.getGroup() + ")");

            if (person.getClass().getSimpleName().equals("Teacher"))
                System.out.println(person.getInitials() +
                        "преподаватель (кафедра: " + person.getNameChair() + ")");

            if (person.getAge() < min) {
                min = person.getAge();
                indexMin = people.indexOf(person);
            }

            if (person.getAge() > max) {
                max = person.getAge();
                indexMax = people.indexOf(person);
            }
        }

        System.out.println("\n" + people.get(indexMax).getInitials() +
                "Возраст: " + people.get(indexMax).getAge());
        
        System.out.println("\n" + people.get(indexMin).getInitials() +
                "Возраст: " + people.get(indexMin).getAge());
    }
}

// 3 задание

package com.company;

import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Random r = new Random(50);
        Set<Integer> set1 = new HashSet<>();
        for (int i = 0; i < 50; i++) {
            int a = r.nextInt(100);
            set1.add(a);
        }
        Iterator it1 = set1.iterator();
        while (it1.hasNext()) {
            System.out.print(" " + it1.next() + ":");
        }
        System.out.println();

        TreeSet<Integer> set2 = new TreeSet<>();
        for (int i = 0; i < 50; i++) {
            int a = r.nextInt(100);
            set2.add(a);
        }
        Iterator it2 = set2.iterator();
        while (it2.hasNext()) {
            System.out.print(" " + it2.next() + ":");
        }
        System.out.println();

        LinkedHashSet<Integer> set3 = new LinkedHashSet<>();
        for (int i = 0; i < 50; i++) {
            int a = r.nextInt(100);
            set3.add(a);
        }
        Iterator it3 = set3.iterator();
        while (it3.hasNext()) {
            System.out.print(" " + it3.next() + ":");
        }
        System.out.println();

        HashSet<Integer> set4 = new HashSet<>(set1);
        if (set2.containsAll(set4)) {
            System.out.println("Set 2 contains set 4");
        }
        set2.removeAll(set4);
        Iterator it4 = set2.iterator();
        while (it4.hasNext()) {
            System.out.print(" " + it4.next() + ":");
        }
    }
}
