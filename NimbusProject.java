import java.io.*;
import java.util.*;

// ===== Part A: Sum of Integers Using Autoboxing & Unboxing =====
class SumIntegers {
    public static void run() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();

        System.out.println("\n--- Sum of Integers (Autoboxing & Unboxing) ---");
        System.out.print("Enter integers separated by space: ");
        String[] inputs = sc.nextLine().split(" ");

        for (String str : inputs) {
            int num = Integer.parseInt(str); // String -> primitive
            numbers.add(num);                // Autoboxing
        }

        int sum = 0;
        for (Integer num : numbers) {
            sum += num;                      // Unboxing
        }

        System.out.println("Total sum: " + sum);
    }
}

// ===== Part B: Serialization / Deserialization of Student =====
class Student implements Serializable {
    int studentID;
    String name;
    double grade;

    public Student(int studentID, String name, double grade) {
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
    }

    public String toString() {
        return "Student ID: " + studentID + ", Name: " + name + ", Grade: " + grade;
    }
}

class StudentSerialization {
    public static void run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n--- Student Serialization & Deserialization ---");
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Grade: ");
        double grade = sc.nextDouble();

        Student student = new Student(id, name, grade);

        // Serialization
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("student.dat"))) {
            out.writeObject(student);
            System.out.println("Student serialized to student.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialization
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("student.dat"))) {
            Student deserializedStudent = (Student) in.readObject();
            System.out.println("Deserialized Student: " + deserializedStudent);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

// ===== Part C: Employee Management System =====
class Employee implements Serializable {
    String name;
    int id;
    String designation;
    double salary;

    public Employee(String name, int id, String designation, double salary) {
        this.name = name;
        this.id = id;
        this.designation = designation;
        this.salary = salary;
    }

    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Designation: " + designation + ", Salary: " + salary;
    }
}

class EmployeeManagement {
    static final String FILE_NAME = "employees.dat";

    @SuppressWarnings("unchecked")
    public static void run() {
        Scanner sc = new Scanner(System.in);
        int choice;
        ArrayList<Employee> employees = new ArrayList<>();

        // Load existing employees
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            employees = (ArrayList<Employee>) in.readObject();
        } catch (FileNotFoundException e) {
            // First time, no file exists yet
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        do {
            System.out.println("\n--- Employee Management Menu ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Exit to Main Menu");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Employee ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Designation: ");
                    String designation = sc.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();
                    sc.nextLine();

                    employees.add(new Employee(name, id, designation, salary));
                    System.out.println("Employee added successfully.");

                    // Save updated list
                    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
                        out.writeObject(employees);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    if (employees.isEmpty()) {
                        System.out.println("No employee records found.");
                    } else {
                        System.out.println("\n--- Employee Records ---");
                        for (Employee emp : employees) {
                            System.out.println(emp);
                        }
                    }
                    break;

                case 3:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 3);
    }
}

// ===== Main Class =====
public class NimbusProject {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Nimbus Project Menu =====");
            System.out.println("1. Sum of Integers (Autoboxing & Unboxing)");
            System.out.println("2. Student Serialization/Deserialization");
            System.out.println("3. Employee Management System");
            System.out.println("4. Exit Application");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    SumIntegers.run();
                    break;
                case 2:
                    StudentSerialization.run();
                    break;
                case 3:
                    EmployeeManagement.run();
                    break;
                case 4:
                    System.out.println("Exiting Nimbus Project. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 4);

        sc.close();
    }
}
