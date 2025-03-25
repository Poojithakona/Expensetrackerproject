package poojithaproject;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Expense implements Serializable {
    private double amount;
    private String category;
    private String description;
    private LocalDate date;

    public Expense(double amount, String category, String description) {
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = LocalDate.now();
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return date + " | " + category + " | Rs" + amount + " | " + description;
    }
}


class ExpenseManager {
    private static final String FILE_NAME = "expenses.txt";
    private List<Expense> expenses;

    public ExpenseManager() {
        this.expenses = new ArrayList<>();
        loadExpenses();
    }

    public void addExpense(double amount, String category, String description) {
        Expense expense = new Expense(amount, category, description);
        expenses.add(expense);
        saveExpenses();
    }

    public void showSummary(String period) {
        LocalDate now = LocalDate.now();
        double total = 0;

        for (Expense exp : expenses) {
            if ((period.equals("day") && exp.getDate().equals(now)) ||
                (period.equals("week") && exp.getDate().isAfter(now.minusDays(7))) ||
                (period.equals("month") && exp.getDate().getMonth().equals(now.getMonth()))) {
                total += exp.getAmount();
                System.out.println(exp);
            }
        }
        System.out.println("Total " + period + " expenses: Rs" + total);
    }

    private void saveExpenses() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(expenses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadExpenses() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            expenses = (List<Expense>) ois.readObject();
        } catch (FileNotFoundException e) {
            expenses = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

public class ExpenseTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();

        while (true) {
            System.out.println("\nDaily Expense Tracker");
            System.out.println("1. Add Expense");
            System.out.println("2. View Daily Summary");
            System.out.println("3. View Weekly Summary");
            System.out.println("4. View Monthly Summary");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    manager.addExpense(amount, category, description);
                    System.out.println("Expense added!");
                    break;
                case 2:
                    manager.showSummary("day");
                    break;
                case 3:
                    manager.showSummary("week");
                    break;
                case 4:
                    manager.showSummary("month");
                    break;
                case 5:
