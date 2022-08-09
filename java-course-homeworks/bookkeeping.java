package com.company;


class Employee {
    public String nameEmployee;
    public int employeesCount;
    public int salary;
    public int[] employees;

    public Employee() {
        this.nameEmployee = "None";
        this.employeesCount = 0;
        this.salary = 0;
        this.employees = new int[this.employeesCount];
    }

    public Employee(String name, int count, int salary, int[] array) {
        this.nameEmployee = name;
        this.employeesCount = count;
        this.salary = salary;
        this.employees = new int[this.employeesCount];
        for (int i = 0; i < this.employeesCount; i++) {
            try {
                this.employees[i] = array[i];
            } catch (Exception e) {
                System.out.println("Employee's employees is empty");
            }
        }
    }

    public int getSalary() {
        return this.salary;
    }

    public String getNameEmployee() {
        return this.nameEmployee;
    }

    public int getEmployeesCount() {
        return this.employeesCount;
    }

    public void print() {
        System.out.print("Name Employee is: " + this.getNameEmployee() + '\n' +
                "count employees = " + this.getEmployeesCount() + '\n' +
                "salary = " + this.getSalary() + '\n' + "employees quality: ");

        for (int i = 0; i < this.employeesCount; i++) {
            System.out.print(this.employees[i] + " ");
        }
        System.out.println('\n');
    }
}

class Bookkeeping extends Employee {
    Employee[] listEmployees;
    boolean l = true;

    public Bookkeeping(Employee[] emp) {
        this.listEmployees = new Employee[emp.length];
//        this.listEmployees = emp;
        for (int i = 0; i < emp.length; i++) {
            this.listEmployees[i] = emp[i];
        }
    }

    public void increaseSalaryOfEmployees(int number) {
        this.listEmployees[number].salary = this.listEmployees[number].getSalary()
                + this.listEmployees[number].getSalary();
    }

    public void increaseSalaryIf() {

        for (int i = 0; i < this.listEmployees.length; i++) {
            if (this.listEmployees[i].getEmployeesCount() > 3) {
//                System.out.println(this.listEmployees[i].getEmployeesCount());

//                System.out.println(this.listEmployees[i].getEmployeesCount());
                for (int j = 0; j < this.listEmployees[i].getEmployeesCount(); j++) {
                    if (this.listEmployees[i].employees[j] > 6) {
                        l = false;
                        break;
                    }
                }

                if (l)
                    this.increaseSalaryOfEmployees(i);
            }
        }
    }


}

public class Main {
    public static void main(String[] args) {
        Employee employee = new Employee("Fedor",
                5, 1000, new int[]{1, 2, 4, 3, 0});
        employee.print();

        Employee employee2 = new Employee("Nick",
                4, 1200, new int[]{1, 2, 4, 0});
        employee2.print();
        Employee[] employees = {employee, employee2};

        Bookkeeping bookkeeping = new Bookkeeping(employees);
        bookkeeping.increaseSalaryIf();

        System.out.println("Fedor's salary = " + employees[0].getSalary());
        System.out.println("Nick's salary = " + employees[1].getSalary());
    }
}