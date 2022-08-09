//Создайте класс для сотрудников, которые имеют имя, должность и зарплату.
// Реализуйте в этом классе интерфейс Comparable для сравнивания сотрудников по размеру оплаты труда.
// Реализуйте альтернативный способ сравнения по имени с помощью интерфейса Comparator.
// Отсортируйте массив сотрудников сначала по одному, а потом по другому параметру.

package com.company;
import java.util.Arrays;
import java.util.Comparator;

class Employee implements Comparable {
    public String nameEmployee;
    public String position;
    public int salary;

    public Employee() {
        this.nameEmployee = "None";
        this.position = "None";
        this.salary = 0;
    }

    public Employee(String name, String position, int salary) {
        this.nameEmployee = name;
        this.position = position;
        this.salary = salary;
    }

    public int getSalary() {
        return this.salary;
    }

    public String getEmployeeName() {
        return this.nameEmployee;
    }

    public String getEmployeePosition() {
        return this.position;
    }

    public void print() {
        System.out.print("Name Employee is: " + this.getEmployeeName() + '\n' +
                "employee position = " + this.getEmployeePosition() + '\n' +
                "salary = " + this.getSalary() + '\n');
    }

    @Override
    public int compareTo(Object employee) {
        Employee emp = (Employee)employee;
        if (this.salary >= emp.salary) {
            if (this.salary == emp.salary) {
                System.out.println("0");
                return 0;
            }
            else
                System.out.println('1');
                return 1;
        } else
            System.out.println("-1");
            return -1;
    }
}

class EmployeeForComparator implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        Employee emp1 = (Employee) o1;
        Employee emp2 = (Employee) o2;
        return emp1.getEmployeeName().compareTo(emp2.getEmployeeName());
    }
}

public class Main {

    public static void main(String[] args) {
        Employee[] employees = {
                new Employee("Fedor", "Doctor", 1000),
                new Employee("Nick", "IT-specialist", 10000),
                new Employee("Kuzma", "Kek", 99999)
        };

        Arrays.sort(employees);
        for (Employee emp : employees) {
            emp.print();
        }
        
        System.out.println("\n\n\n");

        EmployeeForComparator empComp = new EmployeeForComparator();
        Arrays.sort(employees, empComp);
        for (Employee emp : employees) {
            emp.print();
        }
    }
}