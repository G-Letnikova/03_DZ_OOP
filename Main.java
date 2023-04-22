
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Main {

    private static Random random = new Random();


    static Employee generateEmployee(int i){
        String[] names = new String[] { "Анатолий", "Глеб", "Клим", "Мартин", "Лазарь", "Владлен", "Клим", "Панкратий", "Рубен", "Герман" };
        String[] surnames = new String[] { "Григорьев", "Фокин", "Шестаков", "Хохлов", "Шубин", "Бирюков", "Копылов", "Горбунов", "Лыткин", "Соколов" };

        int salary = random.nextInt(200, 300);
        int index = random.nextInt(30, 50);
        int hours = random.nextInt(10,100);
        if (i !=2)                   // чтобы рабочих было больше
            return new Worker(names[random.nextInt(10)], surnames[random.nextInt(10)], salary * index * 7);
        else
            return new Freelancer(names[random.nextInt(10)], surnames[random.nextInt(10)], salary * index/10,  hours);
    }


    public static void main(String[] args) {

        int nuberOfEemployee = 15;
        Employee[] employees = new Employee[nuberOfEemployee];
        Random r = new Random();
        for (int i = 0; i < nuberOfEemployee; i++)
            employees[i] = generateEmployee(r.nextInt(3));  // чтобы фрилансеров было меньше

        for (Employee employee : employees){
            System.out.println(employee);
        }

//        Arrays.sort(employees, new SalaryComparator());
//        Arrays.sort(employees, new NameComparator());
        Arrays.sort(employees, new SurNameComparator());


        System.out.printf("\n*** Отсортированный массив сотрудников ***\n\n");

        for (Employee employee : employees){
            System.out.println(employee);
        }
        System.out.printf("Рабочих: %d\n",Worker.getCounter());
        System.out.printf("Фрилансеров: %d\n",Freelancer.getCounter());

    }

}

class SalaryComparator implements Comparator<Employee> {


    @Override
    public int compare(Employee o1, Employee o2) {

        return Double.compare( o2.calculateSalary(), o1.calculateSalary());
    }
}

class NameComparator implements Comparator<Employee> {


    @Override
    public int compare(Employee o1, Employee o2) {
        int res = o1.name.compareTo(o2.name);
        if (res == 0){
            res = Double.compare(o2.calculateSalary(), o1.calculateSalary());
        }
        return res;
    }
}

class SurNameComparator implements Comparator<Employee> {


    @Override
    public int compare(Employee o1, Employee o2) {
        int res = o1.surName.compareTo(o2.surName);
        if (res == 0){
            res = o1.name.compareTo(o2.name);
            if (res == 0)
                res = Double.compare(o2.calculateSalary(), o1.calculateSalary());
        }
        return res;
    }
}

abstract class Employee implements Comparable<Employee>{

    protected String name;
    protected String surName;
    protected double salary;

    public Employee(String name, String surName, double salary) {
        this.name = name;
        this.surName = surName;
        this.salary = salary;
    }

    public abstract double calculateSalary();

    @Override
    public String toString() {
        return String.format("Сотрудник: %s %s; Среднемесячная заработная плата: %.2f", name, surName, salary);
    }

    @Override
    public int compareTo(Employee o) {
        return Double.compare( o.calculateSalary(), calculateSalary());
    }
}

class Worker extends Employee{
    private static int counter;

    {
        counter++;
    }
    public static int getCounter(){
        return counter;
    }

    public Worker(String name, String surName, double salary) {
        super(name, surName, salary);
    }

    @Override
    public double calculateSalary() {
        return salary ;
    }

    @Override
    public String toString() {
        return String.format("%s %s; Рабочий; Оклад: %.2f (руб.)", name, surName, salary);
    }
}


class Freelancer extends Employee{
    private int workingHours;
    private static int counter;

    {
        counter++;
    }
    public static int getCounter(){
        return counter;
    }

    public Freelancer(String name, String surName, double salary, int workingHours) {
        super(name, surName, salary);
        this.workingHours = workingHours;
    }

    @Override
    public double calculateSalary() {
        return workingHours * salary;
    }

    @Override
    public String toString() {
        return String.format("%s %s; Фрилансер; Выплачено: %.2f руб. (Ставка: %.2f (руб./час))", name, surName, calculateSalary(), salary);
    }
}