// CS &141 assignment
// Payroll Calculator

import java.util.Scanner; //import scanner
import java.time.format.DateTimeFormatter; //import date and time formatter
import java.time.LocalDateTime; // import date and time method

public class PayrollCalculator { // main method begins execution of Java program

    public static String promptEmployeeType(Scanner in ) {
        System.out.println("\nEmployee Types:\n  S - Salary\n  H - Hourly"); // Take user input for employee type and assign to type
        System.out.print("Enter Employee Type (S or H): ");
        return in.nextLine();
    }
    public static void handleSalaryEmployee(String name, Scanner in ) {
        System.out.print("\nEnter Monthly Salary: "); // Take user input of monthly salary and assign to salary
        double salary = in.nextDouble();
        in.nextLine(); // clear the leftover newline
        double net_salary = (salary * 0.22); // Compute salary @ tax rate of 22% and assign to net_salary
        System.out.printf("%nThanks %s. Your Monthy Gross is : %.2f", name, salary); // monthly gross
        System.out.printf("%nPer week, this equals : %.2f", salary / 4); // weekly gross
        System.out.printf("%nFederal Tax is estimated at 22 percent. Your net pay is approximately : %.2f per month.%n", salary - net_salary); // net pay
    }

    public static void handleHourlyEmployee(String name, Scanner in ) {
        System.out.print("\nEnter Rate Per Hour: "); // Take user input of hourly rate and assign to rate
        double rate = in.nextDouble();
        in.nextLine(); // clear the leftover newline

        System.out.print("\nEnter Number of Hours Worked: "); // Take user input of total hours and assign to hours
        double hours = in.nextDouble();
        in.nextLine();

        if (hours > 40) { // Check for hours worked over 40 (overtime)
            double overtime = (hours - 40); // compute total of overtime hours and assign to overtime
            double gross_rate = (rate * hours); // compute weekly gross and assign to gross_rate
            double overtime_rate = (rate * 1.5); // compute overtime hourly rate and assign to overtime_rate
            double gross_total = (gross_rate + overtime_rate * overtime); // compute gross total including overtime and assign to gross_total
            double tax_cut = (gross_total * 0.22); // compute tax rate of 22% on gross total and assign to tax_cut

            System.out.printf("%nThanks %s.  Your weekly Gross pay including overtime is : %.2f", name, gross_total); // display weekly gross
            System.out.printf("%nRegular Pay : %.2f", gross_rate); // display weekly gross total without overtime
            System.out.printf("%nOvertime Rate : %.2f @ %.1f hours = %.2f", overtime_rate, overtime, overtime_rate * overtime); // display overtime rate, hours and total
            System.out.printf("%n%nFederal Tax is estimated at 22 percent. Your net pay is approximately : %.2f this week.%n", gross_total - tax_cut); // display net pay after tax

        } else { // user worked 40 hours or less
            double weekly_gross = (rate * hours); // compute weekly gross and assign to weekly_gross
            double gross_total = (weekly_gross * 4); // compute monthly projection at current rate and assign to gross_total
            double tax_cut = (weekly_gross * 0.22); // compute tax rate of 22% on weekly gross and assign to tax cut

            System.out.printf("%nThanks %s. Your Monthy Gross at this rate would be : %.2f", name, gross_total); // display projected monthy gross
            System.out.printf("%nPer week, this equals : %.2f", weekly_gross); // display weekly gross total
            System.out.printf("%nFederal Tax is estimated at 22 percent. Your net pay is approximately : %.2f this week.%n", weekly_gross - tax_cut); // display weekly net after tax
        }
    }

    public static void handleInvalidEmployeeType(String input) {
        System.out.printf("Error: '%s' is not a valid employee type.%n", input);
        System.out.println("Please enter 'S' for Salary or 'H' for Hourly.");
    }

    public static boolean promptContinue(Scanner in ) {
        System.out.print("\nWould you like to continue? (Y or N) : "); // prompt user to run for additional employees, assign to again
        String again = in.next();// check again for n/N, any other response runs program loop
        System.out.printf("Value of again : " + again);
        System.out.println("[debug] returning: " + !again.equalsIgnoreCase("Y"));
        return again.equalsIgnoreCase("Y"); // boolean value for stop is true, exit loop
    }

    public static void main(String[] args) {

        boolean stop = false; // boolean value for stop is false

        while (!stop) { // while stop is false, run loop

            Scanner in = new Scanner(System.in);

            System.out.print("\n\n**** Welcome to the payroll calulator ****  "); // Welcome msg. Promt user for name
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); // Set display order for date/time
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now)); // print current date and time

            System.out.print("Enter Employee Name: "); // Take user input for name and assign to name
            String name = in.nextLine();

            String type = "";
            int attempts = 0;

            while (!(type.equalsIgnoreCase("S") || type.equalsIgnoreCase("H")) && attempts < 5) {
                type = promptEmployeeType(in);
                if (!type.equalsIgnoreCase("S") && !type.equalsIgnoreCase("H")) {
                    handleInvalidEmployeeType(type);
                    attempts++;
                }
            }

            if (attempts >= 5) {
                System.out.println("Too many invalid attempts. Exiting payroll system.");
                return; // exits main
            }

            if (type.equalsIgnoreCase("S")) {
                handleSalaryEmployee(name, in);
            } else {
                handleHourlyEmployee(name, in);
            }

            stop = !promptContinue(in); // prompt user to continue; possible exit
        }
    } // end of main method
} // end of PayrollCalculator class